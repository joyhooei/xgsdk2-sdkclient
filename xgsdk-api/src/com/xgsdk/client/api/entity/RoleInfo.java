
package com.xgsdk.client.api.entity;

public class RoleInfo {

    private String balance;
    private int level;
    private int vipLevel;
    private String roleId;
    private String roleName;
    private String partyName;
    private String gender;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
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
                + ", gender=" + gender + "]";
    }

}
