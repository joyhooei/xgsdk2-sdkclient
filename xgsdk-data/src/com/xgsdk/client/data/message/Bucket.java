package com.xgsdk.client.data.message;

public interface Bucket {
    
    boolean isReadyToSend();
    
    void addMessage(Object message);

    String getContent();
    
    void onSendFinish(boolean isSuc, long time, String resp);
    
}
