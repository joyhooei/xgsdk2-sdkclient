
package com.xgsdk.client.api.entity;

import com.xgsdk.client.core.utils.CommonUtils;

import java.util.HashMap;

public class PayInfo {
    public static final String KEY_XG_ORDER_ID = "XG_ORDER_ID";

    private String sdkUid;
    private String appGoodsId;
    private String appGoodsName;
    private String appGoodsDesc;
    private String appGoodsUnit;
    private int totalPrice;
    private int originalPrice;
    private int productUnitPrice;
    private int appGoodsAmount;
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

    public String getGameTradeNo() {
        return gameTradeNo;
    }

    public void setGameTradeNo(String gameTradeNo) {
        this.gameTradeNo = gameTradeNo;
    }

    public String getGameCallbackUrl() {
        return gameCallbackUrl;
    }

    public void setGameCallbackUrl(String gameCallbackUrl) {
        this.gameCallbackUrl = gameCallbackUrl;
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

    public String getCurrencyName() {
        return currencyName;
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

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
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

    public String getSdkUid() {
        return sdkUid;
    }

    public void setSdkUid(String sdkUid) {
        this.sdkUid = sdkUid;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAppGoodsAmount() {
        return appGoodsAmount;
    }

    public void setAppGoodsAmount(int appGoodsAmount) {
        this.appGoodsAmount = appGoodsAmount;
    }

    public String getAppGoodsId() {
        return appGoodsId;
    }

    public void setAppGoodsId(String appGoodsId) {
        this.appGoodsId = appGoodsId;
    }

    public String getAppGoodsName() {
        return appGoodsName;
    }

    public void setAppGoodsName(String appGoodsName) {
        this.appGoodsName = appGoodsName;
    }

    public String getAppGoodsDesc() {
        return appGoodsDesc;
    }

    public void setAppGoodsDesc(String appGoodsDesc) {
        this.appGoodsDesc = appGoodsDesc;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getAppGoodsUnit() {
        return appGoodsUnit;
    }

    public void setAppGoodsUnit(String appGoodsUnit) {
        this.appGoodsUnit = appGoodsUnit;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Override
    public String toString() {
        return "PayInfo [sdkUid=" + sdkUid + ", appGoodsId=" + appGoodsId
                + ", appGoodsName=" + appGoodsName + ", appGoodsDesc="
                + appGoodsDesc + ", appGoodsUnit=" + appGoodsUnit
                + ", totalPrice=" + totalPrice + ", originalPrice="
                + originalPrice + ", productUnitPrice=" + productUnitPrice
                + ", appGoodsAmount=" + appGoodsAmount + ", gameTradeNo="
                + gameTradeNo + ", currencyName=" + currencyName + ", custom="
                + custom + ", roleId=" + roleId + ", roleName=" + roleName
                + ", level=" + level + ", vipLevel=" + vipLevel + ", serverId="
                + serverId + ", serverName=" + serverName + ", zoneId="
                + zoneId + ", zoneName=" + zoneName + ", gameCallbackUrl="
                + gameCallbackUrl + ", additionalParams=" + additionalParams
                + "]";
    }

}
