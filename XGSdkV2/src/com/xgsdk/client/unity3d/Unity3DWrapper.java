
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
public class Unity3DWrapper {
    private static final String LOG_TAG = "Unity3DAgent";
    private static Unity3DWrapper sInstance;
    private XGSDK mSdk;
    private String mGameControllerObject = "GameController";

    public Unity3DWrapper() {
        mSdk = XGSDK.getInstance();
    }

    public static Unity3DWrapper getInstance() {
        XGLogger.i(LOG_TAG, "getInstance");
        if (null == sInstance) {
            synchronized (Unity3DWrapper.class) {
                if (null == sInstance) {
                    sInstance = new Unity3DWrapper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化实例，在cocos2d-x activity中调用，传入游戏所在activity
     * 
     * @param activity
     * @param runner
     */
    public void init() {
        XGLogger.i(LOG_TAG, "init");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mSdk.init(UnityPlayer.currentActivity);
            }

        });
        mSdk.setUserCallBack(new UserCallBack() {

            @Override
            public void onLogoutSuccess(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject, "onLogoutSuccess",
                        msg);
            }

            @Override
            public void onLogoutFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject, "onLogoutFail", msg);

            }

            @Override
            public void onLoginSuccess(String authInfo) {
                UnityPlayer.UnitySendMessage(mGameControllerObject, "onLoginSuccess",
                        authInfo);

            }

            @Override
            public void onLoginFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject, "onLoginFail", msg);
            }

            @Override
            public void onInitFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameControllerObject, "onInitFail", msg);
            }
        });
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
                                UnityPlayer.UnitySendMessage(mGameControllerObject,
                                        "onPaySuccess", msg);
                            }

                            @Override
                            public void onFail(String msg) {

                                UnityPlayer.UnitySendMessage(mGameControllerObject,
                                        "onPayFail", msg);

                            }

                            @Override
                            public void onCancel(String msg) {

                                UnityPlayer.UnitySendMessage(mGameControllerObject,
                                        "onPayCancel", msg);

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
     * 
     * @param userId
     * @param username
     * @param nickname
     * @param userGender
     * @param userLevel
     * @param avatarUrl
     * @param createdTs
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
     * 
     * @param balance
     * @param level
     * @param vipLevel
     * @param roleId
     * @param roleName
     * @param partyName
     * @param gender
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
                        UnityPlayer.UnitySendMessage(mGameControllerObject, "onExit", "");
                    }

                    @Override
                    public void onNoChannelExiter() {
                        // 需要游戏自身弹出退出窗口
                        UnityPlayer.UnitySendMessage(mGameControllerObject,
                                "onNoChannelExiter", "");
                    }

                    @Override
                    public void onCancel() {

                        UnityPlayer.UnitySendMessage(mGameControllerObject,
                                "onExitCancel", "");
                    }

                }, "");
            }
        });
    }

}
