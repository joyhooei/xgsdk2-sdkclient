
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
import com.xgsdk.client.core.ProductInfo;
import com.xgsdk.client.core.ProductInfo.GAME_ENGINE;
import com.xgsdk.client.core.utils.ToastUtil;
import com.xgsdk.client.core.utils.XGLog;

import org.json.JSONException;
import org.json.JSONObject;

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
        ProductInfo.setGameEngine(GAME_ENGINE.UNITY3D);
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

    /**
     * 支付
     * 
     * @param uid
     * @param productTotalPirce
     * @param productCount
     * @param productUnitPrice
     * @param productId
     * @param productName
     * @param productDesc
     * @param currencyName
     * @param serverId
     * @param serverName
     * @param zoneId
     * @param zoneName
     * @param roleId
     * @param roleName
     * @param balance
     * @param gameOrderId
     * @param ext
     * @param notifyURL
     */
    public void pay(final String uid, final int productTotalPirce,
            final int productCount, final int productUnitPrice,
            final String productId, final String productName,
            final String productDesc, final String currencyName,
            final String serverId, final String serverName,
            final String zoneId, final String zoneName, final String roleId,
            final String roleName, final String balance,
            final String gameOrderId, final String ext, final String notifyURL) {
        XGLog.i(LOG_TAG, "pay");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                final PayInfo payment = new PayInfo();
                payment.setUid(uid);
                payment.setProductDesc(productDesc);
                payment.setServerId(serverId);
                payment.setProductId(productId);
                payment.setProductName(productName);
                payment.setExt(ext);
                payment.setProductCount(productCount);
                payment.setCurrencyName(currencyName);
                payment.setProductTotalPrice(productTotalPirce);
                payment.setProductUnitPrice(productUnitPrice);
                payment.setZoneId(zoneId);
                payment.setZoneName(zoneName);
                payment.setRoleId(roleId);
                payment.setRoleName(roleName);
                payment.setBalance(balance);
                payment.setGameOrderId(gameOrderId);
                payment.setServerName(serverName);
                payment.setNotifyURL(notifyURL);
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
    public void switchAccount() {
        XGLog.i(LOG_TAG, "switchAccount");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.switchAccount(UnityPlayer.currentActivity, "");
            }

        });
    }

    /**
     * 进入游戏后向渠道传递用户信息
     */
    public void onEnterGame(String uid, String username, String roleId,
            String roleName, String gender, String level, String vipLevel,
            String balance, String partyName, String serverId, String serverName) {
        XGLog.i(LOG_TAG, "login success,tell userinfo to sdk");
        XGUser userInfo = new XGUser();
        userInfo.setUserName(username);
        userInfo.setUid(uid);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setRoleName(roleName);
        roleInfo.setGender(gender);
        roleInfo.setLevel(level);
        roleInfo.setVipLevel(vipLevel);
        roleInfo.setBalance(balance);
        roleInfo.setPartyName(partyName);
        GameServerInfo gameInfo = new GameServerInfo();
        gameInfo.setServerId(serverId);
        gameInfo.setServerName(serverName);
        mSdk.onEnterGame(UnityPlayer.currentActivity, userInfo, roleInfo,
                gameInfo);
    }

    public void onCreateRole(String roleId, String roleName, String gender,
            String level, String vipLevel, String balance, String partyName) {
        XGLog.i(LOG_TAG, "onCreateRole");
        RoleInfo info = new RoleInfo();
        info.setBalance(balance);
        info.setGender(gender);
        info.setLevel(level);
        info.setPartyName(partyName);
        info.setRoleId(roleId);
        info.setRoleName(roleName);
        info.setVipLevel(vipLevel);
        mSdk.onCreateRole(UnityPlayer.currentActivity, info);
    }

    public void onRoleLevelup(String roleId, String roleName, String gender,
            String level, String vipLevel, String balance, String partyName) {
        XGLog.i(LOG_TAG, "onRoleLevelup");
        RoleInfo role = new RoleInfo();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setLevel(level);
        role.setVipLevel(vipLevel);
        role.setBalance(balance);
        role.setGender(gender);
        role.setPartyName(partyName);
        mSdk.onRoleLevelup(UnityPlayer.currentActivity, role);
    }

    /**
     * 传递事件
     * 
     * @param eventId
     */
    public void onEvent(String eventId, String content) {
        XGLog.i(LOG_TAG, "onEvent");
        mSdk.onEvent(UnityPlayer.currentActivity, eventId, content);
    }

    /**
     * 访问用户中心
     */
    public void openUserCenter() {
        XGLog.i(LOG_TAG, "openUserCenter");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.openUserCenter(UnityPlayer.currentActivity, "");
            }
        });
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
