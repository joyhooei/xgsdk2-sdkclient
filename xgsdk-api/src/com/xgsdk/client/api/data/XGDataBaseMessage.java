package com.xgsdk.client.api.data;


/**
 * 
 * 西瓜统计消息
 * 
 * @author yinlong
 *
 */
public abstract class XGDataBaseMessage implements XGDataMessage {

	private XGDataMessageType messageType;
	
	private long ts;
	
	private String sessionId;
	
	private Object ext;

	public XGDataBaseMessage(XGDataMessageType messageType) {
		this.messageType = messageType;
	}
	
//	@Override
//	public XGDataMessageType messageType() {
//		return messageType;
//	}
//	
//	@Override
//	public String messageSessionId() {
//		return sessionId;
//	}

}
