package com.wzb.runtimetest;
import android.util.Log;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Apr 11, 2019 10:34:11 AM	
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
/**
 * 读写配置文件
 * 
 * @author wzb
 *
 */
public class PropUtil {

    /** 
     * 根据KEY，读取文件对应的值 
     * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties 
     * @param key 键 
     * @return key对应的值 
     */  
    public static String readData(String filePath, String key) {  
 
        Properties props = new Properties();  
        try {  
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));  
            props.load(in);  
            in.close();  
            String value = props.getProperty(key);  
            Log.e("wzb","readData key="+key+",v="+value);
            return value;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    /** 
     * 修改或添加键值对 如果key存在，修改, 反之，添加。 
     * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties 
     * @param key 键 
     * @param value 键对应的值 
     */  
    public static void writeData(String filePath, String key, String value) {  
 
        Properties prop = new Properties();  
        try {  
            File file = new File(filePath);  
            if (!file.exists())  
                file.createNewFile();  
            InputStream fis = new FileInputStream(file);  
            prop.load(fis);  
            //一定要在修改值之前关闭fis  
            fis.close();  
            OutputStream fos = new FileOutputStream(filePath);  
            prop.setProperty(key, value);  
            //保存，并加入注释  
            prop.store(fos, "Update " + key + " value");  
            fos.close();  
            Log.e("wzb","writeData key="+key+",v="+value);
        } catch (IOException e) {  
           Log.e("wzb","Visit " + filePath + " for updating " + value + " value error");  
        }  
    }  
    
    public static void removeData(String filePath, String key) {  
 
        Properties prop = new Properties();  
        try {  
            File file = new File(filePath);  
            if (!file.exists())  
                file.createNewFile();  
            InputStream fis = new FileInputStream(file);  
            prop.load(fis);  
            //一定要在修改值之前关闭fis  
            fis.close();  
            OutputStream fos = new FileOutputStream(filePath);  
            //prop.setProperty(key, value);  
            prop.remove(key);
            //保存，并加入注释  
            prop.store(fos, "Remove " + key );  
            fos.close();  
            Log.e("wzb","removeData key="+key);
        } catch (IOException e) {  
           Log.e("wzb","Visit " + filePath + " for updating " );  
        }  
    }  


}
