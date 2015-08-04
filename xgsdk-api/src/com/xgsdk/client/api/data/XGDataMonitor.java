package com.xgsdk.client.api.data;

import android.app.Activity;



/**
 * 
 * 数据事件监控器
 * 
 * @author yinlong
 *
 */
public interface XGDataMonitor {
    
    void setActivity(Activity currentActivity);

    void onResume();
    
    void onPause();
    
	void onDeviceConnect(DeviceConnectMessage message);
	
	void onAccountLogin(AccountLoginMessage message);
	
	void onRoleLogin(RoleLoginMessage message);
	
	void onRoleLevelChange(RoleLevelMessage message);
	
	void onRoleMission(RoleMissionMessage message);
	
	void onEvent(GameEventMessage message);
	
	
}
