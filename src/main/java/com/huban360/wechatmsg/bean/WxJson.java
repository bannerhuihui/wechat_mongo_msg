package com.huban360.wechatmsg.bean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @program: code
 * @description: 微信公众号模板消息封装数据格式类
 * @author: Dahui
 * @create: 2019-03-19 11:56
 **/
public class WxJson implements Serializable {
    private static final long serialVersionUID = 4474191341944075901L;
    /** 颜色的正则表达式 */
    private static final String COLOR_PTN = "^(#[\\da-fA-F]+)|([a-zA-Z]+)$";

    /**
     * 模板中的小程序封装
     *  appid 小程序序列号
     *  pagepath 需要跳转的路径地址
     */
    private class LittlePro{
        private String appid;
        private String pagepath;

        public LittlePro(String appid,String pagepath){
            this.appid = appid;
            this.pagepath = pagepath;
        }

        public LittlePro(){

        }

        public void setAppid(String appid){
            this.appid = appid;
        }

        public String getAppid(){
            return appid;
        }

        public String getPagepath(){
            return pagepath;
        }

        public void setPagepath(String pagepath){
            this.pagepath=pagepath;
        }

    }

    /**
     * 模板中变量及其颜色配置的私有类
     */
    private class Value {
        private String value;
        private String color = "#173177";

        public Value(String value) {
            super();
            this.value = value;
        }

        public Value(String value, String color) {
            super();
            this.value = value;
            if (null != color && color.matches(COLOR_PTN)) {
                this.color = color;
            }
        }

        public String getColor() {
            return color;
        }

        public String getValue() {
            return value;
        }
    }

    private String touser;
    private String template_id;
    // 模板消息全文链接地址
    private String url;
    // 模板消息顶部颜色
    private String topcolor = "#28FF28";
    private Map<String, Value> datas = new LinkedHashMap<String, Value>();
    private Map<String, LittlePro> miniprogram = new LinkedHashMap<String, LittlePro>();

    public WxJson(String template_id, String url) {
        super();
        this.template_id = template_id;
        this.url = url;
    }

    public WxJson() {
        super();
        this.template_id = template_id;
    }

    /**
     * 重写父类的toString方法，用来生成微信模板消息的json报文
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        LittlePro pro = miniprogram.get("miniprogram");
        sb.append("{\"touser\":\"").append(touser).append("\",");
        sb.append("\"template_id\":\"").append(template_id).append("\",");
        sb.append("\"miniprogram\":{").append("\"appid\":\"").append(pro.getAppid())
                .append("\",\"pagepath\":\"").append(pro.getPagepath()).append("\"},");
        sb.append("\"data\":{");

        Iterator<Entry<String, Value>> it = datas.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Value> entry = it.next();
            Value v = entry.getValue();
            sb.append('"').append(entry.getKey()).append("\":{\"value\":\"").append(v.getValue())
                .append("\",\"color\":\"").append(v.getColor()).append("\"},");
        }
        if (datas.size() > 0) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("}}");
        return sb.toString();
    }

    /**
     * 增加变量的键值对，模板中对应变量值的显示颜色使用默认的颜色
     * 
     * @param key
     * @param value
     */
    public void addData(String key, String value) {
        datas.put(key, new Value(value));
    }

    /**
     * 添加小程序参数
     * appid 微信小程序编号
     * pagepath 需要跳转的页面
     */
    public void addMiniprogram(String appid,String pagepath){
        miniprogram.put("miniprogram",new LittlePro(appid,pagepath));
    }

    /**
     * 增加变量的键值对，模板中对应变量值的显示颜色使用指定的颜色
     * 
     * @param key
     * @param value
     * @param color
     */
    public void addData(String key, String value, String color) {
        if (null != color && color.matches(COLOR_PTN)) {
            datas.put(key, new Value(value, color));
        } else {
            datas.put(key, new Value(value));
        }
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public void setTouser(String touser){
        this.touser = touser;
    }

    public String getTouser(){
        return touser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        if (null != topcolor && topcolor.matches(COLOR_PTN)) {
            this.topcolor = topcolor;
        }
    }
}
