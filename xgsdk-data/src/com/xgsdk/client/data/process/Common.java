
package com.xgsdk.client.data.process;

import com.xgsdk.client.ProductInfo;
import com.xgsdk.client.SystemInfo;
import com.xgsdk.client.core.util.NetworkUtil;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.data.Constants;
import com.xgsdk.client.data.DataInfo;
import com.xgsdk.client.data.PaymentType;
import com.xgsdk.client.data.handler.ProcessThread;
import com.xgsdk.client.data.network.IDataTransmitter;
import com.xgsdk.client.data.network.TransmitterFactory;
import com.xgsdk.client.data.util.JSONTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

public class Common {
    private static final String DURATION = "duration";

    private static final String SESSION_END_TIME = "end_millis";

    private static final String SESSION_START_TIME = "start_millis";

    private static final long SESSION_TIMEOUT_MS = 30 * 1000;

    public static int byteFlag;

    private static final int SEND_TRY_TIMES = 3;

    private static final HashMap<String, Integer> sEventMap = new HashMap<String, Integer>();
    private static final ArrayList<String> IMMEDIATE_ACTIONS = new ArrayList<String>();
    private static final ArrayList<String> ACCOUNT_SENSITIVE_ACTIONS = new ArrayList<String>();
    static {
        IMMEDIATE_ACTIONS.add(Constants.ACTION_GAME_CLOSE);
        IMMEDIATE_ACTIONS.add(Constants.ACTION_GAME_LOGIN);
        IMMEDIATE_ACTIONS.add(Constants.ACTION_GAME_OPEN);
        IMMEDIATE_ACTIONS.add(Constants.ACTION_GAME_LOGOUT);

        ACCOUNT_SENSITIVE_ACTIONS.add(Constants.ACTION_GAME_CONSUME);
        ACCOUNT_SENSITIVE_ACTIONS.add(Constants.ACTION_GAME_PAY);
        ACCOUNT_SENSITIVE_ACTIONS.add(Constants.ACTION_GAME_LEVELUP);
        ACCOUNT_SENSITIVE_ACTIONS.add(Constants.ACTION_GAME_LOGIN);
        ACCOUNT_SENSITIVE_ACTIONS.add(Constants.ACTION_GAME_LOGOUT);
        ACCOUNT_SENSITIVE_ACTIONS.add(Constants.ACTION_GAME_ORDER);
    }

    public static final Common INSTANCE = new Common();

    private Common() {

    }

    public static SharedPreferences getSharedPreferences(Context context) {
        String strPackageName = context.getPackageName();
        return context.getSharedPreferences("xsjdata_agent_state_"
                + strPackageName, 0);
    }

    public static boolean isNewSession(Context context) {
        SharedPreferences mStatePreferences = getSharedPreferences(context);
        long l1 = mStatePreferences.getLong(SESSION_END_TIME, -1L);
        long l2 = System.currentTimeMillis();
        return l2 - l1 > SESSION_TIMEOUT_MS;
    }

    public static String getSessionStartInfo(Context context) {
        SharedPreferences pref = getSharedPreferences(context);
        // post should terminate activities Json-info to cached file
        DataPackager.prepareTerminateJson(context, getSessionId(context));
        long ctime = System.currentTimeMillis();
        String strAppkey = ProductInfo.getXGAppKey(context);
        String newSessionId = strAppkey + String.valueOf(ctime);
        XGLogger.i("session_id is " + newSessionId);
        // open to edit
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.SESSION_ID, newSessionId);
        editor.putLong(SESSION_START_TIME, ctime);
        editor.putLong(SESSION_END_TIME, -1L);
        editor.putLong(DURATION, 0L);
        editor.commit();
        DataPackager.prepareLaunch(context, getSessionId(context));
        return newSessionId;
    }

    public static void sendCountEvent(Context context) {
        try {
            JSONObject data = getEventCountData();
            if (data == null) {
                return;
            }
            new ProcessThread(context, Constants.ACTION_BEHAVE, data, null,
                    Constants.FLAG_EVENT_COUNT).start();
            cleanEventCount();
        } catch (Exception e) {
            XGLogger.e("sendCountEvent error", e);
        }
    }

    public static void storeCountEvent(Context context) {
        try {
            JSONObject data = getEventCountData();
            if (data == null) {
                return;
            }
            JSONObject json = DataPackager.packageAction(
                    Constants.ACTION_BEHAVE, data, null);
            storeDataToCache(context, json);
            cleanEventCount();
        } catch (JSONException e) {
            XGLogger.e("JSONException in storeCountEvent ", e);
        }
    }

    public static void storeDataToCache(Context context, JSONObject dataJson)
            throws JSONException {
        ArrayList<JSONObject> datas = new ArrayList<JSONObject>();
        datas.add(dataJson);
        Common.writeToCachedFile(context, datas, true, true);
    }

    public static String getSessionExtendInfo(Context context) {
        SharedPreferences pref = getSharedPreferences(context);
        Long localLong = Long.valueOf(System.currentTimeMillis());
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(SESSION_END_TIME, localLong);
        editor.commit();
        return pref.getString(Constants.SESSION_ID, null);
    }

    public static String getSessionId(Context context) {
        SharedPreferences pref = Common.getSharedPreferences(context);
        return pref.getString(Constants.SESSION_ID, null);
    }

    private static String getDataType(JSONObject actionJSONObject) {
        String dataType = null;
        try {
            dataType = actionJSONObject.has(Constants.KEY_DATA_TYPE) ? actionJSONObject
                    .getString(Constants.KEY_DATA_TYPE) : null;
        } catch (JSONException e) {
            XGLogger.e("getDataType error: " + actionJSONObject, e);
        }
        return dataType;
    }

    public static void postToServer(Context context, JSONObject actionJson) {
        if (actionJson == null) {
            XGLogger.i("postToServer actionJson is null");
            return;
        }

        if (context == null) {
            XGLogger.i("postToServer context cannot be null");
            return;
        }

        JSONObject infoJson = getInfo(context);

        String dataType = getDataType(actionJson);
        boolean isLaunch = TextUtils.equals(dataType,
                Constants.DATA_TYPE_LAUNCH);
        boolean isTerminate = TextUtils.equals(dataType,
                Constants.DATA_TYPE_TERMINATE);
        boolean isOnlineDetection = TextUtils.equals(dataType,
                Constants.DATA_TYPE_ONLINE_DETECTION);
        boolean isNetworkAvaiable = NetworkUtil.isNetworkAvailable(context);
        boolean needToReport = needReport(context, isLaunch);

        try {
            ArrayList<JSONObject> pendingData = new ArrayList<JSONObject>();

            String action = JSONTools.fetchString(actionJson,
                    Constants.KEY_ACTION);

            checkInfo(context, actionJson, infoJson, action);

            if (!isNetworkAvaiable) {
                if (!isLaunch && !isTerminate && !isOnlineDetection
                        && !IMMEDIATE_ACTIONS.contains(action)) {
                    ArrayList<JSONObject> datas = new ArrayList<JSONObject>();
                    datas.add(actionJson);
                    writeToCachedFile(context, datas, true, true);
                }

                return;
            }

            if (!needToReport && !isLaunch && !isTerminate
                    && !isOnlineDetection
                    && !IMMEDIATE_ACTIONS.contains(action)) {
                ArrayList<JSONObject> datas = new ArrayList<JSONObject>();
                datas.add(actionJson);
                writeToCachedFile(context, datas, true, true);
                return;
            }

            String[] resp = null;
            IDataTransmitter transmitter = TransmitterFactory.getTransmitter(
                    context, Constants.TRANS_TYPE_TCP);

            // send current data
            if (!isLaunch && !isTerminate) {
                actionJson.put(Constants.KEY_INFO, infoJson);
                if (IMMEDIATE_ACTIONS.contains(action)) {
                    XGLogger.d(action + " data: \n" + actionJson);
                }
                int times = 0;
                boolean isFail = true;
                while (times < SEND_TRY_TIMES && isFail) {
                    times++;
                    resp = transmitter.send(actionJson);
                    isFail = resp == null
                            || resp.length == 0
                            || !TextUtils
                                    .equals(Constants.RESPONSE_OK, resp[0]);
                    if (isFail) {
                        XGLogger.w("try " + times + " times. send " + action
                                + " data failed: " + String.valueOf(resp));
                        if (times < SEND_TRY_TIMES) {
                            continue;
                        } else if (!isOnlineDetection && !isLaunch
                                && !isTerminate
                                && !IMMEDIATE_ACTIONS.contains(action)) {
                            pendingData.add(actionJson);
                            break;
                        }
                    } else {
                        XGLogger.w("try " + times + " times. send " + action
                                + " data succeed: " + resp[0]);
                        break;
                    }
                }
            }

            // send cached data
            JSONObject cacheJson = getCacheDatas(context);
            JSONArray cacheArray = null;
            if (cacheJson != null) {
                cacheArray = cacheJson.optJSONArray(Constants.KEY_CACHE);
            }
            if (cacheArray != null && cacheArray.length() > 0 && needToReport) {
                resp = null;

                JSONObject[] cachedMsgs = new JSONObject[cacheArray.length()];
                for (int i = 0; i < cacheArray.length(); i++) {
                    JSONObject json = cacheArray.getJSONObject(i);
                    cachedMsgs[i] = json.has(Constants.KEY_INFO) ? json : json
                            .put(Constants.KEY_INFO, infoJson);
                }

                resp = transmitter.send(cachedMsgs);
                if (resp != null && resp.length > 0) {
                    for (int i = 0; i < resp.length; i++) {
                        if (TextUtils.equals(Constants.RESPONSE_OK, resp[i])) {
                            XGLogger.w("send cached "
                                    + JSONTools.fetchString(cachedMsgs[i],
                                            Constants.KEY_ACTION)
                                    + " data succeed " + i);
                        } else {
                            XGLogger.w("send cached "
                                    + JSONTools.fetchString(cachedMsgs[i],
                                            Constants.KEY_ACTION)
                                    + " data failed: "
                                    + String.valueOf(resp[i]));
                            JSONObject json = cacheArray.getJSONObject(i);
                            if (json != null) {
                                pendingData.add(json);
                            }
                        }
                    }
                }
            }

            // save cache or delete
            writeToCachedFile(context, pendingData, true, false);

        } catch (Exception e) {
            XGLogger.e("postToServer error", e);
        }

    }

    private static void checkInfo(Context context, JSONObject actionJson,
            JSONObject infoJson, String action) throws JSONException {
        if (ACCOUNT_SENSITIVE_ACTIONS.contains(action)) {
            StringBuffer msg = new StringBuffer();
            boolean error = false;
            if (infoJson.isNull(Constants.KEY_ACCOUNT_ID)) {
                msg.append("accountId is null.");
                error = true;
            }
            if (infoJson.isNull(Constants.KEY_ROLE_ID)) {
                msg.append("roleId is null.");
                error = true;

            }
            if (infoJson.isNull(Constants.KEY_SERVER_ID)) {
                msg.append("serverId is null.");
                error = true;
            }

            if (error) {
                JSONObject errorjson = new JSONObject();
                errorjson.put(Constants.PREFIX_ERROR + Constants.KEY_INFO,
                        infoJson);
                errorjson.put(Constants.PREFIX_ERROR + Constants.KEY_ACTION,
                        actionJson);
                errorjson.put(Constants.PREFIX_ERROR + Constants.KEY_MSG,
                        msg.toString());
                errorjson.put(Constants.KEY_ENV, getDeviceData(context));
                SdkDataTools.onSdkError(context, errorjson);
            }

        }
    }

    public static boolean checkPermission(Context context, String paramString) {
        PackageManager localPackageManager = context.getPackageManager();
        return localPackageManager.checkPermission(paramString,
                context.getPackageName()) == 0;
    }

    public static String[] getConnectedNetInfo(Context context) {
        String[] arrayOfString = {
                "Unknown", "Unknown"
        };
        PackageManager localPackageManager = context.getPackageManager();
        if (localPackageManager.checkPermission(
                "android.permission.ACCESS_NETWORK_STATE",
                context.getPackageName()) != 0) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }
        ConnectivityManager localConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (localConnectivityManager == null) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }
        NetworkInfo localNetworkInfo1 = localConnectivityManager
                .getNetworkInfo(1);
        if (localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "WiFi";
            return arrayOfString;
        }
        NetworkInfo localNetworkInfo2 = localConnectivityManager
                .getNetworkInfo(0);
        if (localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "2G/3G/4G";
            arrayOfString[1] = localNetworkInfo2.getSubtypeName();
            return arrayOfString;
        }
        return arrayOfString;
    }

    public static String getCacheFilename(Context context) {
        String str = context.getPackageName();
        return "xsjdata_agent_cached_" + str;
    }

    public static void deleteCacheFile(Context context) {
        context.deleteFile(getCacheFilename(context));
    }

    public static boolean needReport(Context context, boolean isLaunch) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        boolean flag = false;
        switch (DataInfo.getInstance().getReportPolicy()) {
            case REALTIME:
                flag = true;
                break;
            case BATCH_AT_LAUNCH:
                if (isLaunch) {
                    flag = true;
                }
                break;
            case SMART:
                if (wifi.isWifiEnabled() || isLaunch) {
                    flag = true;
                }
                break;

        }
        return flag;
    }

    public static void writeToCachedFile(Context context,
            ArrayList<JSONObject> datas, boolean setInfo, boolean append) {
        String cacheFileName = getCacheFilename(context);
        FileOutputStream fos = null;
        try {
            JSONObject cacheJSONObject = getCacheDatas(context);
            if (cacheJSONObject == null) {
                cacheJSONObject = new JSONObject();
            }
            JSONArray cachedArray = append ? cacheJSONObject
                    .optJSONArray(Constants.KEY_CACHE) : new JSONArray();
            if (cachedArray == null) {
                cachedArray = new JSONArray();
            }
            JSONObject infoJson = getInfo(context);
            if (datas != null && datas.size() > 0) {
                for (JSONObject json : datas) {
                    if (setInfo && json != null
                            && !json.has(Constants.KEY_INFO)) {
                        json.put(Constants.KEY_INFO, infoJson);
                    }
                    cachedArray.put(json);
                }
            }

            cacheJSONObject.put(Constants.KEY_CACHE, cachedArray);
            fos = context.openFileOutput(cacheFileName, Context.MODE_PRIVATE);

            fos.write(cacheJSONObject.toString().getBytes());
            XGLogger.d("write to cache " + cacheJSONObject);
        } catch (Exception e) {
            XGLogger.e("write cache file error", e);
            deleteCacheFile(context);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    XGLogger.e("close resource error", e);
                }
            }
        }
    }

    public static JSONObject getInfo(Context context) {
        JSONObject json = new JSONObject();
        try {
//            json.put(Constants.KEY_APPKEY, SystemInfo.getAppKey(context));
//            json.put(Constants.KEY_APPID, SystemInfo.getAppId(context));
//            json.put(Constants.KEY_CHANNEL_ID,
//                    SystemInfo.getChannelId(context));
            json.put(Constants.KEY_IMEI,
                    SystemInfo.getDeviceUUID(context, true));
            json.put(Constants.KEY_UUID,
                    SystemInfo.getDeviceUUID(context, true));
//            json.put(Constants.KEY_ADID, SystemInfo.getAdid(context));
            json.put(Constants.KEY_XSJ_VERSION, Constants.XSJDATA_VERSION);
            json.put(Constants.KEY_OS_ID, Constants.OS_ID);
            json.put(Constants.KEY_APPV,
                    SystemInfo.getAppVersionCode(context));
            json.put(Constants.KEY_PACKAGE_NAME,
                    SystemInfo.getPackageName(context));
            json.put(Constants.KEY_APP_VERSION_CODE,
                    SystemInfo.getAppVersionCode(context));
            json.put(Constants.KEY_APP_VERSION_NAME,
                    SystemInfo.getAppVersionName(context));
            String serverId = DataInfo.getInstance().getServerId();
            if (TextUtils.isEmpty(serverId)) {
                XGLogger.e("serverId is null");
            }
            json.put(Constants.KEY_SERVER_ID, serverId);
            json.put(Constants.KEY_ACCOUNT_ID, DataInfo.getInstance()
                    .getAccountId());
            json.put(Constants.KEY_ROLE_ID, DataInfo.getInstance().getRoleId());
        } catch (JSONException e) {
            XGLogger.e("getInfo error", e);
        }
        return json;
    }

    public static JSONObject getDeviceData(Context context) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_IMEI, SystemInfo.getIMEI(context));
            json.put(Constants.KEY_OS, Constants.OS);
            json.put(Constants.KEY_OS_VERSION, Build.VERSION.SDK);
            json.put(Constants.KEY_BUILD_PRODUCT, Build.PRODUCT);
            json.put(Constants.KEY_PRODUCT,
                    Build.BRAND + " " + SystemInfo.getDeviceModel(context));
            json.put(Constants.KEY_BRAND, Build.BRAND);
            json.put(Constants.KEY_DEVICE_MODEL,
                    SystemInfo.getDeviceModel(context));
            json.put(Constants.KEY_CPU, SystemInfo.getCpu(context));
            json.put(Constants.KEY_RESOLUTION,
                    SystemInfo.getResolution(context));
            json.put(Constants.KEY_MEMORY, SystemInfo.getMemoryTotal(context));
            json.put(Constants.KEY_IMSI, SystemInfo.getIMSI(context));
            json.put(Constants.KEY_CARRIER, SystemInfo.getOperators(context));
            json.put(Constants.KEY_NETWORK, getConnectedNetInfo(context)[0]);
            json.put(Constants.KEY_PACKAGE_NAME,
                    SystemInfo.getPackageName(context));
            json.put(Constants.KEY_COUNTRY, Locale.getDefault().getCountry());
            json.put(Constants.KEY_LANGUAGE, Locale.getDefault().getLanguage());
            json.put(Constants.KEY_MAC, SystemInfo.getMacAddress(context));
            json.put(Constants.KEY_XSJ_VERSION, Constants.XSJDATA_VERSION);
            TimeZone tz = TimeZone.getDefault();
            json.put(Constants.KEY_TIMEZONE, tz.getRawOffset() / 3600000);
            json.put(Constants.KEY_APP_VERSION_CODE,
                    SystemInfo.getAppVersionCode(context));
            json.put(Constants.KEY_APP_VERSION_NAME,
                    SystemInfo.getAppVersionName(context));
        } catch (JSONException e) {
            XGLogger.e("getDeviceData error", e);
        }
        return json;
    }

    public static JSONObject getEventCountData() {
        if (sEventMap == null || sEventMap.size() == 0) {
            return null;
        }
        Set<Entry<String, Integer>> entrys = sEventMap.entrySet();
        JSONObject json = new JSONObject();
        try {
            for (Entry<String, Integer> e : entrys) {
                json.put(e.getKey(), e.getValue());
            }
        } catch (JSONException ex) {
            XGLogger.e("getEventCountData error", ex);
        }

        return json;
    }

    public static JSONObject getLoginData(Context context, String roleName,
            int ingot) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_ROLE_NAME, roleName);
            json.put(Constants.KEY_INGOT, ingot);
        } catch (JSONException e) {
            XGLogger.e("getLoginData error", e);
        }
        return json;
    }

    public static JSONObject getPayData(Context context, String orderId,
            float money, int ingot, int orderType, PaymentType payment) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_MONEY, money);
            json.put(Constants.KEY_ORDER_ID, orderId);
            json.put(Constants.KEY_INGOT, ingot);
            json.put(Constants.KEY_ORDER_TYPE, orderType);
            if (payment != null) {
                json.put(Constants.KEY_PAYMENT_ID, payment.getId());
            }
        } catch (JSONException e) {
            XGLogger.e("getPayData error", e);
        }
        return json;
    }

    public static JSONObject getUplevelData(Context context, String roleLevel,
            String newbieStep, String vipLevel) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_ROLE_LEVEL, roleLevel);
            json.put(Constants.KEY_VIP_LEVEL, vipLevel);
            json.put(Constants.KEY_NEWBIE_STEP, newbieStep);
        } catch (JSONException e) {
            XGLogger.e("getUplevelData error", e);
        }
        return json;
    }

    public static JSONObject getConsumeData(Context context, String consumeId,
            int amount, int ingot, String category) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_CONSUME_ID, consumeId);
            json.put(Constants.KEY_INGOT, ingot);
            json.put(Constants.KEY_AMOUNT, amount);
            json.put(Constants.KEY_CATEGORY, category);
        } catch (JSONException e) {
            XGLogger.e("getConsumeData error", e);
        }
        return json;
    }

    // public static JSONObject getConsumeData(Context context, String
    // consumeId,
    // String ingot, String category) {
    // JSONObject json = new JSONObject();
    // try {
    // json.put(Constants.KEY_CONSUME_ID, consumeId);
    // json.put(Constants.KEY_INGOT, ingot);
    // json.put(Constants.KEY_AMOUNT, 1);
    // json.put(Constants.KEY_CATEGORY, category);
    // } catch (JSONException e) {
    // XGLogger.e("getConsumeData error", e);
    // }
    // return json;
    // }

    public static JSONObject getOrderData(Context context, String orderId,
            String billingId, float money, int type, String payWay,
            String payTime, String status) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_ORDER_ID, orderId);
            json.put(Constants.KEY_BILLING_ID, billingId);
            json.put(Constants.KEY_MONEY, money);
            json.put(Constants.KEY_STATUS, status);
            json.put(Constants.KEY_TYPE, type);
            json.put(Constants.KEY_PAYWAY, payWay);
            json.put(Constants.KEY_PAYTIME, payTime);
        } catch (JSONException e) {
            XGLogger.e("getOrderData error", e);
        }
        return json;
    }

    public static JSONObject getOtherData(HashMap<String, String> otherMap) {

        if (otherMap == null || otherMap.size() == 0) {
            return null;
        }
        JSONObject json = new JSONObject();

        Set<String> keys = otherMap.keySet();
        try {
            for (String key : keys) {
                json.put(key, otherMap.get(key));
            }
        } catch (JSONException e) {
            XGLogger.e("getOtherData error", e);
        }

        return json;
    }

    public static JSONObject getErrorData(String errorMsg, boolean uncaught) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_TYPE,
                    uncaught ? Constants.ERROR_TYPE_UNCAUGHT
                            : Constants.ERROR_TYPE_CAUGHT);
            json.put(Constants.KEY_MSG, errorMsg);
        } catch (JSONException e) {
            XGLogger.e("getErrorData error : " + errorMsg, e);
        }
        return json;
    }

    public static JSONObject getErrorData(Context context, Throwable ex,
            boolean uncaught) {
        JSONObject errorJson = getDeviceData(context);
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            errorJson.put(Constants.KEY_TYPE,
                    uncaught ? Constants.ERROR_TYPE_UNCAUGHT
                            : Constants.ERROR_TYPE_CAUGHT);
            errorJson.put(Constants.KEY_MSG, sw.toString());
        } catch (JSONException e) {
            XGLogger.e("getErrorData error : " + ex.getMessage(), e);
        }
        return errorJson;
    }

    public static JSONObject getErrorData(Context context, String error,
            boolean uncaught) {
        JSONObject errorJson = getDeviceData(context);
        try {
            errorJson.put(Constants.KEY_TYPE,
                    uncaught ? Constants.ERROR_TYPE_UNCAUGHT
                            : Constants.ERROR_TYPE_CAUGHT);
            errorJson.put(Constants.KEY_MSG, error);
        } catch (JSONException e) {
            XGLogger.e("getErrorData error : " + error, e);
        }
        return errorJson;
    }

    public static JSONObject getSDKErrorData(Context context,
            JSONObject errorInfo) {
        JSONObject errorJson = new JSONObject();
        try {
            errorJson.put(Constants.KEY_MSG, errorInfo.toString());
        } catch (Exception e) {
            XGLogger.e("getSDKErrorData error : " + errorInfo, e);
        }
        return errorJson;
    }

    public static JSONObject getCacheDatas(Context context) {
        String cachefilename = Common.getCacheFilename(context);
        FileInputStream fis = null;
        try {
            File file = context.getFileStreamPath(cachefilename);
            if (file == null || !file.exists()) {
                return null;
            }
            fis = context.openFileInput(cachefilename);
            String strCacheInfo = "";
            byte[] arrayOfByte = new byte[16384];
            int i = 0;
            while ((i = fis.read(arrayOfByte)) != -1) {
                strCacheInfo = strCacheInfo + new String(arrayOfByte, 0, i);
            }
            if (TextUtils.isEmpty(strCacheInfo)) {
                return null;
            }
            return new JSONObject(strCacheInfo);

        } catch (Exception e) {
            XGLogger.e("getCacheDatas error", e);
            deleteCacheFile(context);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                XGLogger.e("getCacheDatas close resouce error", e);
            }
        }
        return null;
    }

    public static JSONObject getEventData(String eventId, int count) {
        JSONObject json = new JSONObject();
        try {
            json.put(eventId, count);
        } catch (JSONException e) {
            XGLogger.e("getEventData error", e);
        }
        return json;
    }

    public static void eventCount(Context context, String eventId) {
        int count = 1;
        if (sEventMap.containsKey(eventId)) {
            count += sEventMap.get(eventId);
        }
        sEventMap.put(eventId, count);
    }

    public static int getEventCount(Context context, String eventId) {
        String key = eventId;
        return sEventMap.containsKey(key) ? sEventMap.get(key) : 0;
    }

    public synchronized static void cleanEventCount() {
        sEventMap.clear();
    }

}
