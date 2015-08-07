package com.xgsdk.client.data.message;

import com.alibaba.fastjson.JSON;
import com.xgsdk.client.data.Config;



/**
 * 
 * 
 * 
 * @author yinlong
 */
public class MemBucket extends AbsBucket {

    protected MessageEntity message;
    
    public MemBucket(Head head) {
        super();
        message = new MessageEntity(head);
    }
    
    @Override
    public void addMessage(Object msg) {
        message.getContent().add(msg);
    }

    @Override
    public String getContent() {
        return JSON.toJSONString(message);
    }
    
    @Override
    public boolean isReadyToSend() {
        if(super.isReadyToSend()) {
            return true;
        }
        return message.getContent().size() > Config.maxMsgSize;
    }

    @Override
    public void onSendFinish(boolean isSuc, long time, String resp) {

    }

}
