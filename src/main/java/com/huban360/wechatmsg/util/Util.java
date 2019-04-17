package com.huban360.wechatmsg.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * @program: code
 * @description: 获取配置文件
 * @author: Dahui
 * @create: 2019-03-19 14:18
 **/
public class Util {


    /**
     * 读取配置文件信息
     * @param filename  文件名
     * @param key
     * @return
     */
    public static String getPropertyValue(String filename,String key){

        Properties prop = null;
        String value = "";
        try {
            // 通过Spring中的PropertiesLoaderUtils工具类进行获取
            prop = PropertiesLoaderUtils.loadAllProperties(filename+".properties");
            // 根据关键字查询相应的值
            value = prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 时间格式化
     * @param date
     * @return
     */
    public static String dateTormatter(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * 获取时间
     * @param date
     * @param time 1 明天 0 今天 -1 昨天
     * @return
     */
    public static String mathDate(Date date,int time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,time);
        String format1 = simpleDateFormat.format(instance.getTime());
        return format1+" 00:00:00";
    }

}
