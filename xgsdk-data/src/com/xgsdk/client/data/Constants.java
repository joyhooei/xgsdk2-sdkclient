
package com.xgsdk.client.data;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class Constants {

    public static final boolean TEST = false;

    public static final String URL = "";
    public static final String HOST = TEST ? "10.234.10.17"
    // public static final String HOST = TEST ? "10.234.41.34"
            : "receive.sdk.xoyo.com";
    public static final int PORT = TEST ? 8087 : 8089;

    public static final SimpleDateFormat SDF = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'ZZZ", Locale.getDefault());
    static {
        SDF.setTimeZone(TimeZone.getDefault());
    }

    public static final String XSJDATA_VERSION = "2.1.6";

    public static final String OS = "android";
    public static final String OS_ID = "1";
    public static final int DEFAULT_TIMEZONE = 8;

    public static String LAST_SEND_TIME = "last_send_time";
    public static final String LOG_TAG = "XSJData";

    public static final int COUNT_REPORT_THRESHOLD = 30;

    public static final int FLAG_PAUSE = 0;
    public static final int FLAG_RESUME = 1;
    public static final int FLAG_EVENT = 2;
    public static final int FLAG_EVENT_COUNT = 3;
    public static final int FLAG_ERROR = 8;
    public static final int FLAG_GAME_ACTION = 9;
    public static final int FLAG_CRASH = 10;
    public static final int FLAG_ONLINE_DETECTION = 11;
    public static final int FLAG_SDK_ERROR = -1;

    public static final String ERROR_TYPE_UNCAUGHT = "uncaught";
    public static final String ERROR_TYPE_CAUGHT = "caught";

    public static final int DATA_MAX_LENGTH = 1024 * 4;

    public static final String TRANS_TYPE_TCP = "tcp";
    public static final String TRANS_TYPE_HTTP = "http";
    public static final String TRANS_TYPE = TRANS_TYPE_TCP;

    public static final String RESPONSE_OK = "ok";

    public static final String SESSION_ID = "xsjdata_session_id";

    public static final String KEY_EDITION = "edition";
    public static final String KEY_CPU = "cpu";
    public static final String KEY_CARRIER = "carrier";
    public static final String KEY_ACCESS = "access";
    public static final String KEY_RESOLUTION = "resolution";
    public static final String KEY_TIMEZONE = "timezone";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_DEVICE_MODEL = "device_model";
    public static final String KEY_OS_VERSION = "osVersion";
    public static final String KEY_OS = "os";
    public static final String KEY_OS_ID = "osid";//1:android,2:iOS
    public static final String KEY_OS_SDK_VERSION = "ossdkv";
    public static final String KEY_BRAND = "brand";
    public static final String KEY_MEMORY = "memory";
    public static final String KEY_IMSI = "imsi";
    public static final String KEY_NETWORK = "network";
    public static final String KEY_PRODUCT = "product";
    public static final String KEY_BUILD_PRODUCT = "build_product";
    public static final String KEY_PACKAGE_NAME = "packageName";
    public static final String KEY_APP_VERSION_CODE = "versionCode";
    public static final String KEY_APPV = "appv";
    public static final String KEY_APP_VERSION_NAME = "versionName";
    public static final String KEY_LIBVERSION = "libv";
    public static final String KEY_CHANNEL = "ch";
    public static final String KEY_MODEL = "model";
    public static final String KEY_ID = "id";
    public static final String KEY_MAC = "mac";
    // public static final String KEY_DEFAULT = "df";
    // public static final String KEY_PAGE = "p";
    // public static final String KEY_ACC = "c";
    // public static final String KEY_EXTRA = "ex";
    // public static final String KEY_LABEL = "l";
    // public static final String KEY_EVENT = "e";
    public static final String KEY_SESSION = "s";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DATA_TYPE = "dataType";
    public static final String KEY_MSG = "msg";
    public static final String KEY_XSJ_VERSION = "v";
    public static final String KEY_ORDER_ID = "orderId";
    public static final String KEY_MONEY = "money";
    public static final String KEY_ORDER_TYPE = "orderType";
    public static final String KEY_PAYMENT_ID = "paymentId";
    public static final String KEY_ROLE_NAME = "roleName";
    public static final String KEY_INGOT = "ingot";
    public static final String KEY_ROLE_LEVEL = "roleLevel";
    public static final String KEY_VIP_LEVEL = "vipLevel";
    public static final String KEY_NEWBIE_STEP = "newbieStep";
    public static final String KEY_CONSUME_ID = "consumeId";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_BILLING_ID = "billingId";
    public static final String KEY_PAYWAY = "payWay";
    public static final String KEY_PAYTIME = "payTime";
    public static final String KEY_STATUS = "status";
    public static final String KEY_SERVER_ID = "serverId";
    public static final String KEY_ACCOUNT_ID = "accountId";
    public static final String KEY_ROLE_ID = "roleId";
    public static final String KEY_PRODUCT_ID = "productId";
    public static final String KEY_AMOUNT = "amount";
    public static final String PREFIX_ERROR = "err_";

    public static final String KEY_TS = "ts";
    public static final String KEY_ACTION_TIME = "action_time";
    public static final String KEY_SEND_TIME = "send_time";
    public static final String KEY_APPKEY = "appkey";
    public static final String KEY_APPID = "appid";
    public static final String KEY_CHANNEL_ID = "channelId";
    public static final String KEY_IMEI = "imei";
    public static final String KEY_ADID = "adid";
    public static final String KEY_UUID = "uuid";
    public static final String KEY_DATA = "data";
    public static final String KEY_INFO = "info";
    public static final String KEY_ACTION = "action";
    public static final String KEY_OTHER = "other";
    public static final String KEY_DATAS = "datas";
    public static final String KEY_ENV = "env";

    public static final String ACTION_GAME_OPEN = "open";
    public static final String ACTION_GAME_CLOSE = "close";
    public static final String ACTION_GAME_LOGIN = "login";
    public static final String ACTION_GAME_LOGOUT = "logout";
    public static final String ACTION_GAME_CONSUME = "consume";
    public static final String ACTION_GAME_LEVELUP = "uplevel";
    public static final String ACTION_GAME_ORDER = "order";
    public static final String ACTION_GAME_PAY = "pay";
    public static final String ACTION_ERROR = "error";
    public static final String ACTION_SDK_ERROR = "sdk_error";
    public static final String ACTION_BEHAVE = "b";

    public static final String DATA_TYPE_LAUNCH = "launch";
    public static final String DATA_TYPE_TERMINATE = "terminate";
    public static final String DATA_TYPE_ONLINE_DETECTION = "onlineDetection";

    public static final String KEY_CACHE = "cache";

    public static final String SUFFIX_COUNT = "_count";
    public static final String SUFFIX_TIME = "_time";

}
