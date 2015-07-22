
package com.xgsdk.client.entity;

import com.xgsdk.client.util.CommonUtils;

import java.util.HashMap;

public class RoleInfo {

    private String balance;
    private String level;
    private String vipLevel;
    private String roleId;
    private String roleName;
    private String partyName;
    private String gender;

    private HashMap<String, String> extraData = new HashMap<String, String>();

    public String[] getExtraKeys() {
        return CommonUtils.getKeysArrayFromMap(extraData, String.class);
    }

    public void setExtraData(String key, String value) {
        extraData.put(key, value);
    }

    public String getExtraData(String key) {
        return extraData.get(key);
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RoleInfo [balance=" + balance + ", level=" + level
                + ", vipLevel=" + vipLevel + ", roleId=" + roleId
                + ", roleName=" + roleName + ", partyName=" + partyName
                + ", gender=" + gender + ", extraData=" + extraData + "]";
    }

}
