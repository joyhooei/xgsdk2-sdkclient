
package com.xgsdk.client.testchannel.util;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class CommonStr {

    public static final String USER_CENTER = "个人中心";
    public static final String SWITCH_ACCOUNT = "切换账号";
    public static final String MENU = "菜单";
    public static final String XG_TEST_LOGIN = "XG 测试渠道登录";
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGIN_CANCEL = "登录取消";
    public static final String LOGIN_FAIL = "登录失败";
    public static final String PAY_ORDER = "订单支付";
    public static final String PAY_SUCCESS = "支付成功";
    public static final String PAY_CANCEL = "支付取消";
    public static final String PAY_FAIL = "支付失败";
    public static final String CLICKEXITORBACK = "点击退出退出按钮/返回键";
    public static final String EXIT_IMMEDIATE = "直接退出";
    public static final String EXIT_CANCEL = "取消退出";
    public static final String EXIT_USEGAMER = "使用游戏方退出";

    public static final int BLUE = Color.parseColor("#436EEE");
    public static final int GREEN = Color.parseColor("#20B2AA");
    public static final int BLACK = Color.parseColor("#000000");
    public static final int WHITE = Color.parseColor("#FFFFFF");
    public static final int RED = Color.parseColor("#FF0000");

    public static final String ORDER_USER_ID = "用户ID";
    public static final String ORDER_PRODUCT_ID = "产品ID";
    public static final String ORDER_PRODUCT_NAME = "产品名称";
    public static final String ORDER_PRODUCT_DESC = "产品描述";
    public static final String ORDER_PRODUCT_TOTAL_PRICE = "产品总价格(元)";
    public static final String ORDER_PRODUCT_UNIT_PRICE = "产品单价(元)";
    public static final String ORDER_PRODUCT_COUNT = "产品数量";
    public static final String ORDER_EXCHANGE_RATE = "交易率";
    public static final String ORDER_CURRENCY_NAME = "游戏金额单位";
    public static final String ORDER_EXT = "透传字段";
    public static final String ORDER_NOTIFY_URL = "支付通知回调地址";
    public static final String ORDER_ROLE_ID = "游戏角色ID";
    public static final String ORDER_ROLE_NAME = "游戏角色名称";
    public static final String ORDER_SERVER_ID = "游戏角色服务器ID";
    public static final String ORDER_SERVER_NAME = "游戏角色服务器名称";
    public static final String ORDER_BALANCE = "游戏内货币余额";
    public static final String ORDER_GAME_ORDER_ID = "游戏创建订单ID";
    public static final String ORDER_ZONE_ID = "区ID";
    public static final String ORDER_ZONE_NAME = "区名";

    public static Map<String, String> orderMap = new HashMap<String, String>();
    static {
        orderMap.put("uid", ORDER_USER_ID);
        orderMap.put("productId", ORDER_PRODUCT_ID);
        orderMap.put("productName", ORDER_PRODUCT_NAME);
        orderMap.put("productDesc", ORDER_PRODUCT_DESC);
        orderMap.put("productTotalPrice", ORDER_PRODUCT_TOTAL_PRICE);
        orderMap.put("productUnitPrice", ORDER_PRODUCT_UNIT_PRICE);
        orderMap.put("productCount", ORDER_PRODUCT_COUNT);
        // orderMap.put("exchangeRate", ORDER_EXCHANGE_RATE);
        orderMap.put("currencyName", ORDER_CURRENCY_NAME);
        orderMap.put("ext", ORDER_EXT);
        orderMap.put("notifyURL", ORDER_NOTIFY_URL);
        orderMap.put("roleId", ORDER_ROLE_ID);
        orderMap.put("roleName", ORDER_ROLE_NAME);
        orderMap.put("serverId", ORDER_SERVER_ID);
        orderMap.put("serverName", ORDER_SERVER_NAME);
        orderMap.put("balance", ORDER_BALANCE);
        orderMap.put("gameOrderId", ORDER_GAME_ORDER_ID);
        orderMap.put("zoneId", ORDER_ZONE_ID);
        orderMap.put("zoneName", ORDER_ZONE_NAME);

    }
}
