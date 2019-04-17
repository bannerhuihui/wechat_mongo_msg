/**
 * TODO(用一句话描述该文件的作用)
 *
 * @title: MongoManager.java
 * @author mayujie-ghq
 * @date 2015年8月4日 下午3:37:44
 */
package com.huban360.wechatmsg.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取MongoDB连接
 * 
 * @className MongoManager
 * @author mayujie-ghq
 * @version V1.0 2015年8月4日 下午3:37:44 TODO(如果是修改版本，描述修改内容)
 */
public class MongoManager {

    private static final Logger logger = LoggerFactory.getLogger(MongoManager.class);



    /**
     *连接MongoDB
     * @return
     */
    public static MongoDatabase getMongoDatabase(){
        String mongodbIp = Util.getPropertyValue("mongo","mongodbIp");
        String mongodbPort = Util.getPropertyValue("mongo","mongodbPort");
        String mdataBase = Util.getPropertyValue("mongo","dataBase");
        MongoClient mongoClient = new MongoClient(mongodbIp,Integer.valueOf(mongodbPort));
        MongoDatabase db = mongoClient.getDatabase("futures");
        return db;
    }


}
