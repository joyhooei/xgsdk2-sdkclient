package com.xgsdk.client.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgsdk.client.data.message.MessageType;

import java.util.Map;



/**
 * 
 * 
 * 
 * @author yinlong
 */
public class MessagePacker implements Runnable {

    private Object sourceMessage;
    
    private MessageType messageType;
    
    private long ts;
    
    /**
     * 本次消息包uuid
     */
    private String msgSn;
    
    public MessagePacker(Object sourceMessage, MessageType messageType) {
        this.sourceMessage = sourceMessage;
        this.messageType = messageType;
        this.ts = DateUtil.nowTs();
    }
    
    void setMsgSn(String msgSn) {
        this.msgSn = msgSn;
    }

    @Override
    public void run() {
        packCandy();
    }
    
    void packCandy() {
        JSONObject jsob = null;
        if (sourceMessage instanceof JSONObject) {
            jsob = (JSONObject) sourceMessage;
        } else if (sourceMessage instanceof Map) {
            jsob = new JSONObject((Map<String, Object>) sourceMessage);
        } else {
            try {
                jsob = JSON.parseObject(sourceMessage.toString());
            } catch (Exception e) {
                jsob = null;
            }
        }
        
        if (null == jsob) {
            // 不知道发的什么过来
            return;
        }
    }
    
    void packCandy0(JSONObject jsob) {
        jsob.put(Config.msgType, messageType.getType());
        jsob.put(Config.msgSn, msgSn);
        jsob.put(Config.timestamp, DateUtil.formatDataTime(ts));
    }

    void putBucket(JSONObject jsob) {
        
    }
    
}
