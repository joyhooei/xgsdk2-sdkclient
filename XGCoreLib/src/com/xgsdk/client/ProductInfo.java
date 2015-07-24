
package com.xgsdk.client;

import com.xgsdk.client.core.util.PropertiesUtil;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Properties;

public class ProductInfo {

    private static final String SDK_CONFIG_FILE = "sdk_config.properties";
    private static final String CONFIG_KEY_XG_APP_ID = "XgAppID";
    private static final String CONFIG_KEY_XG_APP_KEY = "XgAppKey";
    private static final String CONFIG_KEY_XG_CHANNEL_ID = "XgChannelID";
    private static final String CONFIG_KEY_XG_VERSION = "xgVersion";
    private static final String CONFIG_KEY_XG_AUTH_URL = "XgAuthUrl";
    private static final String CONFIG_KEY_XG_RECHARGE_URL = "XgRechargeUrl";
    private static final String CONFIG_KEY_XG_DATA_URL = "XgDataUrl";
    private static final String CONFIG_KEY_XG_UPDATE_URL = "XgUpdateUrl";

    private static final String DEFAULT_AUTH_URL = "http://onsite.auth.xgsdk.com:8180";
    private static final String DEFAULT_RECHARGE_URL = "http://onsite.recharge.xgsdk.com:8180";

    private static Properties sConfigProperties;

    public static enum GAME_ENGINE {
        ANDROID, UNITY3D, COCOS2DX
    }

    private static GAME_ENGINE sGameEngine = GAME_ENGINE.ANDROID;

    public static GAME_ENGINE getGameEngine() {
        return sGameEngine;
    }

    public static void setGameEngine(GAME_ENGINE engine) {
        if (engine != null && engine != sGameEngine) {
            sGameEngine = engine;
        }
    }

    private enum ConstKey {
        XG_APP_ID, XG_APP_KEY, XG_CHANNEL_ID, XG_RECHARGE_URL, XG_AUTH_URL, XG_DATA_URL, XG_UPDATE_URL, XG_VERSION
    }

    public static String getXGAppId(Context context) {
        return getValue(context, ConstKey.XG_APP_ID);
    }

    public static String getXGAppKey(Context context) {
        return getValue(context, ConstKey.XG_APP_KEY);
    }

    public static String getChannelId(Context context) {
        return getValue(context, ConstKey.XG_CHANNEL_ID);
    }

    public static String getXGRechargeUrl(Context context) {
        return getValue(context, ConstKey.XG_RECHARGE_URL);
    }

    public static String getXGAuthUrl(Context context) {
        return getValue(context, ConstKey.XG_AUTH_URL);
    }

    public static String getXGDataUrl(Context context) {
        return getValue(context, ConstKey.XG_DATA_URL);
    }

    public static String getXGUpdateUrl(Context context) {
        return getValue(context, ConstKey.XG_UPDATE_URL);
    }

    public static String getXGVersion(Context context) {
        return getValue(context, ConstKey.XG_VERSION);
    }

    private static final HashMap<ConstKey, String> sValueMap = new HashMap<ConstKey, String>();

    private static synchronized String getValue(Context context, ConstKey key) {
        if (key == null) {
            return null;
        }

        String result = sValueMap.get(key);
        if (result != null) {
            return result;
        }

        result = loadValue(context, key);
        sValueMap.put(key, result);
        return result;
    }

    private static String loadValue(Context context, ConstKey key) {
        String result = null;
        switch (key) {
            case XG_APP_ID:
                result = _getValueFromXGConfig(context, CONFIG_KEY_XG_APP_ID,
                        null);
                break;
            case XG_APP_KEY:
                result = _getValueFromXGConfig(context, CONFIG_KEY_XG_APP_KEY,
                        null);
                break;
            case XG_CHANNEL_ID:
                result = _getValueFromXGConfig(context,
                        CONFIG_KEY_XG_CHANNEL_ID, null);
                break;
            case XG_AUTH_URL:
                result = _getValueFromXGConfig(context, CONFIG_KEY_XG_AUTH_URL,
                        DEFAULT_AUTH_URL);
                break;
            case XG_DATA_URL:
                result = _getValueFromXGConfig(context, CONFIG_KEY_XG_DATA_URL,
                        null);
                break;
            case XG_RECHARGE_URL:
                result = _getValueFromXGConfig(context,
                        CONFIG_KEY_XG_RECHARGE_URL, DEFAULT_RECHARGE_URL);
                break;
            case XG_UPDATE_URL:
                result = _getValueFromXGConfig(context,
                        CONFIG_KEY_XG_UPDATE_URL, null);
                break;
            case XG_VERSION:
                result = _getValueFromXGConfig(context, CONFIG_KEY_XG_VERSION,
                        null);
        }
        return result;
    }

    private static String _getValueFromXGConfig(Context context, String key,
            String defaultValue) {
        if (sConfigProperties == null) {
            sConfigProperties = PropertiesUtil.loadPropertiesFromAsset(context,
                    SDK_CONFIG_FILE);
        }

        String value = sConfigProperties.getProperty(key);
        if (TextUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

}
