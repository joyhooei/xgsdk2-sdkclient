
package com.xgsdk.client.api.entity;

import com.xgsdk.client.core.utils.CommonUtils;

import java.util.HashMap;

public class PayInfo {
    public static final String KEY_XG_ORDER_ID = "XG_ORDER_ID";

    private String uid;
    private String productId;
    private String productName;
    private String productDesc;
    private String productUnit;
    private int totalPrice;
    private int originalPrice;
    private int productUnitPrice;
    private int productAmount;
    private String gameTradeNo;
    private String currencyName;
    private String custom;
    private String roleId;
    private String roleName;
    private int level;
    private int vipLevel;
    private String serverId;
    private String serverName;
    private String zoneId;
    private String zoneName;
    private String gameCallbackUrl;

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

    public String getXgOrderId() {
        return additionalParams.get(KEY_XG_ORDER_ID);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(int productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getGameTradeNo() {
        return gameTradeNo;
    }

    public void setGameTradeNo(String gameTradeNo) {
        this.gameTradeNo = gameTradeNo;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
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

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getGameCallbackUrl() {
        return gameCallbackUrl;
    }

    public void setGameCallbackUrl(String gameCallbackUrl) {
        this.gameCallbackUrl = gameCallbackUrl;
    }

    @Override
    public String toString() {
        return "PayInfo [uid=" + uid + ", productId=" + productId
                + ", productName=" + productName + ", productDesc="
                + productDesc + ", productUnit=" + productUnit
                + ", totalPrice=" + totalPrice + ", originalPrice="
                + originalPrice + ", productUnitPrice=" + productUnitPrice
                + ", productAmount=" + productAmount + ", gameTradeNo="
                + gameTradeNo + ", currencyName=" + currencyName + ", custom="
                + custom + ", roleId=" + roleId + ", roleName=" + roleName
                + ", level=" + level + ", vipLevel=" + vipLevel + ", serverId="
                + serverId + ", serverName=" + serverName + ", zoneId="
                + zoneId + ", zoneName=" + zoneName + ", gameCallbackUrl="
                + gameCallbackUrl + ", additionalParams=" + additionalParams
                + "]";
    }

}
