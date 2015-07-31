
package com.xgsdk.client.testchannel.check;

import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.entity.GameServerInfo;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.api.entity.RoleInfo;
import com.xgsdk.client.api.entity.XGUser;
import com.xgsdk.client.core.http.HttpUtils;
import com.xgsdk.client.core.utils.XGLog;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Check {

    private static final String URL_PORTAL_CHECK = "http://10.20.72.72:8090/package/interfaceTest";

    private static final String METHOD_ON_CREATE = "onCreate";
    private static final String METHOD_ON_RESUME = "onResume";
    private static final String METHOD_ON_START = "onStart";
    private static final String METHOD_ON_RESTART = "onRestart";
    private static final String METHOD_ON_PAUSE = "onPause";
    private static final String METHOD_ON_STOP = "onStop";
    private static final String METHOD_ON_DESTORY = "onDestory";
    private static final String METHOD_ON_ACTIVITY_RESULT = "onActivityResult";
    private static final String METHOD_ON_NEW_INTENT = "onNewIntent";
    private static final String METHOD_INIT = "init";
    private static final String METHOD_PAY = "pay";
    private static final String METHOD_LOGIN = "login";
    private static final String METHOD_LOGOUT = "logout";
    private static final String METHOD_EXIT = "exit";
    private static final String METHOD_SWITCH_ACCOUNT = "switchAccount";
    private static final String METHOD_OPEN_USERCENTER = "openUserCenter";
    private static final String METHOD_ON_CREATE_ROLE = "onCreateRole";
    private static final String METHOD_ON_ENTER_GAME = "onEnterGame";
    private static final String METHOD_ON_ROLE_LEVELUP = "onRoleLevelup";
    private static final String METHOD_ON_VIP_LEVELUP = "onVipLevelup";
    private static final String METHOD_ON_APPLICATION_CREATE = "onApplicationCreate";
    private static final String METHOD_ON_APPLICATION_ATTACHBASECONTEXT = "onApplicationAttachBaseContext";
    private static final String METHOD_ON_EVENT = "onEvent";
    private static final String METHOD_ON_ROLE_CONSUME = "onRoleConsume";

    private static final String PARAM_NAME_ACTIVITY = "Activity";
    private static final String PARAM_NAME_CONTEXT = "Context";
    private static final String PARAM_NAME_REQUEST_CODE = "RequestCode";
    private static final String PARAM_NAME_RESULT_CODE = "ResultCode";
    private static final String PARAM_NAME_DATA = "Data";
    private static final String PARAM_NAME_INTENT = "Intent";
    private static final String PARAM_NAME_PAYINFO = "PayInfo";
    private static final String PARAM_NAME_PAYCALLBACK = "PayCallBack";
    private static final String PARAM_NAME_USERCALLBACK = "UserCallBack";
    private static final String PARAM_NAME_ROLEINFO = "RoleInfo";
    private static final String PARAM_NAME_XGUSER = "XGUser";
    private static final String PARAM_NAME_GAMESERVERINFO = "GameServerInfo";
    private static final String PARAM_NAME_LEVEL = "Level";
    private static final String PARAM_NAME_VIPLEVEL = "VipLevel";
    private static final String PARAM_NAME_CUSTOM_PARAMS = "CustomParams";
    private static final String PARAM_NAME_EVENT_ID = "EventId";
    private static final String PARAM_NAME_CONTENT = "Content";
    private static final String PARAM_NAME_ACCOUNT_ID = "AccountId";
    private static final String PARAM_NAME_ACCOUNT_NAME = "AccountName";
    private static final String PARAM_NAME_ROLE_ID = "RoleId";
    private static final String PARAM_NAME_ROLE_NAME = "RoleName";
    private static final String PARAM_NAME_ROLE_TYPE = "RoleType";
    private static final String PARAM_NAME_ROLE_LEVEL = "RoleLevel";
    private static final String PARAM_NAME_ITEM_CATALOG = "ItemCatalog";
    private static final String PARAM_NAME_ITEM_ID = "ItemId";
    private static final String PARAM_NAME_ITEM_NAME = "ItemName";
    private static final String PARAM_NAME_CONSUME_GOLD = "ConsumeGold";
    private static final String PARAM_NAME_CONSUME_BINDING_GOLD = "ConsumeBindingGold";

    private static boolean sCheckEnable = false;

    public static void initCheckEnable(boolean checkEnable) {
        sCheckEnable = checkEnable;
        XGLog.i("set CheckEnable " + checkEnable);
    }

    private static void check(Context context, String name,
            HashMap<String, Object> params) {
        if (!sCheckEnable) {
            return;
        }

        JSONObject json = CheckData.getCheckTotalJson(context, name, params);
        XGLog.w(json.toString());
        try {
            String ret = HttpUtils.doPostInThread(URL_PORTAL_CHECK,
                    json.toString());
            XGLog.w(ret);
        } catch (Exception e) {
            XGLog.e("post check data error.", e);
        }
    }

    private static HashMap<String, Object> getVariablesFromObj(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();

        Method[] methods = obj.getClass().getDeclaredMethods();
        if (methods != null) {
            for (int i = 0; i < methods.length; i++) {

                Method method = methods[i];
                String name = method.getName();
                int parametersCount = method.getParameterTypes().length;
                if (name.startsWith("get") && parametersCount == 0) {
                    try {
                        Object ret = method.invoke(obj, null);
                        String n = name.substring(3);
                        map.put(n, ret);
                    } catch (Exception e) {
                        XGLog.d("", e);

                    }
                }
            }
        }
        return map;
    }

    private static void lifecycle(String name, Activity activity) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        check(activity, name, paramsMap);
    }

    public static void onCreate(Activity activity) {
        lifecycle(METHOD_ON_CREATE, activity);
    }

    public static void onResume(Activity activity) {
        lifecycle(METHOD_ON_RESUME, activity);
        // XGSDKDataAgentUtils.onResume();
    }

    public static void onStart(Activity activity) {
        lifecycle(METHOD_ON_START, activity);
    }

    public static void onRestart(Activity activity) {
        lifecycle(METHOD_ON_RESTART, activity);
    }

    public static void onPause(Activity activity) {
        lifecycle(METHOD_ON_PAUSE, activity);
        // XGSDKDataAgentUtils.onPause();
    }

    public static void onStop(Activity activity) {
        lifecycle(METHOD_ON_STOP, activity);
    }

    public static void onDestory(Activity activity) {
        lifecycle(METHOD_ON_DESTORY, activity);
    }

    public static void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_REQUEST_CODE, requestCode);
        paramsMap.put(PARAM_NAME_RESULT_CODE, resultCode);
        paramsMap.put(PARAM_NAME_DATA, data);
        check(activity, METHOD_ON_ACTIVITY_RESULT, paramsMap);
    }

    public static void onNewIntent(Activity activity, Intent intent) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_INTENT, intent);
        check(activity, METHOD_ON_NEW_INTENT, paramsMap);
    }

    public static void init(Activity activity) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        check(activity, METHOD_INIT, paramsMap);
    }

    public static void login(Activity activity, String customParams) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_CUSTOM_PARAMS, customParams);
        check(activity, METHOD_LOGIN, paramsMap);
    }

    public static void logout(Activity activity, String customParams) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_CUSTOM_PARAMS, customParams);
        check(activity, METHOD_LOGOUT, paramsMap);
    }

    public static void pay(Activity activity, PayInfo payInfo,
            PayCallBack payCallBack) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_PAYINFO, getVariablesFromObj(payInfo));
        paramsMap.put(PARAM_NAME_PAYCALLBACK, payCallBack);
        check(activity, METHOD_PAY, paramsMap);
        // XGSDKDataAgentUtils.roleRecharge(payInfo.getUid(), payInfo.get,
        // payInfo.getRoleId(), payInfo.getRoleName(), roleType, pay,
        // rechargeChannel, currency, gold, bindingGold, curGold,
        // curBindingGold, totalBindingGold);
    }

    public static void switchAccount(Activity activity, String customParams) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_CUSTOM_PARAMS, customParams);
        check(activity, METHOD_SWITCH_ACCOUNT, paramsMap);
    }

    public static void exit(Activity activity, ExitCallBack exitCallBack,
            String customParams) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_USERCALLBACK, exitCallBack);
        paramsMap.put(PARAM_NAME_CUSTOM_PARAMS, customParams);
        check(activity, METHOD_EXIT, paramsMap);
    }

    public static void openUserCenter(Activity activity) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        check(activity, METHOD_OPEN_USERCENTER, paramsMap);
    }

    public static void onCreateRole(Activity activity, RoleInfo role) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ROLEINFO, getVariablesFromObj(role));
        check(activity, METHOD_ON_CREATE_ROLE, paramsMap);
    }

    public static void onEnterGame(Activity activity, XGUser user,
            RoleInfo role, GameServerInfo server) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_XGUSER, getVariablesFromObj(user));
        paramsMap.put(PARAM_NAME_ROLEINFO, getVariablesFromObj(role));
        paramsMap.put(PARAM_NAME_GAMESERVERINFO, getVariablesFromObj(server));
        check(activity, METHOD_ON_ENTER_GAME, paramsMap);
    }

    public static void onRoleLevelup(Activity activity, String level) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_LEVEL, level);
        check(activity, METHOD_ON_ROLE_LEVELUP, paramsMap);
    }

    public static void onVipLevelup(Activity activity, String vipLevel) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_VIPLEVEL, vipLevel);
        check(activity, METHOD_ON_VIP_LEVELUP, paramsMap);
    }

    public static void onApplicationCreate(Context context) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_CONTEXT, context);
        check(context, METHOD_ON_APPLICATION_CREATE, paramsMap);
    }

    public static void onApplicationAttachBaseContext(Context context) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_CONTEXT, context);
        check(context, METHOD_ON_APPLICATION_ATTACHBASECONTEXT, paramsMap);
    }

    public static void onEvent(Activity activity, String eventId, String content) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_EVENT_ID, eventId);
        paramsMap.put(PARAM_NAME_CONTENT, content);
        check(activity, METHOD_ON_EVENT, paramsMap);
        // XGSDKDataAgentUtils.customEvent(eventId, content);
    }

    public static void onRoleConsume(Activity acti, String accountId,
            String accountName, String roleId, String roleName,
            String roleType, String roleLevel, String activity,
            String itemCatalog, String itemId, String itemName,
            String consumeGold, String consumeBindingGold) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACCOUNT_ID, accountId);
        paramsMap.put(PARAM_NAME_ACCOUNT_NAME, accountName);
        paramsMap.put(PARAM_NAME_ROLE_ID, roleId);
        paramsMap.put(PARAM_NAME_ROLE_NAME, roleName);
        paramsMap.put(PARAM_NAME_ROLE_TYPE, roleType);
        paramsMap.put(PARAM_NAME_ROLE_LEVEL, roleLevel);
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_ITEM_CATALOG, itemCatalog);
        paramsMap.put(PARAM_NAME_ITEM_ID, itemId);
        paramsMap.put(PARAM_NAME_ITEM_NAME, itemName);
        paramsMap.put(PARAM_NAME_CONSUME_GOLD, consumeGold);
        paramsMap.put(PARAM_NAME_CONSUME_BINDING_GOLD, consumeBindingGold);
        check(acti, METHOD_ON_ROLE_CONSUME, paramsMap);
        // XGSDKDataAgentUtils.roleConsume(accountId, accountName, roleId,
        // roleName, roleType, roleLevel, activity, itemCatalog, itemId,
        // itemName, consumeGold, consumeBindingGold);
    }

}
