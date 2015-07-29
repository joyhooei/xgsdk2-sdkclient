
package com.xgsdk.client;

import com.xgsdk.client.core.util.MD5Util;
import com.xgsdk.client.core.util.XGLogger;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SystemInfo {

    private static final String LOG_TAG = "ConstInfo";

    private static final HashMap<String, String> OPERATORS_MAP = new HashMap<String, String>();
    static {
        OPERATORS_MAP.put("46000", "中国移动");
        OPERATORS_MAP.put("46002", "中国移动");
        OPERATORS_MAP.put("46001", "中国联通");
        OPERATORS_MAP.put("46003", "中国电信");
    }

    private enum ConstKey {
        DEVICE_ID, SDK_VERSION, APP_VERSION_NAME, APP_VERSION_CODE, APP_NAME, PACKAGE_NAME, RESOLUTION, DEVICE_MODEL, DEVICE_UUID, DEVICE_UUID_HASH, OPERATORS, PROCESS_NAME, PHONE_NUMBER, PHONE_IMEI, PHONE_IMSI, MAC, CPU, MEMORY, LOCAL_IP
    }

    public static String getSdkVersion(Context context) {
        return getValue(context, ConstKey.SDK_VERSION);
    }

    public static String getAppVersionName(Context context) {
        return getValue(context, ConstKey.APP_VERSION_NAME);
    }

    public static int getAppVersionCode(Context context) {
        String code = getValue(context, ConstKey.APP_VERSION_CODE);
        return Integer.parseInt(code);
    }

    public static String getAppName(Context context) {
        return getValue(context, ConstKey.APP_NAME);
    }

    public static String getPackageName(Context context) {
        return getValue(context, ConstKey.PACKAGE_NAME);
    }

    public static String getResolution(Context context) {
        return getValue(context, ConstKey.RESOLUTION);
    }

    public static String getDeviceModel(Context context) {
        return getValue(context, ConstKey.DEVICE_MODEL);
    }

    public static String getDeviceUUID(Context context, boolean hash) {
        if (hash) {
            return getValue(context, ConstKey.DEVICE_UUID_HASH);
        } else {
            return getValue(context, ConstKey.DEVICE_UUID);
        }
    }

    public static String getOperators(Context context) {
        return getValue(context, ConstKey.OPERATORS);
    }

    public static String getProcessName(Context context) {
        return getValue(context, ConstKey.PROCESS_NAME);
    }

    public static String getPhoneNumber(Context context) {
        return getValue(context, ConstKey.PHONE_NUMBER);
    }

    public static String getIMEI(Context context) {
        return getValue(context, ConstKey.PHONE_IMEI);
    }

    public static String getIMSI(Context context) {
        return getValue(context, ConstKey.PHONE_IMSI);
    }

    public static String getMacAddress(Context context) {
        return getValue(context, ConstKey.MAC);
    }

    public static String getCpu(Context context) {
        return getValue(context, ConstKey.CPU);
    }

    public static String getMemoryTotal(Context context) {
        return getValue(context, ConstKey.MEMORY);
    }

    public static String getLocalIP(Context context) {
        return getValue(context, ConstKey.LOCAL_IP);
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
            case SDK_VERSION:
                result = _getSdkVersion(context);
                break;
            case APP_VERSION_NAME:
                result = _getAppVersion(context);
                break;
            case APP_VERSION_CODE:
                result = _getAppVersionCode(context);
                break;
            case APP_NAME:
                result = _getAppName(context);
                break;
            case PACKAGE_NAME:
                result = _getAppPackage(context);
                break;
            case RESOLUTION:
                result = _getScreenSize(context);
                break;
            case DEVICE_MODEL:
                result = _getDeviceModel(context);
                break;
            case DEVICE_UUID:
                result = _getDeviceUUID(context);
                break;
            case DEVICE_UUID_HASH:
                result = MD5Util.subMd5(_getDeviceUUID(context));
                break;
            case OPERATORS:
                result = _getOperators(context);
                break;
            case PROCESS_NAME:
                result = _getProcessName(context);
                break;
            case PHONE_NUMBER:
                result = _getPhoneNumber(context);
                break;
            case PHONE_IMEI:
                result = _getPhoneIMEI(context);
                break;
            case PHONE_IMSI:
                result = _getPhoneIMSI(context);
                break;
            case MAC:
                result = _getMacAddress(context);
                break;
            case CPU:
                result = _getCpu();
                break;
            case MEMORY:
                result = _getMemory();
                break;
            case LOCAL_IP:
                result = _getLocalIP();
                break;
            case DEVICE_ID:
                result = _getDeviceId(context);
                break;
            default:
                result = null;
        }

        return result;
    }

    private static void loadAppInfo(Context context) {
        try {
            PackageManager pmg = context.getPackageManager();
            ApplicationInfo info = pmg.getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);

            CharSequence label = info.loadLabel(pmg);

            sValueMap.put(ConstKey.APP_NAME, label == null ? info.packageName
                    : label.toString());

            Bundle bundle = info.metaData;
            if (bundle == null) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                XGLogger.e(e.getMessage());
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
                XGLogger.e(e.getMessage());
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

    private static String _getSdkVersion(Context context) {
        return Build.VERSION.RELEASE;
    }

    private static String _getAppVersion(Context context) {
        loadPackageInfo(context);
        return sValueMap.get(ConstKey.APP_VERSION_NAME);
    }

    private static String _getAppVersionCode(Context context) {
        loadPackageInfo(context);
        return sValueMap.get(ConstKey.APP_VERSION_CODE);
    }

    private static String _getAppName(Context context) {
        loadAppInfo(context);
        return sValueMap.get(ConstKey.APP_NAME);
    }

    private static String _getAppPackage(Context context) {
        return context.getPackageName();
    }

    private static void loadPackageInfo(Context context) {
        String packageName = context.getPackageName();
        sValueMap.put(ConstKey.PACKAGE_NAME, packageName);

        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            String version = info.versionName;
            if (version == null) {
                version = "code(" + info.versionCode + ")";
            }
            sValueMap.put(ConstKey.APP_VERSION_NAME, version);
            sValueMap.put(ConstKey.APP_VERSION_CODE,
                    String.valueOf(info.versionCode));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String _getScreenSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return String
                .format("%d*%d", metrics.widthPixels, metrics.heightPixels);
    }

    private static String _getDeviceModel(Context context) {
        return Build.MODEL;
    }

    private static String _getDeviceUUID(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = null;
        String tmMac = null;
        String androidId = null;
        try {
            tmDevice = tm.getDeviceId();
            String deviceId2 = tm.getDeviceId();
            if (!TextUtils.equals(tmDevice, deviceId2)) {
                tmDevice = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tmMac = _getMacAddress(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            androidId = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tmDevice == null) {
            tmDevice = "";
        }
        if (tmMac == null) {
            tmMac = "";
        }
        if (androidId == null) {
            androidId = "";
        }
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmMac.hashCode());
        return deviceUuid.toString();
    }

    private static String _getOperators(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String operatorName = OPERATORS_MAP.get(tm == null ? "" : tm
                .getSimOperator());
        return operatorName == null ? "" : operatorName;
    }

    private static String _getProcessName(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> infos = activityManager
                .getRunningAppProcesses();
        int pid = Process.myPid();
        RunningAppProcessInfo myInfo = null;
        for (RunningAppProcessInfo info : infos) {
            if (info.pid == pid) {
                myInfo = info;
                break;
            }
        }

        return myInfo == null ? null : myInfo.processName;
    }

    private static String _getPhoneNumber(Context context) {
        String result = null;
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? "" : result;
    }

    private static String _getPhoneIMEI(Context context) {
        String result = null;
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getDeviceId();
            String deviceId2 = tm.getDeviceId();
            if (!TextUtils.equals(result, deviceId2)) {
                result = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result == null ? "" : result;
    }

    private static String _getPhoneIMSI(Context context) {
        String result = null;
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result == null ? "" : result;
    }

    private static String _getMacAddress(Context context) {
        String str = null;
        try {
            WifiManager localWifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
            str = localWifiInfo.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static String _getCpu() {
        String str = null;
        FileReader localFileReader = null;
        BufferedReader localBufferedReader = null;
        try {
            localFileReader = new FileReader("/proc/cpuinfo");
            localBufferedReader = new BufferedReader(localFileReader, 1024);
            str = localBufferedReader.readLine();

        } catch (FileNotFoundException localFileNotFoundException) {
            Log.e(LOG_TAG, "Could not open file /proc/cpuinfo",
                    localFileNotFoundException);
        } catch (IOException localIOException) {
            Log.e(LOG_TAG, "Could not read from file /proc/cpuinfo",
                    localIOException);
        } finally {
            try {
                if (localFileReader != null) {
                    localFileReader.close();

                }
                if (localBufferedReader != null) {
                    localBufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (str != null) {
            int i = str.indexOf(':') + 1;
            str = str.substring(i);
        }
        return str.trim();
    }

    private static String _getMemory() {
        String str = null;
        FileReader localFileReader = null;
        BufferedReader localBufferedReader = null;
        try {
            localFileReader = new FileReader("/proc/meminfo");
            localBufferedReader = new BufferedReader(localFileReader, 1024);
            str = localBufferedReader.readLine();

        } catch (FileNotFoundException localFileNotFoundException) {
            Log.e(LOG_TAG, "Could not open file /proc/meminfo",
                    localFileNotFoundException);
        } catch (IOException localIOException) {
            Log.e(LOG_TAG, "Could not read from file /proc/meminfo",
                    localIOException);
        } finally {
            try {
                if (localFileReader != null) {
                    localFileReader.close();

                }
                if (localBufferedReader != null) {
                    localBufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (str != null) {
            int i = str.indexOf(':') + 1;
            str = str.substring(i);
        }
        return str.trim();
    }

    private static String _getLocalIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(LOG_TAG, "getLocalIp error ", ex);
        }
        return null;
    }

}
