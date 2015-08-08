
package com.xgsdk.client.core.data;

import android.app.Activity;

import java.util.Map;

/**
 * 数据接口
 * 
 * @author yinlong
 */
public interface XGDataMonitor {

    /**
     * 设置当前Activity
     * 
     * @param currentActivity 当前activity
     * @param ext 扩展
     */
    void setActivity(Activity currentActivity, Object ext);

    /**
     * 游戏开始
     * 
     * @param ext 用于扩展
     */
    void onResume(Object ext);

    /**
     * 游戏暂停
     * 
     * @param ext 用于扩展
     */
    void onPause(Object ext);

    /**
     * 设备连接(启动游戏)
     * 
     * @param ext 用于扩展
     */
    void onDeviceConnect(Object ext);

    /**
     * 帐号登录
     * 
     * @param accountId 帐号ID
     * @param accountName 帐号名称
     * @param ext 用于扩展
     */
    void onAccountLogin(String accountId, String accountName, Object ext);

    /**
     * 角色登录
     * 
     * @param accountId 帐号ID
     * @param accountName 帐号名称
     * @param roleId 角色ID
     * @param roleName 角色名称
     * @param server 服务器ID
     * @param serverName 服务器名称
     * @param roleLevel 角色等级
     * @param loginFlag login, logout
     * @param ext 用于扩展
     */
    void onRoleLogin(String accountId, String accountName, String roleId,
            String roleName, String server, String serverName, int roleLevel,
            String loginFlag, Object ext);

    /**
     * 角色升级
     * 
     * @param server 服务器ID
     * @param accountId 帐号ID
     * @param accountName 帐号名称
     * @param roleId 角色ID
     * @param roleName 角色名称
     * @param roleLevel 角色等级
     * @param ext 扩展
     */
    void onRoleLevelUp(String server, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext);

    /**
     * 任务事件
     * 
     * @param server 服务器ID
     * @param accountId 帐号ID
     * @param accountName 帐号名称
     * @param roleId 角色ID
     * @param roleName 角色名称
     * @param roleLevel 角色等级
     * @param missionName 任务名称
     * @param missionFlag 关卡标识["enter"进入关卡,"success"成功退出关卡,"failed"失败退出关卡]
     * @param ext 扩展
     */
    void onRoleMission(String server, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, String missionName,
            String missionFlag, Object ext);

    /**
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
            Map<String, Object> eventBody, Object ext);

}
