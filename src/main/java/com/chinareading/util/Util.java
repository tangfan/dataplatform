package main.java.com.chinareading.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by tangfan on 2016/1/11.
 */
public class Util {
    public static String readFileToString(File file) {
        StringBuffer result = new StringBuffer("");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"GBK");
            BufferedReader br = new BufferedReader(isr);
            String s;
            while ((s = br.readLine()) != null) {
                result = result.append("\n" + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
