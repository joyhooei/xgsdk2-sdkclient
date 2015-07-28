
package com.xgsdk.client.cocos2dx;

import com.xgsdk.client.ProductInfo;
import com.xgsdk.client.ProductInfo.GAME_ENGINE;
import com.xgsdk.client.XGSDK;
import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.callback.UserCallBack;
import com.xgsdk.client.core.util.ToastUtil;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.entity.GameServerInfo;
import com.xgsdk.client.entity.PayInfo;
import com.xgsdk.client.entity.RoleInfo;
import com.xgsdk.client.entity.XGUser;

import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

import android.app.Activity;

public class XGSDKCocos2dxWrapper {
    private static final String LOG_TAG = "XGCocos2dxWrapper";

    private XGSDK mSdk;

    private static XGSDKCocos2dxWrapper sInstance;

    private Activity mActivity;

    void init(Activity activity) {
        mActivity = activity;
        mSdk.init(activity);
        Cocos2dxPluginWrapper.init(activity);
        Cocos2dxPluginWrapper.setGLSurfaceView(Cocos2dxGLSurfaceView
                .getInstance());
    }

    private XGSDKCocos2dxWrapper() {
        ProductInfo.setGameEngine(GAME_ENGINE.COCOS2DX);
        mSdk = XGSDK.getInstance();
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

            @Override
            public void run() {
                mSdk.setUserCallBack(new UserCallBack() {

                    @Override
                    public void onLogoutSuccess(final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxUserCallBack.onLogoutSuccess(msg);
                            }

                        });
                    }

                    @Override
                    public void onLogoutFail(final int code, final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxUserCallBack.onLogoutFail(code, msg);
                            }

                        });
                    }

                    @Override
                    public void onLoginSuccess(final String authInfo) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxUserCallBack.onLoginSuccess(authInfo);
                            }

                        });
                    }

                    @Override
                    public void onLoginFail(final int code, final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxUserCallBack.onLoginFail(code, msg);
                            }

                        });
                    }

                    @Override
                    public void onInitFail(final int code, final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxUserCallBack.onInitFail(code, msg);
                            }

                        });
                    }

                    @Override
                    public void onLoginCancel(final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxUserCallBack.onLoginCancel(msg);
                            }

                        });
                    }
                });
            }

        });
    }

    public static XGSDKCocos2dxWrapper getInstance() {
        if (sInstance == null) {
            synchronized (XGSDKCocos2dxWrapper.class) {
                if (sInstance == null) {
                    sInstance = new XGSDKCocos2dxWrapper();
                }
            }
        }

        return sInstance;
    }

    /**
     * 获取渠道ID
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
        XGLogger.i(LOG_TAG, "login");
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

            @Override
            public void run() {
                mSdk.login(mActivity, customParams);
            }

        });
    }

    public void pay(final String uid, final int productTotalPirce,
            final int productCount, final int productUnitPrice,
            final String productId, final String productName,
            final String productDesc, final String currencyName,
            final String serverId, final String serverName,
            final String zoneId, final String zoneName, final String roleId,
            final String roleName, final String balance,
            final String gameOrderId, final String ext, final String notifyURL) {
        XGLogger.i(LOG_TAG, "pay");
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

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
                payment.setZoneId(zoneId);
                payment.setZoneName(zoneName);
                payment.setRoleName(roleName);
                payment.setBalance(balance);
                payment.setGameOrderId(gameOrderId);
                payment.setServerName(serverName);
                payment.setNotifyURL(notifyURL);
                mSdk.pay(mActivity, payment, new PayCallBack() {

                    @Override
                    public void onSuccess(final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxPayCallBack.onSuccess(msg);
                            }
                        });
                    }

                    @Override
                    public void onFail(final int code, final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxPayCallBack.onFail(code, msg);
                            }
                        });
                    }

                    @Override
                    public void onCancel(final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxPayCallBack.onCancel(msg);
                            }
                        });
                    }

                    @Override
                    public void onOthers(final int code, final String msg) {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxPayCallBack.onOthers(code, msg);
                            }
                        });
                    }

                });
            }

        });
    }

    /**
     * 游戏退出时调用,渠道释放资源
     */
    public void exit(final String customParams) {
        XGLogger.i(LOG_TAG, "exit");
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

            @Override
            public void run() {
                mSdk.exit(mActivity, new ExitCallBack() {

                    @Override
                    public void onExit() {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                // 调用渠道退出窗口后的回调
                                Cocos2dxExitCallBack.onExit();
                            }
                        });

                    }

                    @Override
                    public void onNoChannelExiter() {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                // 需要游戏自身弹出退出窗口
                                Cocos2dxExitCallBack.onNoChannelExiter();
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        Cocos2dxPluginWrapper.runOnGLThread(new Runnable() {

                            @Override
                            public void run() {
                                Cocos2dxExitCallBack.onCancel();
                            }
                        });
                    }

                }, customParams);
            }
        });
    }

    /**
     * 登出
     */
    public void logout(final String customParams) {
        XGLogger.i(LOG_TAG, "logout");
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

            @Override
            public void run() {
                mSdk.logout(mActivity, customParams);
            }

        });
    }

    /**
     * 切换用户
     */
    public void switchAccount(final String customParams) {
        XGLogger.i(LOG_TAG, "switchAccount");
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

            @Override
            public void run() {
                mSdk.switchAccount(mActivity, customParams);
            }

        });
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
        mSdk.onEnterGame(mActivity, userInfo, roleInfo, gameInfo);
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
        mSdk.onCreateRole(mActivity, info);
    }

    /**
     * 角色升级后向渠道传递升级信息
     * 
     * @param roleId
     * @param level
     */
    public void onRoleLevelup(String level) {
        XGLogger.i(LOG_TAG, "onRoleLevelup");
        mSdk.onRoleLevelup(mActivity, level);
    }

    /**
     * vip升级后传递vip升级信息
     * 
     * @param vipLevel
     */
    public void onVipLevelup(String vipLevel) {
        XGLogger.i(LOG_TAG, "onVipLevelup");
        mSdk.onVipLevelup(mActivity, vipLevel);
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
     * 访问用户中心
     */
    public void openUserCenter() {
        XGLogger.i(LOG_TAG, "openUserCenter");
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

            @Override
            public void run() {
                mSdk.openUserCenter(mActivity, "");
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
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

            @Override
            public void run() {
                ToastUtil.showToast(mActivity, msg);
            }
        });
    }
}
