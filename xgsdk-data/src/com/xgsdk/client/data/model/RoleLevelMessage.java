package com.xgsdk.client.data.model;



/**
 * 
 * 
 * @author yinlong
 *
 */
public class RoleLevelMessage extends XGDataBaseMessage {
	
	private String server;
	
	private String accountId;
	
	private String accountName;
	
	private String roleId;
	
	private String roleName;
	
	private int roleLevel;
	
	public RoleLevelMessage() {
	    super(XGDataMessageType.ROLE_LEVEL_CHANGE);
    }

}
