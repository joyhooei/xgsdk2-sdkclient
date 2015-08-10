
package com.xgsdk.client.data.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgsdk.client.core.utils.MD5Util;
import com.xgsdk.client.data.Config;
import com.xgsdk.client.data.DateUtil;

/**
 * @author yinlong
 */
public class MemBucket extends AbsBucket {

    protected Head head;

    protected JSONArray content;

    protected String appKey;

    public MemBucket(Head head, String appKey) {
        super();
        this.head = head;
        this.content = new JSONArray();
    }

    @Override
    public void addMessage(Object msg) {
        content.add(msg);
    }

    @Override
    public String getContent() {
        // 加时间戳
        head.setBatchTimestamp(DateUtil.nowDataTime());
        // 加sign
        String contentStr = JSON.toJSONString(content);
        head.setSign(MD5Util.md5(contentStr + appKey));

        JSONObject ob = new JSONObject();
        ob.put("head", head);
        ob.put("content", contentStr);

        return JSON.toJSONString(ob);
    }

    @Override
    public boolean isReadyToSend() {
        if (super.isReadyToSend()) {
            return true;
        }
        return content.size() > Config.maxMsgSize;
    }

    @Override
    public void onSendFinish(boolean isSuc, long time, String resp) {

    }

}
