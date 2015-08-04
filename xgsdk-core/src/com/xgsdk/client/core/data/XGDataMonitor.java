package com.xgsdk.client.core.data;

import android.app.Activity;



/**
 * 
 * 数据事件监控器
 * 
 * @author yinlong
 *
 */
public interface XGDataMonitor {
    
    /**
     * 
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
	
	/**
	 * 角色等级变化
	 * 
	 * @param message
	 */
	void onRoleLevelChange(Object message);
	
	/**
	 * 任务事件
	 * 
	 * @param message
	 */
	void onRoleMission(Object message);
	
	/**
	 * 自定义事件
	 * 
	 * @param message
	 */
	void onEvent(Object message);
	
	
}
