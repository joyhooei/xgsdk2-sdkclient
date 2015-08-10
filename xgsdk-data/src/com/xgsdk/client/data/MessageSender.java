
package com.xgsdk.client.data;

import com.xgsdk.client.core.http.HttpUtils;
import com.xgsdk.client.core.http.IHttpExecuteCallback;
import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.data.message.Bucket;

public class MessageSender implements Runnable, IHttpExecuteCallback {

    private Bucket bucket;

    private Boolean isSuc;

    private long startTs;

    private long overTs;

    public MessageSender(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        String content = bucket.getContent();
        try {
            startTs = DateUtil.nowTs();
            HttpUtils.executeHttpPost(Config.dataHost, content, this);
        } catch (Throwable t) {
            XGLog.e("error in send data http post", t);
            isSuc = false;
        }
    }

    @Override
    public void callback(int code, String data) {
        overTs = DateUtil.nowTs();
        if (200 == code) {
            isSuc = true;
        }
        bucket.onSendFinish(isSuc, overTs - startTs, data);
    }

}
