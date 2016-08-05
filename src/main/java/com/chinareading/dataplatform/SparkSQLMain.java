package main.java.com.chinareading.dataplatform;

import com.chinareading.util.Util;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.hive.HiveContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by tangfan on 2016/1/11.
 */
public class SparkSQLMain {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: SparkSQLMain <SQL_file> <properties_file>");
            System.exit(1);
        }

        String SQLFileName = args[0];
        String propertieFileName = args[1];

        File sqlFile = new File(SQLFileName);
        File propertiesFile = new File(propertieFileName);

        FileInputStream fis = new FileInputStream(propertiesFile);
        Properties props = new Properties();
        props.load(fis);

        String sql = Util.readFileToString(sqlFile);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataDate = props.getProperty("data.date");
        if ("".equals(dataDate)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date yesterday = cal.getTime();
            dataDate = sdf.format(yesterday);
        }
        sql = sql.replaceAll("DATA_TIME", dataDate);

        String hiveDataBase = props.getProperty("hive.database");

        SparkConf sparkConf = new SparkConf().setAppName("SparkSQL_" + props.getProperty("app.name"));
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        HiveContext hiveContext = new HiveContext(sparkContext);

        try {
            if (!"".equals(hiveDataBase)) {
                hiveContext.sql("use " + hiveDataBase);
            }
            DataFrame df = hiveContext.sql(sql);

            String jdbcURL = props.getProperty("jdbc.url");
            String tableName = props.getProperty("table.name");
            Properties jdbcProps = new Properties();
            jdbcProps.setProperty("user", props.getProperty("jdbc.username"));
            jdbcProps.setProperty("password", props.getProperty("jdbc.password"));

            df.write().mode(SaveMode.Append).partitionBy("statis_day").jdbc(jdbcURL, tableName, jdbcProps);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sparkContext.stop();
        }
    }
}
