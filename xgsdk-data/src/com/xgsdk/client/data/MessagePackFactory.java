package com.xgsdk.client.data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;
import com.xgsdk.client.core.SystemInfo;
import com.xgsdk.client.core.XGInfo;
import com.xgsdk.client.core.utils.MD5Util;
import com.xgsdk.client.data.message.Bucket;
import com.xgsdk.client.data.message.Head;
import com.xgsdk.client.data.message.MemBucket;
import com.xgsdk.client.data.message.MessageEntity;

import android.app.Activity;



/**
 * 
 * 
 * 
 * @author yinlong
 */
class MessagePackFactory {
    
    private static final String MESSAGE_SOURCE = "client";
    
    private static final String MESSAGE_OS = "android";
    
    private static final Object LOCK = new Object();
    
    private Head head;
    
    private Activity activity;
    
    private ExecutorService packerServce;
    
    private ExecutorService sendService;
    
    private Bucket bucket;
    
    private volatile boolean hurryUp;
    
    /**
     * 本次会话seessionId
     */
    private String sessionId;
    
    MessagePackFactory() {
        packerServce = Executors.newFixedThreadPool(2);
        sendService = Executors.newFixedThreadPool(2);
        hurryUp = false;
        reloadSession();
    }
    
    void setActivity(Activity activity) {
        this.activity = activity;
    }

    void start() {
        hurryUp = false;
        reloadSession();
    }
    
    void tryOver() {
        // 所有工作请快点弄完
        hurryUp = true;
    }
    
    void reloadSession() {
        sessionId = "session" + MD5Util.md5(generatorUUID());
    }
    
    String generatorUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    void handleCandy(MessagePacker candy) {
        candy.setMsgSn(sessionId);
        packerServce.execute(candy);
    }
    
    void addBucket(JSONObject objs) {
        synchronized (LOCK) {
            if (null == bucket) {
                initHead();
                bucket = createBucket();
            }
            bucket.addMessage(objs);
            if (hurryUp || bucket.isReadyToSend()) {
                sendService.execute(new MessageSender(bucket));
                bucket = null;
            }
        }
    }

    Bucket createBucket() {
        initHead();
        return new MemBucket(head, XGInfo.getXGAppKey(activity));
    }
    
    void initHead() {
        head = new Head();
        head.setDatasource(MessagePackFactory.MESSAGE_SOURCE);
        head.setChannel(XGInfo.getChannelId());
        head.setDeviceId(XGInfo.getXGDeviceId(activity));
        head.setOs(MessagePackFactory.MESSAGE_OS);
        head.setOsVersion(SystemInfo.getAndroidSdkVersion(activity));
        
        // TODO
        head.setTimezone(8);
        head.setCountry("CN");
        head.setLanguage("zh");
        
        head.setAppVersionCode(SystemInfo.getAppVersionCode(activity));
        head.setAppVersion(SystemInfo.getAppVersionName(activity));
        head.setCarrier(SystemInfo.getOperators(activity));
        head.setXgVersion(XGInfo.getXGVersion(activity));
        head.setAppId(XGInfo.getXGAppId(activity));
        head.setCpuFreq(SystemInfo.getCpu(activity));
        head.setMemTotal(SystemInfo.getMemoryTotal(activity));
        head.setDeviceBrand(SystemInfo.getDeviceBrand());
        head.setDeviceModel(SystemInfo.getDeviceModel());
        head.setImei(SystemInfo.getIMEI(activity));
        head.setImsi(SystemInfo.getIMSI(activity));
        head.setMac(SystemInfo.getMacAddress(activity));
    }
    

}
