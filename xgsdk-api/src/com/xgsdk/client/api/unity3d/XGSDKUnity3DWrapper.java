
package com.xgsdk.client.api.unity3d;

import com.unity3d.player.UnityPlayer;
import com.xgsdk.client.api.XGSDK;
import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.callback.UserCallBack;
import com.xgsdk.client.api.entity.GameServerInfo;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.api.entity.RoleInfo;
import com.xgsdk.client.api.entity.XGUser;
import com.xgsdk.client.core.XGInfo;
import com.xgsdk.client.core.XGInfo.GAME_ENGINE;
import com.xgsdk.client.core.utils.ToastUtil;
import com.xgsdk.client.core.utils.XGLog;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author XGSDK包装类，通过提供给Unity3D调用
 */
public class XGSDKUnity3DWrapper {
    private static final String LOG_TAG = "Unity3DWrapper";

    private static final String METHOD_ON_LOGIN_SUCCESS = "onLoginSuccess";
    private static final String METHOD_ON_LOGIN_FAIL = "onLoginFail";
    private static final String METHOD_ON_LOGIN_CANCEL = "onLoginCancel";
    private static final String METHOD_ON_LOGOUT_SUCCESS = "onLogoutSuccess";
    private static final String METHOD_ON_LOGOUT_FAIL = "onLogoutFail";
    private static final String METHOD_ON_INIT_FAIL = "onInitFail";
    private static final String METHOD_ON_PAY_SUCCESS = "onPaySuccess";
    private static final String METHOD_ON_PAY_FAIL = "onPayFail";
    private static final String METHOD_ON_PAY_CANCEL = "onPayCancel";
    private static final String METHOD_ON_EXIT = "onExit";
    private static final String METHOD_ON_NO_CHANNEL_EXITER = "onNoChannelExiter";
    private static final String METHOD_ON_EXIT_CANCEL = "onExitCancel";

    private static XGSDKUnity3DWrapper sInstance;
    private XGSDK mSdk;
    private String mUnity3dCallbackObject = "XGSDKCallback";

    private String toResultJson(int code, String msg) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("code", code);
            jo.put("msg", msg);
        } catch (JSONException e) {
            XGLog.e("toResultJson error.", e);
        }
        return jo.toString();

    }

    public XGSDKUnity3DWrapper() {
        XGInfo.setGameEngine(GAME_ENGINE.UNITY3D);
        mSdk = XGSDK.getInstance();
        mSdk.setUserCallBack(new UserCallBack() {

            @Override
            public void onLogoutSuccess(String msg) {
                UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                        METHOD_ON_LOGOUT_SUCCESS, msg);
            }

            @Override
            public void onLogoutFail(int code, String msg) {
                UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                        METHOD_ON_LOGOUT_FAIL, toResultJson(code, msg));

            }

            @Override
            public void onLoginSuccess(String authInfo) {
                UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                        METHOD_ON_LOGIN_SUCCESS, authInfo);

            }

            @Override
            public void onLoginCancel(String msg) {
                UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                        METHOD_ON_LOGIN_CANCEL, msg);
            }

            @Override
            public void onLoginFail(int code, String msg) {
                UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                        METHOD_ON_LOGIN_FAIL, toResultJson(code, msg));
            }

            @Override
            public void onInitFail(int code, String msg) {
                UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                        METHOD_ON_INIT_FAIL, toResultJson(code, msg));
            }
        });
    }

    public static XGSDKUnity3DWrapper getInstance() {
        XGLog.i(LOG_TAG, "getInstance");
        if (null == sInstance) {
            synchronized (XGSDKUnity3DWrapper.class) {
                if (null == sInstance) {
                    sInstance = new XGSDKUnity3DWrapper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取渠道tag
     * 
     * @return
     */
    public String getChannelId() {
        return mSdk.getChannelId();
    }

    /**
     * 登录
     */
    public void login(final String customParams) {
        XGLog.i(LOG_TAG, "login");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                XGLog.i(LOG_TAG, "activity name = "
                        + UnityPlayer.currentActivity.getLocalClassName());
                mSdk.login(UnityPlayer.currentActivity, customParams);
            }

        });
    }

    public void pay(final String uid, final String productId,
            final String productName, final String productDesc,
            final int productAmount, final String productUnit,
            final int productUnitPrice, final int totalPrice,
            final int originalPrice, final String currencyName,
            final String custom, final String gameTradeNo,
            final String gameCallbackUrl, final String serverId,
            final String serverName, final String zoneId,
            final String zoneName, final String roleId, final String roleName,
            final int level, final int vipLevel) {
        XGLog.i(LOG_TAG, "pay");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                final PayInfo payment = new PayInfo();
                payment.setUid(uid);
                payment.setProductId(productId);
                payment.setProductName(productName);
                payment.setProductDesc(productDesc);
                payment.setProductAmount(productAmount);
                payment.setProductUnit(productUnit);
                payment.setProductUnitPrice(productUnitPrice);
                payment.setTotalPrice(totalPrice);
                payment.setOriginalPrice(originalPrice);
                payment.setCurrencyName(currencyName);
                payment.setCustom(custom);
                payment.setGameTradeNo(gameTradeNo);
                payment.setGameCallbackUrl(gameCallbackUrl);
                payment.setServerId(serverId);
                payment.setServerName(serverName);
                payment.setZoneId(zoneId);
                payment.setZoneName(zoneName);
                payment.setRoleId(roleId);
                payment.setRoleName(roleName);
                payment.setLevel(level);
                payment.setVipLevel(vipLevel);
                mSdk.pay(UnityPlayer.currentActivity, payment,
                        new PayCallBack() {

                            @Override
                            public void onSuccess(String msg) {
                                UnityPlayer.UnitySendMessage(
                                        mUnity3dCallbackObject,
                                        METHOD_ON_PAY_SUCCESS, msg);
                            }

                            @Override
                            public void onFail(int code, String msg) {

                                UnityPlayer.UnitySendMessage(
                                        mUnity3dCallbackObject,
                                        METHOD_ON_PAY_FAIL,
                                        toResultJson(code, msg));

                            }

                            @Override
                            public void onCancel(String msg) {

                                UnityPlayer.UnitySendMessage(
                                        mUnity3dCallbackObject,
                                        METHOD_ON_PAY_CANCEL, msg);

                            }

                        });
            }

        });
    }

    /**
     * 游戏退出时调用,渠道释放资源
     */
    public void exit(final String customParams) {
        XGLog.i(LOG_TAG, "exit");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.exit(UnityPlayer.currentActivity, new ExitCallBack() {

                    @Override
                    public void onExit() {
                        // 调用渠道退出窗口后的回调
                        UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                                METHOD_ON_EXIT, "");
                    }

                    @Override
                    public void onNoChannelExiter() {
                        // 需要游戏自身弹出退出窗口
                        UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                                METHOD_ON_NO_CHANNEL_EXITER, "");
                    }

                    @Override
                    public void onCancel() {

                        UnityPlayer.UnitySendMessage(mUnity3dCallbackObject,
                                METHOD_ON_EXIT_CANCEL, "");
                    }

                }, customParams);
            }
        });
    }

    /**
     * 登出
     */
    public void logout(final String customParams) {
        XGLog.i(LOG_TAG, "logout");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.logout(UnityPlayer.currentActivity, customParams);
            }

        });
    }

    /**
     * 切换用户
     */
    public void switchAccount(final String customParams) {
        XGLog.i(LOG_TAG, "switchAccount");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.switchAccount(UnityPlayer.currentActivity, customParams);
            }

        });
    }

    /**
     * 进入游戏后向渠道传递用户信息
     */
    public void onEnterGame(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName) {
        XGLog.i(LOG_TAG, "login success,tell userinfo to sdk");
        XGUser user = new XGUser();
        user.setUserName(username);
        user.setUid(uid);
        RoleInfo role = new RoleInfo();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setGender(gender);
        role.setLevel(level);
        role.setVipLevel(vipLevel);
        role.setPartyName(partyName);
        GameServerInfo server = new GameServerInfo();
        server.setServerId(serverId);
        server.setServerName(serverName);
        mSdk.onEnterGame(UnityPlayer.currentActivity, user, role, server);
    }

    public void onCreateRole(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName) {
        XGLog.i(LOG_TAG, "onCreateRole");
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo gameInfo = new GameServerInfo();
        gameInfo.setServerId(serverId);
        gameInfo.setServerName(serverName);
        mSdk.onCreateRole(UnityPlayer.currentActivity, userInfo, roleInfo,
                gameInfo);
    }

    public void onRoleLevelup(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName) {
        XGLog.i(LOG_TAG, "onRoleLevelup");
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo gameInfo = new GameServerInfo();
        gameInfo.setServerId(serverId);
        gameInfo.setServerName(serverName);
        mSdk.onRoleLevelup(UnityPlayer.currentActivity, userInfo, roleInfo,
                gameInfo);
    }

    public void onRoleLogout(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String customParams) {
        XGLog.i(LOG_TAG, "onRoleLogout");
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo gameInfo = new GameServerInfo();
        gameInfo.setServerId(serverId);
        gameInfo.setServerName(serverName);
        mSdk.onRoleLogout(UnityPlayer.currentActivity, userInfo, roleInfo,
                gameInfo, customParams);
    }

    public void onAccountCreate(String uid, String username, String customParams) {
        XGLog.i(LOG_TAG, "onAccountCreate");
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        mSdk.onAccountCreate(UnityPlayer.currentActivity, userInfo,
                customParams);
    }

    public void onAccountLogout(String uid, String username, String customParams) {
        XGLog.i(LOG_TAG, "onAccountLogout");
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        mSdk.onAccountLogout(UnityPlayer.currentActivity, userInfo,
                customParams);
    }

    /**
     * 访问用户中心
     */
    public void openUserCenter(final String customParams) {
        XGLog.i(LOG_TAG, "openUserCenter");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.openUserCenter(UnityPlayer.currentActivity, customParams);
            }
        });
    }

    /**
     * @Title: onEvent
     * @Description: 自定义事件
     * @param: @param uid
     * @param: @param username
     * @param: @param roleId
     * @param: @param roleName
     * @param: @param gender
     * @param: @param level
     * @param: @param vipLevel
     * @param: @param balance
     * @param: @param partyName
     * @param: @param serverId
     * @param: @param serverName
     * @param: @param eventId 事件id
     * @param: @param eventDesc 事件描述
     * @param: @param eventVal 事件值
     * @param: @param eventBody 事件内容 必须是json格式
     * @param: @param customParams 扩展字段 必须是json格式
     * @return: void
     * @throws
     */
    public void onEvent(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String eventId, String eventDesc, int eventVal, String eventBody,
            String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo gameInfo = new GameServerInfo();
        gameInfo.setServerId(serverId);
        gameInfo.setServerName(serverName);
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        if (!TextUtils.isEmpty(eventBody)) {
            try {
                JSONObject body = new JSONObject(eventBody);
                Iterator<String> keys = body.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    bodyMap.put(key, body.opt(key));
                }
            } catch (JSONException e) {
                XGLog.e("eventBody is invalid." + eventBody, e);
            }
        }
        mSdk.onEvent(UnityPlayer.currentActivity, userInfo, roleInfo, gameInfo,
                eventId, eventDesc, eventVal, bodyMap, customParams);

    }

    public void onMissionBegin(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String missionId, String missionName, String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onMissionBegin(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, missionId, missionName, customParams);

    }

    public void onMissionSuccess(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String missionId, String missionName, String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onMissionSuccess(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, missionId, missionName, customParams);

    }

    public void onMissionFail(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String missionId, String missionName, String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onMissionFail(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, missionId, missionName, customParams);

    }

    public void onLevelsBegin(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String levelsId, String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onLevelsBegin(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, levelsId, customParams);

    }

    public void onLevelsSuccess(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String levelsId, String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onLevelsSuccess(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, levelsId, customParams);

    }

    public void onLevelsFail(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String levelsId, String reason, String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);

        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onLevelsFail(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, levelsId, reason, customParams);

    }

    public void onItemBuy(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String itemId, String itemName, int itemCount, int listPrice,
            int transPrice, int payGold, int payBindingGold, int curGold,
            int curBindingGold, int totalGold, int totalBindingGold,
            String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onItemBuy(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, itemId, itemName, itemCount, listPrice, transPrice,
                payGold, payBindingGold, curGold, curBindingGold, totalGold,
                totalBindingGold, customParams);
    }

    public void onItemGet(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String itemId, String itemName, int itemCount, int listPrice,
            int transPrice, int payGold, int payBindingGold, int curGold,
            int curBindingGold, int totalGold, int totalBindingGold,
            String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onItemGet(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, itemId, itemName, itemCount, listPrice, transPrice,
                payGold, payBindingGold, curGold, curBindingGold, totalGold,
                totalBindingGold, customParams);
    }

    public void onItemConsume(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String itemId, String itemName, int itemCount, int listPrice,
            int transPrice, int payGold, int payBindingGold, int curGold,
            int curBindingGold, int totalGold, int totalBindingGold,
            String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);

        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onItemConsume(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, itemId, itemName, itemCount, listPrice, transPrice,
                payGold, payBindingGold, curGold, curBindingGold, totalGold,
                totalBindingGold, customParams);
    }

    public void onGoldGain(String uid, String username, String roleId,
            String roleName, String gender, int level, int vipLevel,
            String partyName, String serverId, String serverName,
            String gainChannel, int gold, int bindingGold, int curGold,
            int curBindingGold, int totalGold, int totalBindingGold,
            String customParams) {
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setPartyName(partyName);
        GameServerInfo serverInfo = new GameServerInfo();
        serverInfo.setServerId(serverId);
        serverInfo.setServerName(serverName);
        mSdk.onGoldGain(UnityPlayer.currentActivity, userInfo, roleInfo,
                serverInfo, gainChannel, gold, bindingGold, curGold,
                curBindingGold, totalGold, totalBindingGold, customParams);
    }

    /**
     * 当前渠道是否提供该接口
     * 
     * @param methodName
     * @return
     */
    public boolean isMethodSupport(String methodName) {
        return mSdk.isMethodSupport(methodName);
    }

    public void showAndroidToast(final String msg) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ToastUtil.showToast(UnityPlayer.currentActivity, msg);
            }
        });
    }

}
