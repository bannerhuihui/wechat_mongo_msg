package com.huban360.wechatmsg.util;

import com.alibaba.fastjson.JSONObject;
import com.huban.framework.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @program: code
 * @description: 推送微信公众号模板消息给个人
 * @author: Dahui
 * @create: 2019-03-19 11:56
 **/
public class WechatMsg {

    private static final Logger log = LoggerFactory.getLogger(WechatMsg.class);

    private static final String URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 获取微信模板发送链接
     * @return
     */
    private static String getUrl(){
        //TODO 1.使用微信刷新token 2.访问接口访问token
        String token = "";
        try {
            token = HttpUtils.get("");
        } catch (IOException e) {
            log.error("WechatMsg 调用接口获取token失败"+e);
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 根据微信ID发送模板消息到微信公众号
     * @param msg
     * @return
     */
    public static boolean pullMsg(String msg){
        boolean flag = false;
        String token = "";
        try {
            String post = HttpUtils.post(URL+token, msg);
            log.info("pullMsg返回消息为:"+post);
            if(StringUtils.isNotEmpty(post)){
                JSONObject jsonObject = JSONObject.parseObject(post);
                String errmsg = (String) jsonObject.get("errmsg");
                if(StringUtils.equals("ok",errmsg)){
                    flag = true;
                }
            }
        } catch (IOException e) {
            log.error("wechatmsg--wechatMsg发送微信消息接口异常");
            e.printStackTrace();
        }
        return flag;
    }

}
