package com.xgsdk.client.api.data;

import com.alibaba.fastjson.JSONObject;



/**
 * 
 * 游戏自定义事件
 * 
 * @author yinlong
 *
 */
public class GameEventMessage extends XGDataBaseMessage {
	
	private String eventId;
	
	private String eventDesc;
	
	private int eventVal;
	
	private JSONObject eventBody;
	
	public GameEventMessage() {
		super(XGDataMessageType.CUSTOM_EVENT);
	}

}
