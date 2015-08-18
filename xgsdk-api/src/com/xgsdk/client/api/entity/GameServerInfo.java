
package com.xgsdk.client.api.entity;

public class GameServerInfo {

    private String serverId;
    private String serverName;
    private String zoneId;
    private String zoneName;

    public GameServerInfo(){
        
    }
    public GameServerInfo(String serverId,String serverName,String zoneId,String zoneName){
        this.serverId = serverId;
        this.serverName = serverName;
        this.zoneId = zoneId;
        this.zoneName = zoneName;
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
                + "]";
    }

}
