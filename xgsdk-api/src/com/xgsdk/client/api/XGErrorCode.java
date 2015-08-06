
package com.xgsdk.client.api;

public interface XGErrorCode {
    public static final int SDK_CLIENT_INIT_FAILED = 1001;// 客户端初始化失败
    public static final int SDK_CLIENT_LOGIN_FAILED = 1011;// 客户端登录失败
    public static final int SDK_CLIENT_SWITCHACCOUNT_FAILED = 1021;// 客户端切换账号失败
    public static final int SDK_CLIENT_LOGOUT_FAILED = 1031;// 客户端登出失败
    public static final int SDK_CLIENT_EXIT_FAILED = 1041;// 客户端退出失败
    
    public static final int SDK_PAY_CREATE_ORDER_FAILED = 2001;// 创建订单失败
    public static final int PAY_FAILED = 2002;// 客户端支付失败
    public static final int PAY_FAILED_CHANNEL_ERROR = 2003;// 客户端支付失败，渠道错误
    
    // 调用 SDK server参数不正确
//    public static final int SDK_SERVER_PARAM_INVALID = 7001;// 参数异常
//    public static final int SDK_SERVER_CONN_FAILED = 7002;// 连接异常
//    public static final int SDK_SERVER_RESPONSE_ERROR = 7003;// SDK server返回失败

//    public static final int BUSINESS_ERROR = 7101;// 业务异常

    public static final int CHANNEL_ERROR = 8001;// 渠道异常

    public static final int OTHER_ERROR = 9999;

}
