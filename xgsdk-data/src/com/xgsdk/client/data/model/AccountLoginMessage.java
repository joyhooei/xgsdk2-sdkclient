package com.xgsdk.client.data.model;



/**
 * 
 * 帐号登录
 * 
 * @author yinlong
 *
 */
public class AccountLoginMessage extends XGDataBaseMessage {

	/**
	 * 帐号ID
	 */
	private String accountId;
	
	/**
	 * 帐号名称
	 */
	private String accountName;
	
	public AccountLoginMessage() {
	    super(XGDataMessageType.ACCOUNT_LOGIN);
    }

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	
}
