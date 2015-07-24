
package com.xgsdk.client.unity3d;

import com.unity3d.player.UnityPlayer;
import com.xgsdk.client.XGSDK;
import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.callback.UserCallBack;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.entity.GameServerInfo;
import com.xgsdk.client.entity.PayInfo;
import com.xgsdk.client.entity.RoleInfo;
import com.xgsdk.client.entity.XGUser;

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
    private String mGameControllerObject = "GameController";

    public XGSDKUnity3DWrapper() {
        mSdk = XGSDK.getInstance();
        mSdk.setUserCallBack(new UserCallBack() {

            @Override
            public void onLogoutSuccess(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject,
                        METHOD_ON_LOGOUT_SUCCESS, msg);
            }

            @Override
            public void onLogoutFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject,
                        METHOD_ON_LOGOUT_FAIL, msg);

            }

            @Override
            public void onLoginSuccess(String authInfo) {
                UnityPlayer.UnitySendMessage(mGameControllerObject,
                        METHOD_ON_LOGIN_SUCCESS, authInfo);

            }

            @Override
            public void onLoginCancel(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject,
                        METHOD_ON_LOGIN_CANCEL, msg);
            }

            @Override
            public void onLoginFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject,
                        METHOD_ON_LOGIN_FAIL, msg);
            }

            @Override
            public void onInitFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject,
                        METHOD_ON_INIT_FAIL, msg);
            }
        });
    }

    public static XGSDKUnity3DWrapper getInstance() {
        XGLogger.i(LOG_TAG, "getInstance");
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
     * 登录
     */
    public void login() {
        XGLogger.i(LOG_TAG, "login");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                XGLogger.i(LOG_TAG, "activity name = "
                        + UnityPlayer.currentActivity.getLocalClassName());
                mSdk.login(UnityPlayer.currentActivity, "");
            }

        });
    }

    /**
     * 登出
     */
    public void logout() {
        XGLogger.i(LOG_TAG, "logout");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.logout(UnityPlayer.currentActivity, "");
            }

        });
    }

    public void pay(final String uid, final int productTotalPirce,
            final int productCount, final int productUnitPrice,
            final String productId, final String productName,
            final String productDesc, final String currencyName,
            final String serverId, final String serverName,
            final String roleId, final String roleName, final String balance,
            final String gameOrderId, final String ext, final String notifyURL) {
        XGLogger.i(LOG_TAG, "pay");
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
                                        mGameControllerObject,
                                        METHOD_ON_PAY_SUCCESS, msg);
                            }

                            @Override
                            public void onFail(String msg) {

                                UnityPlayer.UnitySendMessage(
                                        mGameControllerObject,
                                        METHOD_ON_PAY_FAIL, msg);

                            }

                            @Override
                            public void onCancel(String msg) {

                                UnityPlayer.UnitySendMessage(
                                        mGameControllerObject,
                                        METHOD_ON_PAY_CANCEL, msg);

                            }

                        });
            }

        });
    }

    /**
     * 访问用户中心
     */
    public void openUserCenter() {
        XGLogger.i(LOG_TAG, "openUserCenter");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.openUserCenter(UnityPlayer.currentActivity, "");
            }
        });
    }

    /**
     * 获取渠道tag
     * 
     * @return
     */
    public String getChannel() {
        return mSdk.getChannelId();
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

    /**
     * 进入游戏后向渠道传递用户信息
     */
    public void onEnterGame(String uid, String username, String roleId,
            String roleName, String gender, String level, String vipLevel,
            String balance, String partyName, String serverId, String serverName) {
        XGLogger.i(LOG_TAG, "login success,tell userinfo to sdk");
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

    /**
     * 创建角色成功后向渠道传递角色信息
     */
    public void onCreateRole(String roleId, String roleName, String gender,
            String level, String vipLevel, String balance, String partyName) {
        XGLogger.i(LOG_TAG, "onCreateRole");
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

    /**
     * 切换用户
     */
    public void switchAccount() {
        XGLogger.i(LOG_TAG, "switchAccount");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.switchAccount(UnityPlayer.currentActivity, "");
            }

        });
    }

    /**
     * 角色升级后向渠道传递升级信息
     * 
     * @param roleId
     * @param level
     */
    public void onRoleLevelup(String level) {
        XGLogger.i(LOG_TAG, "onRoleLevelup");
        mSdk.onRoleLevelup(UnityPlayer.currentActivity, level);
    }

    /**
     * vip升级后传递vip升级信息
     * 
     * @param vipLevel
     */
    public void onVipLevelup(String vipLevel) {
        XGLogger.i(LOG_TAG, "onVipLevelup");
        mSdk.onVipLevelup(UnityPlayer.currentActivity, vipLevel);
    }

    /**
     * 传递事件
     * 
     * @param eventId
     */
    public void onEvent(String eventId) {
        XGLogger.i(LOG_TAG, "onEvent");
        // mSdk.onEvent(mActivity, eventId);
    }

    /**
     * 游戏退出时调用,渠道释放资源
     */
    public void exit() {
        XGLogger.i(LOG_TAG, "exit");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.exit(UnityPlayer.currentActivity, new ExitCallBack() {

                    @Override
                    public void onExit() {
                        // 调用渠道退出窗口后的回调
                        UnityPlayer.UnitySendMessage(mGameControllerObject,
                                METHOD_ON_EXIT, "");
                    }

                    @Override
                    public void onNoChannelExiter() {
                        // 需要游戏自身弹出退出窗口
                        UnityPlayer.UnitySendMessage(mGameControllerObject,
                                METHOD_ON_NO_CHANNEL_EXITER, "");
                    }

                    @Override
                    public void onCancel() {

                        UnityPlayer.UnitySendMessage(mGameControllerObject,
                                METHOD_ON_EXIT_CANCEL, "");
                    }

                }, "");
            }
        });
    }

}
