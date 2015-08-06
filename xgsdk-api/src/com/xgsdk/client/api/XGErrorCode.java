
package com.xgsdk.client.api;

import android.util.SparseArray;

public class XGErrorCode {
    public static final int SUCCESS = 200;// 成功
    public static final int INIT_FAILED = 1000;// 客户端初始化失败
    public static final int LOGIN_FAILED = 1100;// 客户端登录失败
    public static final int SWITCHACCOUNT_FAILED = 1200;// 客户端切换账号失败
    public static final int LOGOUT_FAILED = 1300;// 客户端登出失败
    public static final int EXIT_FAILED = 1400;// 客户端退出失败

    public static final int PAY_FAILED = 2000;// 支付失败
    public static final int PAY_FAILED_CREATE_ORDER_FAILED = 2010;// 创建订单失败
    public static final int PAY_FAILED_PRODUCT_TOTAL_PRICE_INVALID = 2020;// 订单金额错误
    public static final int PAY_FAILED_UID_INVALID = 2030;// UID错误
    public static final int PAY_FAILED_PRODUCT_COUNT_INVALID = 2040;// 产品数量错误
    public static final int PAY_FAILED_EXT_INVALID = 2050;// 扩展信息错误
    public static final int PAY_FAILED_CHANNEL_RESPONSE = 2060;// 渠道反馈的支付失败

    public static final int OTHER_ERROR = 9999;

    private static final String MSG_UNKNOWN_ERROR = "Unknown error.";
    private static final String MSG_SUCCESS = "Success.";
    private static final String MSG_INIT_FAILED = "Init failed.";
    private static final String MSG_LGOIN_FAILED = "Login failed.";
    private static final String MSG_SWITCHACCOUNT_FAILED = "Switch account failed.";
    private static final String MSG_LOGOUT_FAILED = "Logout failed.";
    private static final String MSG_EXIT_FAILED = "Exit failed.";
    private static final String MSG_PAY_FAILED = "Pay failed.";
    private static final String MSG_PAY_FAILED_CREATE_ORDER_FAILED = "Pay failed : Create order failed.";
    private static final String MSG_PAY_FAILED_PRODUCT_TOTAL_PRICE_INVALID = "Pay failed : Product total price invalid.";
    private static final String MSG_PAY_FAILED_UID_INVALID = "Pay failed : Uid invalid.";
    private static final String MSG_PAY_FAILED_PRODUCT_COUNT_INVALID = "Pay failed : Product count invalid.";
    private static final String MSG_PAY_FAILED_EXT_INVALID = "Pay failed : Extend infomation invalid.";
    private static final String MSG_PAY_FAILED_CHANNEL_RESPONSE = "Pay failed : channel response.";
    private static final String MSG_OTHER_ERROR = "Other error.";

    private static final SparseArray<String> sErrorMap = new SparseArray<String>();

    static {
        sErrorMap.put(SUCCESS, MSG_SUCCESS);
        sErrorMap.put(INIT_FAILED, MSG_INIT_FAILED);
        sErrorMap.put(LOGIN_FAILED, MSG_LGOIN_FAILED);
        sErrorMap.put(SWITCHACCOUNT_FAILED, MSG_SWITCHACCOUNT_FAILED);
        sErrorMap.put(LOGOUT_FAILED, MSG_LOGOUT_FAILED);
        sErrorMap.put(EXIT_FAILED, MSG_EXIT_FAILED);
        sErrorMap.put(PAY_FAILED, MSG_PAY_FAILED);
        sErrorMap.put(PAY_FAILED_CREATE_ORDER_FAILED,
                MSG_PAY_FAILED_CREATE_ORDER_FAILED);
        sErrorMap.put(PAY_FAILED_PRODUCT_TOTAL_PRICE_INVALID,
                MSG_PAY_FAILED_PRODUCT_TOTAL_PRICE_INVALID);
        sErrorMap.put(PAY_FAILED_UID_INVALID, MSG_PAY_FAILED_UID_INVALID);
        sErrorMap.put(PAY_FAILED_PRODUCT_COUNT_INVALID,
                MSG_PAY_FAILED_PRODUCT_COUNT_INVALID);
        sErrorMap.put(PAY_FAILED_EXT_INVALID, MSG_PAY_FAILED_EXT_INVALID);
        sErrorMap.put(PAY_FAILED_CHANNEL_RESPONSE,
                MSG_PAY_FAILED_CHANNEL_RESPONSE);
        sErrorMap.put(OTHER_ERROR, MSG_OTHER_ERROR);
    }

    public static String parseCode(int code) {
        return sErrorMap.get(code, MSG_UNKNOWN_ERROR);
    }
}
