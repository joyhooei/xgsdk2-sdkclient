package com.xgsdk.client.data.message;


/**
 * 西瓜统计消息类型
 * 
 * @author yinlong
 *
 */
public enum MessageType {

	/**
	 * 设备连接
	 */
	DEVICE_CONNECT("device.connect"),
	
	/**
	 * 帐号登录
	 */
	ACCOUNT_LOGIN("account.login"),
	
	/**
	 * 角色登录
	 */
	ROLE_LOGIN("role.login"),
	
	/**
	 * 角色等级变更
	 */
	ROLE_LEVEL_CHANGE("role.level"),
	
	/**
	 * 任务
	 */
	ROLE_MISSION("role.mission"),
	
	/**
	 * 自定义事件
	 */
	CUSTOM_EVENT("custom.event"),
	
	;
	
	private String type;
	
	private MessageType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
