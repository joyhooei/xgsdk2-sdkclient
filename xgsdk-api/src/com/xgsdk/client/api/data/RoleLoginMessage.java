package com.xgsdk.client.api.data;



/**
 * 
 * 
 * @author yinlong
 *
 */
public class RoleLoginMessage extends XGDataBaseMessage {

	private String server;
	
	private String accountId;
	
	private String accountName;
	
	private String roleId;
	
	private String roleName;
	
	private String roleLevel;
	
	// TODO: what's mean?
	private String loginFlag;
	
	public RoleLoginMessage() {
	    super(XGDataMessageType.ROLE_LOGIN);
    }

}
