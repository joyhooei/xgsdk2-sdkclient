
package com.xgsdk.client.entity;

import com.xgsdk.client.core.util.CommonUtils;

import java.util.HashMap;

public class GameServerInfo {

    private String serverId;
    private String serverName;
    private String zoneId;
    private String zoneName;

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

    @Override
    public String toString() {
        return "GameServerInfo [serverId=" + serverId + ", serverName="
                + serverName + ", zoneId=" + zoneId + ", zoneName=" + zoneName
                + ", additionalParams=" + additionalParams + "]";
    }

}
