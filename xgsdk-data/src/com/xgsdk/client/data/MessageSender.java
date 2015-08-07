package com.xgsdk.client.data;

import com.xgsdk.client.core.http.HttpUtils;
import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.data.message.Bucket;

public class MessageSender implements Runnable {
    
    private Bucket bucket;
    
    public MessageSender(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        String content = bucket.getContent();
        String resp = null;
        boolean isSuc = false;
        try {            
            resp = HttpUtils.executeHttpPost(Config.dataHost, content);
        } catch (Throwable t) {
            XGLog.e("error in send data http post", t);
            isSuc = false;
        }
        XGLog.d("data send resp: " + resp);
        
        if (null == resp) {
            isSuc = false;
        }
    }

}
