package com.xgsdk.client.api.data;



/**
 * 
 * 
 * @author yinlong
 *
 */
public class RoleMissionMessage extends XGDataBaseMessage {
	
	private String server;
	
	private String accountId;
	
	private String accountName;
	
	private String roleId;
	
	private String roleName;
	
	private int roleLevel;
	
	private String missionName;
	
	private String missionFlag;

	public RoleMissionMessage() {
	    super(XGDataMessageType.ROLE_MISSION);
    }

}
