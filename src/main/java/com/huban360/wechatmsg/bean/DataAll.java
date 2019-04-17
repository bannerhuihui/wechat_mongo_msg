package com.huban360.wechatmsg.bean;

import com.huban360.wechatmsg.util.Util;

import java.util.Date;

/**
 * @program: code
 * @description: 微信消息数据
 * @author: Dahui
 * @create: 2019-03-19 12:54
 **/
public class DataAll {

    private String users; //当前登录的用户
    private String todayMsg; //今天捕获消息总数
    private String todayUser;  //今天登陆用户个数
    private String shopid; //店铺id
    private String shopName; // 店铺名称
    private String msgRank; // 捕获消息排名
    private String todayHit; // 今天命中消息总数
    private String hitRank;  // 命中消息排名
    private String totalUser; // 店铺的用户总个数
    private String totalPrice; // 今天命中消息总价值
    private String hitCount; // 效率最高用户命中个数
    private String efficientUser; //  今天效率最高的用户
    private String todayUpdate; // 今天更新的库存条数
    private String wxId;  //管理员微信id
    private String longestUser; //  登录时间最长的用户
    private String hours; //  店铺平均登录时长
    private String badweather_name;  //模板编号
    private String msg_start; // 发送状态
    private String insert_time; //插入时间


    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getBadweather_name() {
        return badweather_name;
    }

    public void setBadweather_name(String badweather_name) {
        this.badweather_name = badweather_name;
    }

    public String getMsg_start() {
        return msg_start;
    }

    public void setMsg_start(String msg_start) {
        this.msg_start = msg_start;
    }

    public String getInsert_time() {
        return Util.dateTormatter(new Date());
    }

    public void setInsert_time(String insert_time){
        this.insert_time = insert_time;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getTodayMsg() {
        return todayMsg;
    }

    public void setTodayMsg(String todayMsg) {
        this.todayMsg = todayMsg;
    }

    public String getTodayUser() {
        return todayUser;
    }

    public void setTodayUser(String todayUser) {
        this.todayUser = todayUser;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMsgRank() {
        return msgRank;
    }

    public void setMsgRank(String msgRank) {
        this.msgRank = msgRank;
    }

    public String getTodayHit() {
        return todayHit;
    }

    public void setTodayHit(String todayHit) {
        this.todayHit = todayHit;
    }

    public String getHitRank() {
        return hitRank;
    }

    public void setHitRank(String hitRank) {
        this.hitRank = hitRank;
    }

    public String getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(String totalUser) {
        this.totalUser = totalUser;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getHitCount() {
        return hitCount;
    }

    public void setHitCount(String hitCount) {
        this.hitCount = hitCount;
    }

    public String getEfficientUser() {
        return efficientUser;
    }

    public void setEfficientUser(String efficientUser) {
        this.efficientUser = efficientUser;
    }

    public String getTodayUpdate() {
        return todayUpdate;
    }

    public void setTodayUpdate(String todayUpdate) {
        this.todayUpdate = todayUpdate;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getLongestUser() {
        return longestUser;
    }

    public void setLongestUser(String longestUser) {
        this.longestUser = longestUser;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return shopid + "<-店铺id"+
        todayMsg + "<-今天捕获消息总数"+
        todayUser + "<-今天登陆用户个数"+
        shopName + "<-店铺名称"+
        msgRank + "<-捕获消息排名"+
        todayHit + "<-今天命中消息总数"+
        hitRank + "<-命中消息排名"+
        totalUser + "<-店铺的用户总个数"+
        totalPrice + "<-今天命中消息总价值"+
        hitCount + "<-效率最高用户命中个数"+
        efficientUser +"<-今天效率最高的用户"+
        todayUpdate + "<-今天更新的库存条数"+
        wxId +"<-管理员微信id"+
        longestUser+ "<-登录时间最长的用户"+
        hours + "<-店铺平均登录时长" +
        badweather_name + "<-模板编号"+
        msg_start + "<-发送状态"+
        insert_time + "<-插入时间"+
        users + "<-当前登录状态员工";
    }
}
