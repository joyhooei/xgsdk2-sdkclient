
package com.xgsdk.client.entity;

import com.xgsdk.client.util.CommonUtils;

import java.util.HashMap;

public class PayInfo {
    private String uid;
    private String productId;
    private String productName;
    private String productDesc;
    private int productTotalPrice;
    private int productUnitPrice;
    private int productCount;
    private String exchangeRate;
    private String currencyName;
    private String ext;
    private String notifyURL;
    private String roleId;
    private String roleName;
    private String serverId;
    private String serverName;
    private String balance;
    private String gameOrderId;

    private HashMap<String, String> additionalParams = new HashMap<String, String>();// 扩展参数

    public String[] getAdditionalParamNames() {
        return CommonUtils.getKeysArrayFromMap(additionalParams, String.class);
    }

    public String getAdditionalParam(String name) {
        return additionalParams.get(name);
    }

    public void setAdditionalParam(String name, String value) {
        additionalParams.put(name, value);
    }

    public String getGameOrderId() {
        return gameOrderId;
    }

    public void setGameOrderId(String gameOrderId) {
        this.gameOrderId = gameOrderId;
    }

    public String getBalance() {
        return balance;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public String getExt() {
        return ext;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public int getProductCount() {
        return productCount;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductTotalPrice() {
        return productTotalPrice;
    }

    public int getProductUnitPrice() {
        return productUnitPrice;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public String getUid() {
        return uid;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductTotalPrice(int productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public void setProductUnitPrice(int productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "PayInfo [uid=" + uid + ", productId=" + productId
                + ", productName=" + productName + ", productDesc="
                + productDesc + ", productTotalPrice=" + productTotalPrice
                + ", productUnitPrice=" + productUnitPrice + ", productCount="
                + productCount + ", exchangeRate=" + exchangeRate
                + ", currencyName=" + currencyName + ", ext=" + ext
                + ", notifyURL=" + notifyURL + ", roleId=" + roleId
                + ", roleName=" + roleName + ", serverId=" + serverId
                + ", serverName=" + serverName + ", balance=" + balance
                + ", gameOrderId=" + gameOrderId + ", additionalParams="
                + additionalParams + "]";
    }

}
