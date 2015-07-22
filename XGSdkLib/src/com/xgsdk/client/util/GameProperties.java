
package com.xgsdk.client.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameProperties {

    public static final String KEY_APP_KEY = "appKey";
    public static final String KEY_APP_ID = "appId";
    public static final String KEY_CP_ID = "cpId";
    public static final String KEY_GAME_ID = "gameId";
    public static final String KEY_SERVER_ID = "serverId";
    public static final String KEY_ISPORTRAIT = "portrait";
    public static final String KEY_ISDEBUGMODEL = "debug";
    public static final String KEY_GAME_ACCOUNT_TITLE = "gameAccountTitle";
    public static final String KEY_GAME_ACCOUNT_ENABLE = "gameAccountEnable";
    public static final String KEY_MERCHANT_ID = "merchantId";
    public static final String KEY_PAYMENT_KEY = "paymentKey";
    public static final String KEY_SERVER_SEQNUM = "serverSeqNum";
    public static final String KEY_SECRET = "appSecret";
    public static final String KEY_GAME_NAME = "gameName";
    public static final String KEY_CP_NAME = "cpName";
    public static final String KEY_PAYMENT_NOTIFY_URL = "paymentNotifyURL";
    public static final String KEY_SPLASH_PIC_COUNT = "splashPicCount";
    public static final String KEY_SPLASH_DURATION_MS = "splashDurationMS";

    public static final String KEY_XSJ_APPKEY = "xsjAppKey";
    public static final String KEY_XSJ_APPID = "xsjAppId";
    public static final String KEY_XSJ_CHANNELID = "xsjChannelId";
    public static final String KEY_XSJ_ADID = "xsjAdId";
    public static final String KEY_XSJ_UPGRADE_ENABLE = "xsjUpgradeEnable";

    public static final String KEY_GAME_PROPERTIES = "xsjgame.properties";

    private static Properties sProperties;
    private static AssetManager mAssetManager;

    public static final int getSplashPicCount(Context countext) {
        String value = getValue(countext, KEY_SPLASH_PIC_COUNT, "0");
        return Integer.parseInt(value);
    }

    public static final String getPymentNotifyUrl(Context context) {
        return getValue(context, KEY_PAYMENT_NOTIFY_URL, null);
    }

    public static final String getGameName(Context context) {
        return getValue(context, KEY_GAME_NAME, null);
    }

    public static final String getCpName(Context context) {
        return getValue(context, KEY_CP_NAME, null);
    }

    public static final String getXSJAppKey(Context context) {
        return getValue(context, KEY_XSJ_APPKEY, null);
    }

    public static final String getXSJAppId(Context context) {
        return getValue(context, KEY_XSJ_APPID, null);
    }

    public static final String getXSJChannelId(Context context) {
        return getValue(context, KEY_XSJ_CHANNELID, null);
    }

    public static final String getXSJAdid(Context context) {
        return getValue(context, KEY_XSJ_ADID, null);
    }

    public static String getServerSeqNum(Context context) {
        return getValue(context, KEY_SERVER_SEQNUM, null);
    }

    public static String getCpId(Context context) {
        return getValue(context, KEY_CP_ID, null);
    }

    public static String getGameId(Context context) {
        return getValue(context, KEY_GAME_ID, null);
    }

    public static String getServerId(Context context) {
        return getValue(context, KEY_SERVER_ID, null);
    }

    public static String getMerchantId(Context context) {
        return getValue(context, KEY_MERCHANT_ID, null);
    }

    public static String getAppKey(Context context) {
        return getValue(context, KEY_APP_KEY, null);
    }

    public static String getAppId(Context context) {
        return getValue(context, KEY_APP_ID, null);
    }

    public static String getGameAccountTitle(Context context) {
        return getValue(context, KEY_GAME_ACCOUNT_TITLE, null);
    }

    public static boolean isGameAccountEnable(Context context) {
        String value = getValue(context, KEY_GAME_ACCOUNT_ENABLE,
                Boolean.FALSE.toString());
        return Boolean.parseBoolean(value);
    }

    public static boolean isXSJUpgradeEnable(Context context) {
        String value = getValue(context, KEY_XSJ_UPGRADE_ENABLE,
                Boolean.FALSE.toString());
        return Boolean.parseBoolean(value);
    }

    public static boolean isPortrait(Context context) {
        String value = getValue(context, KEY_ISPORTRAIT,
                Boolean.FALSE.toString());
        return Boolean.parseBoolean(value);
    }

    public static boolean isDebugModel(Context context) {
        String value = getValue(context, KEY_ISDEBUGMODEL,
                Boolean.FALSE.toString());
        return Boolean.parseBoolean(value);
    }

    public static String getSecret(Context context) {
        String value = getValue(context, KEY_SECRET, null);
        return value;
    }

    public static int getSplashDuration(Context context, int defaultValue) {
        String value = getValue(context, KEY_SPLASH_DURATION_MS, null);
        return TextUtils.isEmpty(value) ? defaultValue : Integer
                .parseInt(value);
    }

    private static void loadProperties(Context context) {
        if (sProperties == null) {
            synchronized (GameProperties.class) {
                if (sProperties == null) {
                    sProperties = new Properties();
                    mAssetManager = context.getAssets();
                    InputStream is = null;
                    try {
                        is = mAssetManager.open(KEY_GAME_PROPERTIES);
                        sProperties.load(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }

    public static String getValue(Context context, String key,
            String defaultValue) {
        loadProperties(context);
        String value = sProperties.getProperty(key, defaultValue);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }

}
