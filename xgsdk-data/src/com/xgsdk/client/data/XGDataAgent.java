
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
    public void setActivity(Activity currentActivity, Object ext) {
        this.activity = currentActivity;
        this.factory.setActivity(activity);
    }

    @Override
    public void onDeviceConnect(Object ext) {
        MessagePacker packer = new MessagePacker(ext, MessageType.DEVICE_CONNECT);
        handleMessage(packer);
    }

    @Override
    public void onAccountLogin(String accountId, String accountName, Object ext) {
        JSONObject jsob = new JSONObject();
        jsob.put("accountId", accountId);
        jsob.put("accountName", accountName);
        MessagePacker packer = new MessagePacker(jsob, MessageType.ACCOUNT_LOGIN);
        handleMessage(packer);
    }

    @Override
    public void onRoleLogin(String accountId, String accountName,
            String roleId, String roleName, String server, String serverName,
            int roleLevel, String loginFlag, Object ext) {
        JSONObject jsob = new JSONObject();
        jsob.put("accountId", accountId);
        jsob.put("accountName", accountName);
        jsob.put("roleId", roleId);
        jsob.put("roleName", roleName);
        jsob.put("server", server);
        jsob.put("serverName", serverName);
        jsob.put("roleLevel", roleLevel);
        jsob.put("loginFlag", loginFlag);
        MessagePacker packer = new MessagePacker(jsob, MessageType.ROLE_LOGIN);
        handleMessage(packer);
    }

    @Override
    public void onRoleLevelUp(String server, String accountId,
            String accountName, String roleId, String roleName, int roleLevel,
            Object ext) {
        JSONObject jsob = new JSONObject();
        jsob.put("server", server);
        jsob.put("accountId", accountId);
        jsob.put("accountName", accountName);
        jsob.put("roleId", roleId);
        jsob.put("roleName", roleName);
        jsob.put("roleLevel", roleLevel);
        MessagePacker packer = new MessagePacker(jsob, MessageType.ROLE_LEVEL_CHANGE);
        handleMessage(packer);
    }

    @Override
    public void onRoleMission(String server, String accountId,
            String accountName, String roleId, String roleName, int roleLevel,
            String missionName, String missionFlag, Object ext) {
        JSONObject jsob = new JSONObject();
        jsob.put("server", server);
        jsob.put("accountId", accountId);
        jsob.put("accountName", accountName);
        jsob.put("roleId", roleId);
        jsob.put("roleName", roleName);
        jsob.put("roleLevel", roleLevel);
        jsob.put("missionName", missionName);
        jsob.put("missionFlag", missionFlag);
        MessagePacker packer = new MessagePacker(jsob, MessageType.ROLE_MISSION);
        handleMessage(packer);
    }

    @Override
    public void onEvent(String eventId, String eventDesc, int eventVal,
            String accountId, String accountName,
            Map<String, Object> eventBody, Object ext) {
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

    @Override
    public void onResume(Object ext) {
        factory.start();;
    }

    @Override
    public void onPause(Object ext) {
        // 游戏暂停时,这次会话结束,赶紧把所有事情都搞定
        factory.tryOver();
    }

}
