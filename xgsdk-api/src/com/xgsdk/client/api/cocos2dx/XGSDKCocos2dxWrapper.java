
package com.xgsdk.client.api.cocos2dx;

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
        XGInfo.setGameEngine(GAME_ENGINE.COCOS2DX);
        mSdk = XGSDK.getInstance();
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

    public String getChannelId() {
        return mSdk.getChannelId();
    }

    /**
     * 登录
     */
    public void login(final String customParams) {
        XGLog.i(LOG_TAG, "login");
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
        XGLog.i(LOG_TAG, "pay");
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

    public void exit(final String customParams) {
        XGLog.i(LOG_TAG, "exit");
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

    public void logout(final String customParams) {
        XGLog.i(LOG_TAG, "logout");
        Cocos2dxPluginWrapper.runOnMainThread(new Runnable() {

            @Override
            public void run() {
                mSdk.logout(mActivity, customParams);
            }

        });
    }

    public void switchAccount(final String customParams) {
        XGLog.i(LOG_TAG, "switchAccount");
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
        mSdk.onEnterGame(mActivity, userInfo, roleInfo, gameInfo);
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
        mSdk.onCreateRole(mActivity, info);
    }

    public void onRoleLevelup(String roleId, String roleName, String gender,
            String level, String vipLevel, String balance, String partyName) {
        XGLog.i(LOG_TAG, "onRoleLevelup");
        RoleInfo info = new RoleInfo();
        info.setBalance(balance);
        info.setGender(gender);
        info.setLevel(level);
        info.setPartyName(partyName);
        info.setRoleId(roleId);
        info.setRoleName(roleName);
        info.setVipLevel(vipLevel);
        mSdk.onRoleLevelup(mActivity, info);
    }

    /**
     * 自定义事件
     * 
     * @param eventId
     */
    public void onEvent(String eventId) {
        XGLog.i(LOG_TAG, "onEvent");
        // mSdk.onEvent(mActivity, eventId);
    }

    /**
     * 访问用户中心
     */
    public void openUserCenter() {
        XGLog.i(LOG_TAG, "openUserCenter");
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
