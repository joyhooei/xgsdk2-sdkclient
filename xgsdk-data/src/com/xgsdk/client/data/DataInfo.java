
package com.xgsdk.client.data;

public class DataInfo {

    public static enum Gender {
        FEMALE, MALE, UNKNOWN
    }

    public static enum AccountType {
        ANONYMOUS, REGISTERED, SINA_WEIBO, QQ, WEIXIN, ND91, TYPE1, TYPE2, TYPE3, TYPE4, TYPE5, TYPE6, TYPE7, TYPE8, TYPE9, TYPE10
    }

    private DataInfo() {

    }

    public static DataInfo getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        public static final DataInfo sInstance = new DataInfo();
    }

    private ReportPolicy mReportPolicy = ReportPolicy.REALTIME;

    private String serverId;
    private String accountId;
    private String roleId;
    private String level;
    private Gender gender;
    private int age;
    private AccountType accountType;
    private String accountName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public ReportPolicy getReportPolicy() {
        return mReportPolicy;
    }

    public void setReportPolicy(ReportPolicy policy) {
        mReportPolicy = policy;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getServerId() {
        return serverId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
