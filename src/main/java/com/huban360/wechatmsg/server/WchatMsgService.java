package com.huban360.wechatmsg.server;

import com.huban360.wechatmsg.bean.DataAll;
import com.huban360.wechatmsg.bean.WxJson;
import com.huban360.wechatmsg.dao.MongoDBDao;
import com.huban360.wechatmsg.util.Util;
import com.huban360.wechatmsg.util.WechatMsg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: code
 * @description: 发送微信消息业务
 * @author: Dahui
 * @create: 2019-03-19 13:30
 **/
public class WchatMsgService {

    private static final Logger LOG = LoggerFactory.getLogger(WchatMsgService.class);


    private static final String BASQWARHER1 = "";
    private static final String BASQWARHER2 = "";
    public void main() {
        MongoDBDao dao = new MongoDBDao();
        //获取当天的数据
        List<DataAll> list = dao.findWechatData(Util.mathDate(new Date(),0),Util.mathDate(new Date(),1),"");
        for (DataAll dataAll: list) {
            WxJson wxJson = new WxJson();
            wxJson.addMiniprogram("小程序id","小程序跳转路径");
            //判断数据是否有微信id
            if(StringUtils.isNotEmpty(dataAll.getWxId())){
                wxJson.setTouser(dataAll.getWxId());
                if(StringUtils.isNotEmpty(dataAll.getTodayUser())){
                    //判断该企业近日是否有人使用配置宝
                    if(Integer.valueOf(dataAll.getTodayUser())>0){
                        wxJson.setTemplate_id(BASQWARHER1);
                        dataAll.setBadweather_name(BASQWARHER1);
                    }else {
                        wxJson.setTemplate_id(BASQWARHER2);
                        dataAll.setBadweather_name(BASQWARHER2);
                    }
                }
                //模板一的业务
                if(StringUtils.equals(BASQWARHER1,dataAll.getBadweather_name())){
                    List<DataAll> data = dao.findWechatData(Util.mathDate(new Date(),-1),Util.mathDate(new Date(),0),dataAll.getShopid());
                    if(data.size()>0){
                        DataAll dataOne = data.get(0);
                        //登录输出文字
                        String loginStart = "";
                        //计算当天命中率

                        //判断昨日是否登录
                        if(StringUtils.isNotEmpty(dataOne.getTodayUser())){
                            if(Integer.valueOf(dataOne.getTodayUser())>0){
                                // 拼接字段
                                String hitStr = "";
                                int yesTodayHit = getYesTodayHit(dataOne);
                                int todayHit = getTodayHit(dataAll);
                                if(todayHit>=yesTodayHit){
                                    hitStr = "上升"+(todayHit-yesTodayHit)+"%";
                                }else{
                                    hitStr = "下降"+(yesTodayHit-todayHit)+"%";
                                }
                                loginStart = "相较于昨日"+hitStr;
                                wxJson.addData("k1","v1");
                                wxJson.addData("k2","v2");

                            }else{
                                // 错误提醒
                                LOG.error("时间转换错误，请核对参数getTodayUser");
                            }
                        }else{
                            loginStart = "昨日未登录。";
                            wxJson.addData("k3","v3");
                            wxJson.addData("k4","v4");
                        }
                        //TODO 判断命中率是否低于行业标准
                        Map<String, Integer> map = mathAll(list);
                        int totalMsg = map.get("totalMsg");
                        int totalInit = map.get("totalInit");
                        int totalTime = map.get("totalTime");
                        int totalSize = map.get("totalSize");
                        // 平均时长
                        Double pvmtime = Double.valueOf(totalTime/totalSize);
                        // （平均）命中率
                        int initAll = (int) (Double.valueOf(totalInit/totalMsg)*100);
                        int init = (int) (Double.valueOf(dataAll.getTodayHit())/Double.valueOf(dataAll.getTodayMsg())*100);
                        //命中率校验提示字段
                        String initmsg = "";
                        if(init>initAll){
                            initmsg = "高于行业平均标准的"+(init-initAll)+"%";
                        }else if(init<initAll){
                            initmsg = "低于行业平均标准的"+(initAll-init)+"%";
                        }
                        //更新库存
                        String updatGoose = "";
                        if(StringUtils.isNotEmpty(dataAll.getTodayUpdate())){
                            if(Integer.valueOf(dataAll.getTodayUpdate())>0){
                                updatGoose = "今日更新库存"+dataAll.getTodayUpdate()+"条。";
                            }else{
                                updatGoose = "今日未更新库存，请及时更新。";
                            }
                        }else{
                            LOG.error("库存更新条数错误，请核对参数 todayUpdate");
                        }
                        //TODO 判断员工平均在线时长
                        String timeMsg = "";
                        if(StringUtils.isNotEmpty(dataAll.getHours())){
                            if(pvmtime>Double.valueOf(dataAll.getHours())){
                                timeMsg = "，建议增加员工在线工作时长。";
                            }
                        }
                        //当前用户是否还在使用配置宝继续工作
                        String users = "此时，";
                        if(StringUtils.isNotEmpty(dataAll.getUsers())){
                            users += dataAll.getUsers()+"仍在使用配置宝卖力的工作，请酌情予以奖励。";
                        }else{
                            LOG.error("当前使用用户异常，请检查数据类型是否正确");
                        }
                    }
                }else{
                    //模板二业务
                    String msg2 = "";
                }
                //发送微信模板消息
                boolean successYN = WechatMsg.pullMsg(wxJson.toString());
                if(successYN){
                    dataAll.setMsg_start("1");
                }else{
                    dataAll.setMsg_start("0");
                }
            }else{
                try {
                    String s = dataAll.toString();
                    dao.saveDBToMongo("微信id为空"+s,"0");
                } catch (JAXBException e) {
                    LOG.error("错误数据保存失败!错误信息为:" + e);
                    e.printStackTrace();
                }finally{
                    continue;
                }
            }
        }
    }


    /**
     * 获取今日命中
     * @param dataOne
     * @return
     */
    public int getTodayHit(DataAll dataOne){
        Double todayHit = 0.0;
        if(StringUtils.isNotEmpty(dataOne.getTodayMsg())&&StringUtils.isNotEmpty(dataOne.getTodayHit())){
            todayHit = Double.valueOf(dataOne.getTodayHit())/Double.valueOf(dataOne.getTodayMsg())*100;
        }else{
            LOG.error("TodayMsg / TodayHit 内容为空");
        }
        return todayHit.intValue();
    }

    /**
     * 获取昨天命中
     * @param dataAll
     * @return
     */
    public int getYesTodayHit(DataAll dataAll){
        Double yesTodayHit = 0.0;
        if(StringUtils.isNotEmpty(dataAll.getTodayMsg())&&StringUtils.isNotEmpty(dataAll.getTodayHit())){
            yesTodayHit = Double.valueOf(dataAll.getTodayHit())/Double.valueOf(dataAll.getTodayMsg())*100;
        }else{
            LOG.error("TodayMsg / TodayHit 内容为空");
        }
        return yesTodayHit.intValue();
    }

    /**
     * 获取总条数 命中数 总价格 总时长 在线商家总数
     * @param list
     * @return
     */
    public Map<String,Integer> mathAll(List<DataAll> list){
        Map<String,Integer> map = new HashMap<String,Integer>();
        //捕获总条数
        Integer todayMsgTotal = 0;
        //命中条数
        Integer initTotal = 0;
        //总价值
        Integer totalPrice = 0;
        //平均时长
        Integer totalTime = 0;
        Integer size = 0;
        for (DataAll data: list
             ) {
            if(StringUtils.isNotEmpty(data.getHours())){
               if(Integer.valueOf(String.valueOf(StringUtils.isNotEmpty(data.getHours())))>0){
                   size += 1;
               }
            }
            todayMsgTotal += Integer.valueOf(data.getTodayMsg());
            initTotal += Integer.valueOf(data.getTodayHit());
            totalPrice += Integer.valueOf(data.getTotalPrice());
            totalTime += Integer.valueOf(data.getHours());
        }
        map.put("totalMsg",todayMsgTotal);
        map.put("totalInit",initTotal);
        map.put("totalPrice",totalPrice);
        map.put("totalTime",totalTime);
        map.put("totalSize",size);
        return map;
    }

}
