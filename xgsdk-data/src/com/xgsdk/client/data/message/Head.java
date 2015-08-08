package com.xgsdk.client.data.message;



/**
 * 
 * 消息头
 * 
 * @author yinlong
 */
public class Head {
    
    /*
     *  "head": {
        "batchTimestamp": "2014-12-07 19:17:29.001+08", 
        "datasource": "client", 
        "sign": "0ca175b9c0f726a831d895e269332461", 
        "channel": "mi", 
        "deviceId": "599a830eb0019b421ce35ec4a80e0e71", 
        "os": "android", 
        "network": "wifi", 
        "osVersion": "3.4b5", 
        "timezone": 8, 
        "appVersionCode": 1, 
        "appVersion": "1.0", 
        "carrier": "中国移动", 
        "country": "CN", 
        "language": "zh", 
        "xgVersion": "2.0", 
        "appId": "1024", 
        "cpuFreq": "MSM8974 2457600", 
        "memTotal": "2931056", 
        "deviceBrand": "Xiaomi", 
        "deviceModel": "MI 4LTE", 
        "cpu": "ARMv7 Processor rev 2 (v7l)", 
        "imei": "869630010064289", 
        "imsi": "460029015211772", 
        "mac": "c4:6a:b7:a3:92:74", 
        "deviceScreen": "720*1280", 
        "isJailBroken": "F", 
        "isPirated": "F"
    }
     */
    
    //////////////////////////////////////////////
    
    // 需要及时更新的字段
    
    private String sign;
    
    private String network;
    
    private String carrier;

    ///////////////////////////////////////////////
    
    private String batchDataId;
    
    private String batchTimestamp;
    
    private String datasource;    
    
    private String channel;
    
    private String deviceId;
    
    private String os;
    
    private String osVersion;
    
    private int timezone;
    
    private int appVersionCode;
    
    private String appVersion;
    
    private String country;
    
    private String language;
    
    private String xgVersion;
    
    private String appId;
    
    private String cpuFreq;
    
    private String memTotal;
    
    private String deviceBrand;
    
    private String deviceModel;
    
    private String cpu;
    
    private String imei;
    
    private String imsi;
    
    private String mac;
    
    private String deviceScreen;

    /**
     * @return the datasource
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * @param datasource the datasource to set
     */
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the os
     */
    public String getOs() {
        return os;
    }

    /**
     * @param os the os to set
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * @return the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * @return the osVersion
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * @param osVersion the osVersion to set
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    /**
     * @return the timezone
     */
    public int getTimezone() {
        return timezone;
    }

    /**
     * @param timezone the timezone to set
     */
    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    /**
     * @return the appVersionCode
     */
    public int getAppVersionCode() {
        return appVersionCode;
    }

    /**
     * @param appVersionCode the appVersionCode to set
     */
    public void setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    /**
     * @return the appVersion
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * @param appVersion the appVersion to set
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * @return the carrier
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * @param carrier the carrier to set
     */
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the xgVersion
     */
    public String getXgVersion() {
        return xgVersion;
    }

    /**
     * @param xgVersion the xgVersion to set
     */
    public void setXgVersion(String xgVersion) {
        this.xgVersion = xgVersion;
    }

    /**
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return the cpuFreq
     */
    public String getCpuFreq() {
        return cpuFreq;
    }

    /**
     * @param cpuFreq the cpuFreq to set
     */
    public void setCpuFreq(String cpuFreq) {
        this.cpuFreq = cpuFreq;
    }

    /**
     * @return the memTotal
     */
    public String getMemTotal() {
        return memTotal;
    }

    /**
     * @param memTotal the memTotal to set
     */
    public void setMemTotal(String memTotal) {
        this.memTotal = memTotal;
    }

    /**
     * @return the deviceBrand
     */
    public String getDeviceBrand() {
        return deviceBrand;
    }

    /**
     * @param deviceBrand the deviceBrand to set
     */
    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    /**
     * @return the deviceModel
     */
    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * @param deviceModel the deviceModel to set
     */
    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /**
     * @return the cpu
     */
    public String getCpu() {
        return cpu;
    }

    /**
     * @param cpu the cpu to set
     */
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    /**
     * @return the imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * @param imei the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @return the imsi
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * @param imsi the imsi to set
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return the deviceScreen
     */
    public String getDeviceScreen() {
        return deviceScreen;
    }

    /**
     * @param deviceScreen the deviceScreen to set
     */
    public void setDeviceScreen(String deviceScreen) {
        this.deviceScreen = deviceScreen;
    }

    /**
     * @return the batchDataId
     */
    public String getBatchDataId() {
        return batchDataId;
    }

    /**
     * @param batchDataId the batchDataId to set
     */
    public void setBatchDataId(String batchDataId) {
        this.batchDataId = batchDataId;
    }

    /**
     * @return the batchTimestamp
     */
    public String getBatchTimestamp() {
        return batchTimestamp;
    }

    /**
     * @param batchTimestamp the batchTimestamp to set
     */
    public void setBatchTimestamp(String batchTimestamp) {
        this.batchTimestamp = batchTimestamp;
    }

}
