
package com.xgsdk.client.core.data;

import com.alibaba.fastjson.JSONObject;

import android.app.Activity;

import java.util.Map;

/**
 * 数据事件监控器
 * 
 * @author yinlong
 */
public interface XGDataMonitor {

    /**
     * 设置当前Activity
     * 
     * @param currentActivity
     */
    void setActivity(Activity currentActivity);

    /**
     * 游戏开始
     */
    void onResume();

    /**
     * 游戏暂停
     */
    void onPause();

    /**
     * 设备连接(启动游戏)
     * 
     * @param message
     */
    void onDeviceConnect(Object message);

    /**
     * 帐号登录
     * 
     * @param message
     */
    void onAccountLogin(Object message);

    /**
     * 角色登录
     * 
     * @param message
     */
    void onRoleLogin(Object message);
//
//    void onRoleLogin(String accountId, String accountName, String roleId,
//            String roleName, String server, String serverName, int roleLevel,
//            String loginFlag, Object ext);

    /**
     * 角色等级变化
     * 
     * @param message
     */
    void onRoleLevelUp(Object message);

    /**
     * 任务事件
     * 
     * @param message
     */
    void onRoleMission(Object message);

    /**
     * 
     * 自定义事件
     * 
     * @param eventId 自定义事件ID
     * @param eventDesc 自定义事件描述
     * @param eventVal 自定义事件值
     * @param accountId 帐号ID
     * @param accountName 帐号名称
     * @param eventBody 自定义事件内容
     * @param ext 扩展
     */
    void onEvent(String eventId, String eventDesc, int eventVal,
            String accountId, String accountName,
            Map<String, Object> eventBody, Map<String, Object> ext);

}
