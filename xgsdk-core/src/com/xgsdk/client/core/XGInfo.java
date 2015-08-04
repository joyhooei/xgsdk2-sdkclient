
package com.xgsdk.client.core;

import com.xgsdk.client.core.utils.PropertiesUtil;
import com.xgsdk.client.core.utils.XGLog;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

public class XGInfo {

    private static final String SDK_CONFIG_FILE = "sdk_config.properties";
    private static final String CONFIG_KEY_XG_APP_ID = "XgAppId";
    private static final String CONFIG_KEY_XG_APP_KEY = "XgAppKey";
    private static final String CONFIG_KEY_XG_VERSION = "XgVersion";
    private static final String CONFIG_KEY_XG_AUTH_URL = "XgAuthUrl";
    private static final String CONFIG_KEY_XG_RECHARGE_URL = "XgRechargeUrl";
    private static final String CONFIG_KEY_XG_DATA_URL = "XgDataUrl";
    private static final String CONFIG_KEY_XG_BUILD_NUMBER = "XgBuildNumber";
    private static final String CONFIG_KEY_XG_PLAN_ID = "XgPlanId";
    private static final String CONFIG_KEY_XG_PORTAL_URL = "XgPortalUrl";
    private static final String CONFIG_KEY_XG_ORIENTATITION = "XgOrientation";
    private static final String ORIENTATITION_LANDSCAPE = "1";
    private static final String ORIENTATITION_PORTRAIT = "0";

    private static final String DEFAULT_AUTH_URL = "http://onsite.auth.xgsdk.com:8180";
    private static final String DEFAULT_RECHARGE_URL = "http://onsite.recharge.xgsdk.com:8180";
    private static final String DEFAULT_PORTAL_URL = "http://www.xgsdk.com";
    private static final String DEFAULT_BUILD_NUMBER = "-1";
    private static final String DEFAULT_PLAN_ID = "-1";

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
        XG_PORTAL_URL, DEVICE_ID, XG_SDK_VERSION, XG_APP_ID, XG_APP_KEY, XG_CHANNEL_ID, XG_RECHARGE_URL, XG_AUTH_URL, XG_DATA_URL, XG_VERSION, XG_BUILD_NUMBER, XG_PLAN_ID
    }

    public static String getXGAppId(Context context) {
        return getValue(context, ConstKey.XG_APP_ID);
    }

    public static String getXGAppKey(Context context) {
        return getValue(context, ConstKey.XG_APP_KEY);
    }

    public static String getChannelId() {
        return getValue(null, ConstKey.XG_CHANNEL_ID);
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

    public static String getXGPortalUrl(Context context) {
        return getValue(context, ConstKey.XG_PORTAL_URL);
    }

    public static String getXGVersion(Context context) {
        return getValue(context, ConstKey.XG_VERSION);
    }

    public static String getXGPlanId(Context context) {
        return getValue(context, ConstKey.XG_PLAN_ID);
    }

    public static String getXGBuildNumsber(Context context) {
        return getValue(context, ConstKey.XG_BUILD_NUMBER);
    }

    public static boolean isLandspcape(Context context) {
        return TextUtils.equals(
                _getValueFromXGConfig(context, CONFIG_KEY_XG_ORIENTATITION,
                        ORIENTATITION_LANDSCAPE), ORIENTATITION_LANDSCAPE);
    }

    public static String getSdkConfig(Context context, String key,
            String defaultValue) {
        return _getValueFromXGConfig(context, key, defaultValue);
    }

    public static String getXGSdkVersion() {
        return getValue(null, ConstKey.XG_SDK_VERSION);
    }

    public static String getXGDeviceId(Context context) {
        return loadValue(context, ConstKey.DEVICE_ID);
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
            case XG_VERSION:
                result = _getValueFromXGConfig(context, CONFIG_KEY_XG_VERSION,
                        null);
            case XG_BUILD_NUMBER:
                result = _getValueFromXGConfig(context,
                        CONFIG_KEY_XG_BUILD_NUMBER, DEFAULT_BUILD_NUMBER);
                break;
            case XG_PLAN_ID:
                result = _getValueFromXGConfig(context, CONFIG_KEY_XG_PLAN_ID,
                        DEFAULT_PLAN_ID);
                break;
            case DEVICE_ID:
                result = _getDeviceId(context);
                break;
            case XG_PORTAL_URL:
                result = _getValueFromXGConfig(context,
                        CONFIG_KEY_XG_PORTAL_URL, DEFAULT_PORTAL_URL);
                break;

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

    public static void init(String sdkVersion, String channelId) {
        sValueMap.put(ConstKey.XG_SDK_VERSION, sdkVersion);
        sValueMap.put(ConstKey.XG_CHANNEL_ID, channelId);
    }

    private static String _getDeviceId(Context context) {
        {
            try {
                TelephonyManager mTelephonyMgr = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                if (mTelephonyMgr != null) {
                    String imei = mTelephonyMgr.getDeviceId();
                    if (imei != null) {
                        return "imei_" + imei;
                    }
                }
            } catch (SecurityException e) {
                XGLog.e(e.getMessage());
            }

            try {
                WifiManager wifi = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                if (wifi != null) {
                    WifiInfo info = wifi.getConnectionInfo();
                    if (info != null && info.getMacAddress() != null) {
                        return "mac_" + info.getMacAddress();
                    }
                }
            } catch (SecurityException e) {
                XGLog.e(e.getMessage());
            }

            SharedPreferences preference = context.getSharedPreferences(
                    "xgsdk", Context.MODE_PRIVATE);
            String uuid = preference.getString("UUID", null);
            if (uuid == null) {
                uuid = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("UUID", uuid);
                editor.commit();
            }
            return uuid;
        }
    }

}
