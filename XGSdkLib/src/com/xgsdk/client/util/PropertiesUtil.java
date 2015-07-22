
package com.xgsdk.client.util;

import com.alibaba.fastjson.JSONObject;
import com.xgsdk.client.core.util.XGLogger;

import android.app.Activity;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static JSONObject channelParams;
    private static String sdkConfigFileName = "sdk_config.properties";
    private static String sdkDataConfigFileName = "xgsdk_service_data.properties";
    private static final String sdkUpdateConfigFileName = "xgsdk_service_update.properties";
    private static Properties sdkConfig;
    private static Properties sdkDataConfig;
    private static Properties sdkUpdateConfig;

    /**
     * @param activity
     * @param key
     * @return
     */
    public static String getMetaData(Activity activity, String key,
            String defaultValue) {
        return getChannelParam(activity, key, defaultValue);
    }

    public static String getXgsdkConfig(Activity activity, String key,
            String defaultValue) {
        if (sdkConfig == null) {
            sdkConfig = getProperties(activity, sdkConfigFileName);
            boolean empty = sdkConfig.isEmpty();
            if (empty) {
                sdkConfig = null;
            }
        }
        String value = defaultValue;
        if (sdkConfig != null) {
            value = sdkConfig.getProperty(key, defaultValue);
        }
        return value;
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static String getDataConfig(Activity activity, String key,
            String defaultValue) {
        if (sdkDataConfig == null) {
            sdkDataConfig = getProperties(activity, sdkDataConfigFileName);
        }
        String value = sdkDataConfig.getProperty(key, defaultValue);

        XGLogger.d("data config [key=" + key + ",value=" + value + "]");
        return value;
    }

    public static String getUpdateConfig(Activity activity, String key,
            String defaultValue) {

        if (sdkUpdateConfig == null) {
            sdkUpdateConfig = getProperties(activity, sdkUpdateConfigFileName);
        }
        String value = sdkUpdateConfig.getProperty(key, defaultValue);

        XGLogger.d("data config [key=" + key + ",value=" + value + "]");
        return value;
    }

    public static Properties getProperties(Activity activity,
            String assetsFileName) {
        Properties properties = new Properties();
        try {
            AssetManager assets;
            // "xgsdk_service_dataCollect.properties"
            assets = activity.getAssets();
            InputStream open = assets.open(assetsFileName);
            properties.load(open);
        } catch (Exception e1) {
            XGLogger.e("getProperties error", e1.getMessage());
        }
        return properties;
    }

    public static String getXgVersion(Activity activity) {
        return getXgsdkConfig(activity, "xgVersion", "");
    }

    static String getChannelParam(Activity activity, String key,
            String defaultValue) {
        String value = "";
        // 应用宝做特殊处理，先去本地参数，取不到再去网络参数
        if ("yingyongbao".equalsIgnoreCase(ProductConfig.getChannelId(activity))) {
            value = getValueYingYongBao(activity, key, defaultValue);
        } else {
            value = getValue(activity, key, defaultValue);
        }

        if (!TextUtils.isEmpty(value)) {
            value = value.trim();
        }
        return value;
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    private static String getValue(Activity activity, String key,
            String defaultValue) {
        String value = "";
        if (null != channelParams) {
            value = channelParams.getString(key);
        }
        if (TextUtils.isEmpty(value)) {
            value = getXgsdkConfig(activity, key, defaultValue);
        }
        XGLogger.d("get config [key=" + key + ",value=" + value + "]");
        return value;
    }

    /**
     * @param key
     * @param defaultValue
     * @param printLog
     * @return
     */
    private static String getValueYingYongBao(Activity activity, String key,
            String defaultValue) {
        String value = getXgsdkConfig(activity, key, defaultValue);
        if (TextUtils.isEmpty(value) && null != channelParams) {
            value = channelParams.getString(key);
        }
        XGLogger.d("get config [key=" + key + ",value=" + value + "]");
        return value;
    }

    public static JSONObject getChannelParam(String channelId) {

        JSONObject parseJson = null;
        // try {
        // String configUrl = ProductConfig.getConfigUrl();
        // if ("false".equalsIgnoreCase(configUrl)) {
        // return new JSONObject();
        // }
        // initState = INIT_STATE_PROCESSING;
        // String xgAppId = ProductConfig.getXgAppId();
        // String xgAppKey = ProductConfig.getXgAppKey();
        //
        // long currentTimeMillis = System.currentTimeMillis();
        //
        // StringBuilder sb = new StringBuilder("channelId=");
        // sb.append(channelId).append("sdkAppid=").append(xgAppId)
        // .append("ts=").append(currentTimeMillis).append(xgAppKey);
        // String sign = Util.getMD5Str(sb.toString());
        // final StringBuilder url = new StringBuilder();
        //
        // url.append(configUrl).append(ProductConfig.GET_CHANNEL_PARAM_URI)
        // .append("/").append(channelId).append("/").append(xgAppId)
        // .append("?ts=").append(currentTimeMillis).append("&sign=")
        // .append(sign);
        //
        // Callable<String> callable = new Callable<String>() {
        // public String call() throws Exception {
        // String result = null;
        // for (int i = 0; i < 10; i++) {
        // result = executeHttpGet(url.toString(), false);
        // if (result != null) {
        // break;
        // }
        // }
        // return result;
        // }
        // };
        // FutureTask<String> future = new FutureTask<String>(callable);
        // Thread thread = new Thread(future);
        // thread.start();
        // thread.join(DEFAULT_THREAD_WAIT_TIMEOUT);
        // String retStr = future.get();
        // XGLogger.d(retStr);
        // if (Util.strIsEmpty(retStr)) {
        // initState = INIT_STATE_FAIL;
        // } else {
        // JSONObject ret = NetworkUtils.parseJson(retStr);
        // if ("1".equals(ret.getString("code"))) {
        // parseJson = ret.getJSONObject("data");
        // // initState = INIT_STATE_SUCCESS;
        // } else {
        // initState = INIT_STATE_FAIL;
        // XGSDKLog.logE("code=" + ret.getString("code") + ",msg="
        // + ret.getString("msg"));
        // return null;
        // }
        // }
        // } catch (Exception e1) {
        // XGSDKLog.logE("getChannelParam Error:" + e1.getMessage());
        // }
        return parseJson;
    }

    public static final int INIT_STATE_BEGIN = 0;
    public static final int INIT_STATE_PROCESSING = 1;
    public static final int INIT_STATE_SUCCESS = 2;
    public static final int INIT_STATE_FAIL = 3;
    private static int initState = INIT_STATE_BEGIN;

    public static boolean initChannelParam(Activity activity) {
        if (channelParams == null) {
            channelParams = getChannelParam(ProductConfig.getChannelId(activity));
        }
        if (channelParams != null) {
            return true;
        } else {
            return false;
        }

    }

}
