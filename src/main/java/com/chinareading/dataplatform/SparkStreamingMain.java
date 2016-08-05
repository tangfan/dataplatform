package main.java.com.chinareading.dataplatform;

import com.chinareading.util.Util;
import com.google.gson.Gson;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.flume.FlumeUtils;
import org.apache.spark.streaming.flume.SparkFlumeEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tangfan on 2016/1/11.
 */
public class SparkStreamingMain {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: SparkStreamingMain <SQL_file> <properties_file>");
            System.exit(1);
        }

        String SQLFileName = args[0];
        String propertieFileName = args[1];

        final File sqlFile = new File(SQLFileName);
        File propertiesFile = new File(propertieFileName);

        FileInputStream fis = new FileInputStream(propertiesFile);
        Properties props = new Properties();
        props.load(fis);

        SparkConf sparkConf = new SparkConf().setAppName("SparkSteaming_" + props.getProperty("app.name"));

        String host = props.getProperty("streaming.host");
        int port = Integer.parseInt(props.getProperty("streaming.port"));
        final Duration batchInterval = new Duration(Integer.parseInt(props.getProperty("streaming.batch.interval")));

        final JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf,batchInterval);
        JavaReceiverInputDStream<SparkFlumeEvent> flumeStream = FlumeUtils.createStream(streamingContext, host, port);

        String cols = props.getProperty("streaming.cols");
        final String[] colsArr = cols.split(",");

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        final JavaDStream rowDStream = flumeStream.map(sparkFlumeEvent -> {
            String line = new String(sparkFlumeEvent.event().getBody().array());
            String result = null;
            if(!line.isEmpty()){
                String row = line.split("\t")[2];
                String[] valueArr = row.split("\\|",-1);
                if(valueArr.length>0){
                    if(valueArr.length==colsArr.length){
                        Map map = new HashMap();
                        for (int i=0;i<colsArr.length;i++){
                            map.put(colsArr[i],valueArr[i]);
                        }
                        Gson gson = new Gson();
                        result = gson.toJson(map);
                    }else{
                        throw new Exception("columns not match ! receive="+line);
                    }
                }
            }
            return result;
        });

        rowDStream.print();

        final String jdbcURL = props.getProperty("jdbc.url");
        final String tableName = props.getProperty("table.name");
        final Properties jdbcProps = new Properties();
        jdbcProps.setProperty("user", props.getProperty("jdbc.username"));
        jdbcProps.setProperty("password", props.getProperty("jdbc.password"));

        rowDStream.foreachRDD((VoidFunction<JavaRDD<String>>) rdd -> {
            if (!rdd.isEmpty()) {
                SQLContext sqlContext = SQLContext.getOrCreate(rdd.context());
                DataFrame df = sqlContext.read().json(rdd);
                df.show();
                df.registerTempTable("tmp_table");

                String sql = Util.readFileToString(sqlFile);
                sql = sql.replace("DATA_TIME", sdf.format(new Date()));
                df = sqlContext.sql(sql);

                df.write().mode(SaveMode.Append).partitionBy("stat_time").jdbc(jdbcURL, tableName, jdbcProps);
            }
        });

        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
