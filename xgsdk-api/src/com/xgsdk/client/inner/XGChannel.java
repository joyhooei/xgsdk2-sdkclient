
package com.xgsdk.client.inner;

import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.callback.UserCallBack;
import com.xgsdk.client.api.entity.GameServerInfo;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.api.entity.RoleInfo;
import com.xgsdk.client.api.entity.XGUser;
import com.xgsdk.client.core.utils.CommonUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

public abstract class XGChannel {

    protected XGUser mUserInfo;
    protected GameServerInfo mGameServerInfo;
    protected RoleInfo mRoleInfo;
    protected UserCallBack mUserCallBack;
    protected ExitCallBack mExitCallBack;
    protected PayCallBack mPayCallBack;

    public abstract String getChannelId();

    public abstract void init(final Activity activity); // 初始化

    public final void setUserCallBack(final UserCallBack userCallBack) {
        mUserCallBack = userCallBack;
    }

    public final void setExitCallBack(final ExitCallBack exitCallBack) {
        mExitCallBack = exitCallBack;
    }

    public final void setPayCallBack(final PayCallBack payCallBack) {
        mPayCallBack = payCallBack;
    }

    public abstract void login(final Activity activity,
            final String customParams);

    public void logout(final Activity activity, final String customParams) {
        if (mUserCallBack != null) {
            mUserCallBack.onLogoutSuccess("");
        }
    }// 登出

    public void exit(final Activity activity, final ExitCallBack exitCallBack,
            final String customParams) {
        if (exitCallBack != null) {
            exitCallBack.onNoChannelExiter();
        }
    }// 退出游戏

    public abstract void pay(final Activity activity, final PayInfo payment,
            final PayCallBack payCallBack);

    public void switchAccount(final Activity activity, final String customParams) {
        if (mUserCallBack != null) {
            mUserCallBack.onLogoutSuccess("");
        }
    }

    public void onCreate(final Activity activity) {
    }// 游戏主Actitivy在onCreate()调用

    public void onDestory(final Activity activity) {
    }// 游戏主Actitivy在onDestory()调用

    public void onPause(final Activity activity) {
    }// 游戏主Actitivy在onPause()调用

    public void onResume(final Activity activity) {
    }// 游戏主Actitivy在onResume()调用

    public void onNewIntent(final Activity activity, final Intent intent) {
    }

    public void onStart(final Activity activity) {
    }

    public void onRestart(final Activity activity) {
    }

    public void onStop(final Activity activity) {
    }

    public void onApplicationCreate(final Context context) {
    }

    public void onApplicationAttachBaseContext(final Context context) {
    }

    public void openUserCenter(final Activity activity,
            final String customParams) {
    }

    public void onLoginSuccess(final Activity activity, final XGUser info) {
        mUserInfo = info;
    }

    public void onCreateRole(final Activity activity, final RoleInfo info) {
        mRoleInfo = info;
    }

    public void setRoleInfo(final Activity activity, final RoleInfo info) {
        mRoleInfo = info;
    }

    public void onSelectGameServer(final Activity activity,
            final GameServerInfo info) {
        mGameServerInfo = info;
    }

    public void setGameServerInfo(final Activity activity,
            final GameServerInfo info) {
        mGameServerInfo = info;
    }

    public void onRoleLevelup(final Activity activity, final RoleInfo role) {
        mRoleInfo = role;
    }

    public void onEnterGame(final Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo) {
        setRoleInfo(activity, roleInfo);
        onSelectGameServer(activity, serverInfo);
        onLoginSuccess(activity, user);
    }

    public XGUser getUserInfo() {
        return mUserInfo;
    }

    public RoleInfo getRoleInfo() {
        return mRoleInfo;
    }

    public GameServerInfo getGameServerInfo() {
        return mGameServerInfo;
    }

    public void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
    }

    public boolean isCreateXGOrderIdBySelf() {
        return false;
    }

    public static boolean isMethodSupported(XGChannel agent, String methodName) {
        Class<?>[] parameterTypes = METHODS_MAP.get(methodName);
        return CommonUtils.supportMethodInSubClass(agent, methodName,
                parameterTypes);
    }

    public static final HashMap<String, Class<?>[]> METHODS_MAP = new HashMap<String, Class<?>[]>();
    static {
        METHODS_MAP.put("login", new Class[] {
                Activity.class, String.class
        });
        METHODS_MAP.put("logout", new Class[] {
                Activity.class, String.class
        });
        METHODS_MAP.put("pay", new Class[] {
                Activity.class, PayInfo.class, PayCallBack.class
        });
        // METHODS_MAP.put("showFloatWindow", new Class[] {
        // Activity.class, Boolean.class
        // });
        METHODS_MAP.put("switchAccount", new Class[] {
                Activity.class, String.class
        });
        METHODS_MAP.put("openUserCenter", new Class[] {
                Activity.class, String.class
        });
        METHODS_MAP.put("exit", new Class[] {
                Activity.class, ExitCallBack.class, String.class
        });
    }
}
