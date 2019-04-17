package com.huban360.wechatmsg.dao;

import com.huban360.wechatmsg.bean.DataAll;
import com.huban360.wechatmsg.util.MongoManager;
import com.huban360.wechatmsg.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: code
 * @description: MongoDB数据操作
 * @author: Dahui
 * @create: 2019-03-19 13:58
 **/
public class MongoDBDao {

    private Logger logger = LoggerFactory.getLogger(MongoDBDao.class);

    /**
     * 保存模板发送数据
     * @return
     * @throws
     */
    public boolean saveDBToMongo(String msg ,String start) throws JAXBException {
        logger.info("MongoDBDao-saveDBToMongo 进入数据保存阶段!");
        // 处理保存的数据
        boolean flag = false;
        MongoManager mongoManager = new MongoManager();
        MongoDatabase mongoDatabase = mongoManager.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection(Util.getPropertyValue("mongo","insert"));
        Document document = new Document();
        logger.info("数据保存开始封装map!");
        document.put("msg", msg);
        document.put("start", start);
        document.put("insertTime",Util.dateTormatter(new Date()));
        // 数据保存操作
        try {
            collection.insertOne(document);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询微信接口
     * @return
     */
    public List<DataAll> findWechatData(String startDate,String endDate,String shopid){
        logger.info("进入数据查询操作!");
        MongoDatabase db = MongoManager.getMongoDatabase();
        MongoCollection<Document> collections = db.getCollection(Util.getPropertyValue("mongo","findmsg"));
        BasicDBObject query = new BasicDBObject();
        query.put("createTime", new BasicDBObject("$gte", startDate).append("$lte",endDate));
        if(StringUtils.isNotEmpty(shopid)){
            query.put("shopId",Long.valueOf(shopid));
        }
        FindIterable<Document> documents = collections.find(query);
        logger.info("查询操作完成!");
        List<DataAll> list = new ArrayList<DataAll>();
        for (Document doc: documents) {
            DataAll dataAll = new DataAll();
            dataAll.setWxId(String.valueOf(doc.get("wxId")));
            dataAll.setShopid(String.valueOf(doc.get("shopId")));
            dataAll.setTodayUser(String.valueOf(doc.get("todayUser")));
            dataAll.setTodayMsg(String.valueOf(doc.get("todayMsg")));
            dataAll.setShopName(String.valueOf(doc.get("shopName")));
            dataAll.setMsgRank(String.valueOf(doc.get("msgRank")));
            dataAll.setTodayHit(String.valueOf(doc.get("todayHit")));
            dataAll.setHitRank(String.valueOf(doc.get("hitRank")));
            dataAll.setTotalPrice(String.valueOf(doc.get("totalPrice")));
            dataAll.setTotalUser(String.valueOf(doc.get("totalUser")));
            dataAll.setHitCount(String.valueOf(doc.get("hitCount")));
            dataAll.setEfficientUser(String.valueOf(doc.get("efficientUser")));
            dataAll.setTodayUpdate(String.valueOf(doc.get("todayUpdate")));
            dataAll.setLongestUser(String.valueOf(doc.get("longestUser")));
            dataAll.setHours(String.valueOf(doc.get("hours")));
            dataAll.setInsert_time(String.valueOf(doc.get("createTime")));
            list.add(dataAll);
        }
        return list;
    }
}
