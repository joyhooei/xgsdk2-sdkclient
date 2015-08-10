
package com.xgsdk.client.data;

public class Config {

    public static String msgType = "msgType";

    public static String msgSn = "msgSn";

    public static String timestamp = "timestamp";

    /**
     * 消息发送时间最长间隔
     */
    public static long timeOut = 5;

    /**
     * 一次消息最大条数
     */
    public static int maxMsgSize = 100;

    /**
     * 数据存放文件夹
     */
    public static String dataDir = "xgsdk_bi";

    /**
     * 数据存放文件开头
     */
    public static String dataPrefix = "cache_";

    /*************** HTTP config ********************/

    /**
     * 请求地址
     */
    public static String dataHost = "";

    public static String respSuccess = "success";

}
