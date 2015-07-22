
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
public class Unity3DAgent {
    private static final String LOG_TAG = "Unity3DAgent";
    private static Unity3DAgent sInstance;
    private XGSDK mSdk;
    private String mGameObject = "GameController";

    public Unity3DAgent() {
        mSdk = XGSDK.getInstance();
//        ProductConfig.setGameEngine(GAME_ENGINE.UNITY3D);
    }

    public static Unity3DAgent getInstance() {
        XGLogger.i(LOG_TAG, "getInstance");
        if (null == sInstance) {
            synchronized (Unity3DAgent.class) {
                if (null == sInstance) {
                    sInstance = new Unity3DAgent();
                }
            }
        }
        return sInstance;
    }

    // public String toJson(Result result) {
    // try {
    // if (result != null) {
    // JSONObject jo = new JSONObject();
    // jo.put("code", result.getCode());
    // jo.put("msg", result.getMsg());
    // String[] keys = result.getExtraKeys();
    // if (keys != null) {
    // JSONObject extras = new JSONObject();
    // for (int i = 0; i < keys.length; i++) {
    // extras.put(keys[i], result.getExtraValue(keys[i]));
    // }
    // jo.put("extras", extras);
    // return jo.toString();
    // }
    // }
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // return "";
    // }

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
                UnityPlayer.UnitySendMessage(mGameObject, "onLogoutSuccess",
                        msg);
            }

            @Override
            public void onLogoutFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameObject, "onLogoutFail", msg);

            }

            @Override
            public void onLoginSuccess(String authInfo) {
                UnityPlayer.UnitySendMessage(mGameObject, "onLoginSuccess",
                        authInfo);

            }

            @Override
            public void onLoginFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameObject, "onLoginFail", msg);
            }

            @Override
            public void onInitFail(String msg) {
                UnityPlayer.UnitySendMessage(mGameObject, "onInitFail", msg);
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
            final String gameOrderId, final String ext) {
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
                mSdk.pay(UnityPlayer.currentActivity, payment,
                        new PayCallBack() {

                            @Override
                            public void onSuccess(String msg) {
                                UnityPlayer.UnitySendMessage(mGameObject,
                                        "onPaySuccess", msg);
                            }

                            @Override
                            public void onFail(String msg) {

                                UnityPlayer.UnitySendMessage(mGameObject,
                                        "onPayFail", msg);

                            }

                            @Override
                            public void onCancel(String msg) {

                                UnityPlayer.UnitySendMessage(mGameObject,
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

    // /**
    // * 获取渠道用户信息 注意：结果在XSJCallBack的匿名子类中异步返回
    // */
    // public void getChannelUserInfo() {
    // XGLogger.i(LOG_TAG, "getChannelUserInfo");
    // mSdk.getChannelUserInfo(mActivity, new XSJCallBack() {
    //
    // @Override
    // public void onSuccess(final Result result) {
    // UnityPlayer.UnitySendMessage(mGameObject, "onGetInfoBack",
    // toJson(result));
    // }
    //
    // @Override
    // public void onFail(final Result result) {
    // UnityPlayer.UnitySendMessage(mGameObject, "onGetInfoBack",
    // toJson(result));
    // }
    //
    // });
    // }

    /**
     * 获取渠道tag
     * 
     * @return
     */
    public String getChannel() {
        return mSdk.getChannelId();
    }

    // /**
    // * 获取配置参数
    // *
    // * @param key
    // * @param defaultValue
    // * @return
    // */
    // public String getGameProperty(String key, String defaultValue) {
    // return mSdk.getGameProperty(mActivity, key, defaultValue);
    // }

    /**
     * 当前渠道是否提供该接口
     * 
     * @param methodName
     * @return
     */
    public boolean isMethodSupport(String methodName) {
        return mSdk.isMethodSupport(methodName);
    }

    // /**
    // * 游戏退出前调用，用来保存数据
    // */
    // public void onExitOrKillProcess() {
    // mSdk.onExitOrKillProgress(mActivity);
    // releaseResource();
    // }

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
        mSdk.exit(UnityPlayer.currentActivity, new ExitCallBack() {

            @Override
            public void onExit() {
                // 调用渠道退出窗口后的回调
                UnityPlayer.UnitySendMessage(mGameObject, "onExit", "");
            }

            @Override
            public void onNoChannelExiter() {
                // 需要游戏自身弹出退出窗口
                UnityPlayer.UnitySendMessage(mGameObject, "onNoChannelExiter",
                        "");
            }

            @Override
            public void onCancel() {

                UnityPlayer.UnitySendMessage(mGameObject, "onExitCancel", "");
            }

        }, "");
    }

    /**
     * Unity引擎的Android游戏退出游戏是通过Android的kill process完成的，不会调用Activity的销毁生命周期。
     * 很多资源的回收都是在渠道SDK的onPause、onStop、onDestroy方法中进行的，为了保证游戏退出的时候
     * 这些资源尤其是一些非本地资源（例如断开服务器连接，通知服务器关闭资源）得到释放，游戏退出前务必调用此接口。
     */
    public void releaseResource() {
        XGLogger.i(LOG_TAG, "releaseResource");
        mSdk.onPause(UnityPlayer.currentActivity);
        mSdk.onStop(UnityPlayer.currentActivity);
        mSdk.onDestory(UnityPlayer.currentActivity);
        try {
            Thread.sleep(1000L);// 1秒的时间释放资源，1秒钟后kill process!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
