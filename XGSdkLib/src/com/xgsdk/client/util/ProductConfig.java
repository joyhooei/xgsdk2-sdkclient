
package com.xgsdk.client.util;

import com.xgsdk.client.core.util.XGLogger;

import android.app.Activity;
import android.util.Log;

public class ProductConfig {
    // xgsdk url地址
    // public static String XGSDK_RECHARGE_URL =
    // "http://onsite.recharge.xgsdk.com:8180";
    private static String XGSDK_RECHARGE_URL = "http://onsite.recharge.xgsdk.com:8180";
    private static String tmpRechargeUrl;

    public static String getRechargeUrl(Activity activity) {
        if (tmpRechargeUrl != null) {
            return tmpRechargeUrl;
        }
        return tmpRechargeUrl = PropertiesUtil.getMetaData(activity,
                "XgRechargeUrl", XGSDK_RECHARGE_URL);
    }

    public static String getNodifyUrl(Activity activity) {
        StringBuilder builder = new StringBuilder(
                ProductConfig.getRechargeUrl(activity));
        builder.append("/xgsdk/apiXgsdkPay/").append(getChannelId(activity))
                .append("Notify/");
        builder.append(getXgAppId(activity));
        Log.d("XGSDK", "nodify url=" + builder.toString());
        return builder.toString();
    }

    // xgsdk url地址
    // public static String XGSDK_AUTH_URL =
    // "http://onsite.auth.xgsdk.com:8180";
    private static String XGSDK_AUTH_URL = "http://onsite.auth.xgsdk.com:8180";
    private static String tmpAuthUrl;

    public static String getAuthUrl(Activity activity) {
        if (tmpAuthUrl != null) {
            return tmpAuthUrl;
        }
        return tmpAuthUrl = PropertiesUtil.getMetaData(activity, "XgAuthUrl",
                XGSDK_AUTH_URL);
    }

    private static String XGSDK_DATA_URL = "http://test.xgsdk.com:6001";
    private static String tmpDataUrl;

    // TODO share test
    // private static String tmpDataUrl = "http://42.62.96.66:38280";

    public static String getDataUrl(Activity activity) {
        if (tmpDataUrl != null) {
            return tmpDataUrl;
        }
        return tmpDataUrl = PropertiesUtil.getMetaData(activity, "XgDataUrl",
                XGSDK_DATA_URL);
    }

    private static String XGSDK_DOCS_URL = "http://dev.xgsdk.com";
    private static String tmpDocsUrl;

    public static String getDocsUrl(Activity activity) {
        if (tmpDocsUrl != null) {
            return tmpDocsUrl;
        }
        return tmpDocsUrl = PropertiesUtil.getXgsdkConfig(activity,
                "XgDocsUrl", XGSDK_DOCS_URL);
    }

    private static String XGSDK_UPDATE_URL = "http://test.xgsdk.com:28080";
    private static String tmpUpdateUrl;

    public static String getUpdateUrl(Activity activity) {
        if (tmpUpdateUrl != null) {
            return tmpUpdateUrl;
        }
        return tmpUpdateUrl = PropertiesUtil.getXgsdkConfig(activity,
                "XgUpdateUrl", XGSDK_UPDATE_URL);
    }

    // 创建订单URI
    public static String PAY_NEW_ORDER_URI = "/xgsdk/apiXgsdkPay/createOrder";
    // 更新订单URI
    public static String PAY_UPDATE_ORDER_URI = "/xgsdk/apiXgsdkPay/updateOrder";
    // 取消订单URI
    public static String PAY_CANCEL_ORDER_URI = "/xgsdk/apiXgsdkPay/cancelOrder";
    // 会话鉴权
    public static String ACCOUNT_VERIFY_SESSION_URI = "/xgsdk/apiXgsdkAccount/verifySession";

    public static String GET_CHANNEL_PARAM_URI = "/xgsdk/apiXgsdkPay/getChannelParam";
    // 刷新余额接口
    public static String PAY_REFRESHBALANCE_URI = "/xgsdk/apiXgsdkPay/refreshBalance";

    public static enum GAME_ENGINE {
        ANDROID, UNITY3D, COCOS2DX
    }

    private static GAME_ENGINE gameEngine = GAME_ENGINE.ANDROID;

    public static GAME_ENGINE getGameEngine() {
        return gameEngine;
    }

    public static void setGameEngine(GAME_ENGINE gameEngine) {
        ProductConfig.gameEngine = gameEngine;
        XGLogger.i("game engine={0}", gameEngine.toString());
    }

    private static final String XGSDK_CONFIG_XG_APPKEY_ID = "XgAppID";
    private static final String XGSDK_CONFIG_XG_APPKEY_KEY = "XgAppKey";
    private static final String XGSDK_CONFIG_CHANNEL_ID = "XgChannelID";
    private static String xgAppId;
    private static String xgAppKey;
    private static String channleId;

    public static String getXgAppId(Activity activity) {
        if (xgAppId != null) {
            return xgAppId;
        }
        return xgAppId = PropertiesUtil.getXgsdkConfig(activity,
                XGSDK_CONFIG_XG_APPKEY_ID, "");
    }

    public static String getXgAppKey(Activity activity) {
        if (xgAppKey != null) {
            return xgAppKey;
        }
        return xgAppKey = PropertiesUtil.getXgsdkConfig(activity,
                XGSDK_CONFIG_XG_APPKEY_KEY, "");
    }

    public static String getChannelId(Activity activity) {
        if (channleId != null) {
            return channleId;
        }
        return channleId = PropertiesUtil.getXgsdkConfig(activity,
                XGSDK_CONFIG_CHANNEL_ID, "");
    }

    private static String XGSDK_CONFIG_URL = "http://onsite.auth.xgsdk.com:8180";
    private static String tmpConfigUrl;

    public static String getConfigUrl(Activity activity) {
        if (tmpConfigUrl != null) {
            return tmpConfigUrl;
        }
        return tmpConfigUrl = PropertiesUtil.getXgsdkConfig(activity,
                "XgConfigUrl", XGSDK_CONFIG_URL);
    }

    private static String XGSDK_SHARE_PARAM_URL_PREFIX = "http://onsite.auth.xgsdk.com:8180";
    private static String shareParamUrlPrefix;

    // TODO share test
    // private static String shareParamUrlPrefix = "http://42.62.96.66:38180";

    public static String getShareParamUrlPrefix(Activity activity) {
        if (shareParamUrlPrefix != null) {
            return shareParamUrlPrefix;
        }
        return shareParamUrlPrefix = PropertiesUtil.getMetaData(activity,
                "XgConfigUrl", XGSDK_SHARE_PARAM_URL_PREFIX);
    }

    private static String XGSDK_PUSH_URL = "http://dev.xgsdk.com";
    private static String tmpPushUrl;

    public static String getPushUrl(Activity activity) {
        if (tmpPushUrl != null) {
            return tmpPushUrl;
        }
        return tmpPushUrl = PropertiesUtil.getMetaData(activity, "XgPushUrl",
                XGSDK_PUSH_URL);
    }

    public static String getXgVersion(Activity activity) {
        return PropertiesUtil.getXgsdkConfig(activity, "xgVersion",
                "1.3_sample");
    }
}
