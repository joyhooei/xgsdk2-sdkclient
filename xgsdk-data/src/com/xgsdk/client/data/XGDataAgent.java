
package com.xgsdk.client.data;

import com.alibaba.fastjson.JSONObject;
import com.xgsdk.client.core.data.XGDataMonitor;
import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.data.message.MessageType;

import android.app.Activity;

import java.util.Map;

/**
 * @author yinlong
 */
public class XGDataAgent implements XGDataMonitor {

    private static final String XG_DATA_TAG = "XGDATA";

    /**
     * 当前activity
     */
    private Activity activity;

    private MessagePackFactory factory;

    public XGDataAgent() {
        XGLog.i(XG_DATA_TAG, "XG Data start init");
        this.factory = new MessagePackFactory();
        XGLog.i(XG_DATA_TAG, "XG Data start over");
    }

    void handleMessage(MessagePacker packer) {
        factory.handleCandy(packer);
    }

    @Override
    public void setActivity(Activity currentActivity) {
        this.activity = currentActivity;
        this.factory.setActivity(activity);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeviceConnect(Object message) {
        MessagePacker packer = new MessagePacker(message,
                MessageType.DEVICE_CONNECT);
        handleMessage(packer);
    }

    @Override
    public void onAccountLogin(Object message) {
        MessagePacker packer = new MessagePacker(message,
                MessageType.ACCOUNT_LOGIN);
        handleMessage(packer);
    }

    @Override
    public void onRoleLogin(Object message) {
        MessagePacker packer = new MessagePacker(message,
                MessageType.ROLE_LOGIN);
        handleMessage(packer);
    }

    @Override
    public void onRoleLevelUp(Object message) {
        MessagePacker packer = new MessagePacker(message,
                MessageType.ROLE_LEVEL_CHANGE);
        handleMessage(packer);
    }

    @Override
    public void onRoleMission(Object message) {
        MessagePacker packer = new MessagePacker(message,
                MessageType.ROLE_MISSION);
        handleMessage(packer);
    }

    @Override
    public void onEvent(String eventId, String eventDesc, int eventVal,
            String accountId, String accountName,
            Map<String, Object> eventBody, Map<String, Object> ext) {
        JSONObject jsob = new JSONObject();
        jsob.put("eventId", eventId);
        jsob.put("eventDesc", eventDesc);
        jsob.put("eventVal", eventVal);
        jsob.put("accountId", accountId);
        jsob.put("accountName", accountName);
        jsob.put("eventBody", eventBody);
        
        MessagePacker packer = new MessagePacker(jsob, MessageType.CUSTOM_EVENT);
        handleMessage(packer);
    }

}
