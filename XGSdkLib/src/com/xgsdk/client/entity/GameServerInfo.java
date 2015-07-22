
package com.xgsdk.client.entity;

import com.xgsdk.client.util.CommonUtils;

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

    private HashMap<String, String> extraData = new HashMap<String, String>();

    public void setExtraData(String key, String value) {
        extraData.put(key, value);
    }

    public String getExtraData(String key) {
        return extraData.get(key);
    }

    public String[] getExtraKeys() {
        return CommonUtils.getKeysArrayFromMap(extraData, String.class);
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
                + ", extraData=" + extraData + "]";
    }

}
