
package com.xgsdk.client;

import com.xgsdk.client.agent.SDKFactory;
import com.xgsdk.client.agent.XGAgent;
import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.callback.UserCallBack;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.entity.GameServerInfo;
import com.xgsdk.client.entity.PayInfo;
import com.xgsdk.client.entity.RoleInfo;
import com.xgsdk.client.entity.XGUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class XGSDK {
    public static final String LOG_TAG = "XG_SDK";

    public static final String V = "2.0";

    private static XGSDK sInstance = null;

    private XGAgent mAgent = null;

    private XGSDK() {
        initAgent();
    }

    public String getChannelId() {
        return mAgent == null ? null : mAgent.getChannelId();
    }

    public static XGSDK getInstance() {
        if (null == sInstance) {
            synchronized (XGSDK.class) {
                if (null == sInstance) {
                    sInstance = new XGSDK();
                }
            }
        }

        return sInstance;
    }

    private void initAgent() {
        try {
            mAgent = SDKFactory.getSDK();
            if (mAgent == null) {
                throw new RuntimeException("Create xgsdk agent error.");
            }
            ProductInfo.init(getChannelId());
            XGLogger.i(LOG_TAG, getChannelId() + " instance " + mAgent);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG,
                    getChannelId() + " instance " + " agent error ", e);
        }
    }

    public void onCreate(Activity activity) {
        try {
            mAgent.onCreate(activity);
            Statistics.onRestart(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onCreate " + " error ", e);
        }
    }

    public void onDestory(Activity activity) {
        try {
            mAgent.onDestory(activity);
            Statistics.onDestory(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onDestory " + " error ", e);
        }
    }

    public void onResume(Activity activity) {
        try {
            mAgent.onResume(activity);
            Statistics.onResume(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onResume " + " error ", e);
        }
    }

    public void onPause(Activity activity) {
        try {
            mAgent.onPause(activity);
            Statistics.onPause(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onPause " + " error ", e);
        }
    }

    public void onStart(Activity activity) {
        try {
            mAgent.onStart(activity);
            Statistics.onStart(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onStart " + " error ", e);
        }
    }

    public void onRestart(Activity activity) {
        try {
            mAgent.onRestart(activity);
            Statistics.onRestart(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onRestart " + " error ", e);
        }
    }

    public void onStop(Activity activity) {
        try {
            mAgent.onStop(activity);
            Statistics.onStop(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onStop " + " error ", e);
        }
    }

    public void onNewIntent(Activity activity, Intent intent) {
        try {
            mAgent.onNewIntent(activity, intent);
            Statistics.onNewIntent(activity, intent);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onNewIntent " + " error ", e);
        }
    }

    public void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
        try {
            mAgent.onActivityResult(activity, requestCode, resultCode, data);
            Statistics
                    .onActivityResult(activity, requestCode, resultCode, data);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onActivityResult "
                    + " error ", e);
        }
    }

    public void setUserCallBack(UserCallBack userCallBack) {
        try {
            mAgent.setUserCallBack(userCallBack);
            Statistics.setUserCallBack(userCallBack);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " setUserCallBack "
                    + " error ", e);
        }
    }

    public void init(Activity activity) {
        try {
            mAgent.init(activity);
            Statistics.init(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " init " + " error ", e);
        }
    }

    public void login(Activity activity, String customParams) {
        try {
            mAgent.login(activity, customParams);
            Statistics.login(activity, customParams);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " login " + " error ", e);
        }
    }

    public void logout(Activity activity, String customParams) {
        try {
            mAgent.logout(activity, customParams);
            Statistics.logout(activity, customParams);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " logout " + " error ", e);
        }
    }

    public void pay(final Activity activity, PayInfo payInfo,
            PayCallBack payCallBack) {

        try {
            mAgent.setPayCallBack(payCallBack);
            mAgent.pay(activity, payInfo, payCallBack);
            Statistics.pay(activity, payInfo, payCallBack);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " pay " + " error ", e);
        }

    }

    public void exit(Activity activity, ExitCallBack exitCallBack,
            String customParams) {
        try {
            mAgent.setExitCallBack(exitCallBack);
            mAgent.exit(activity, exitCallBack, customParams);
            Statistics.exit(activity, exitCallBack, customParams);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " exit " + " error ", e);
        }
    }

    public void openUserCenter(Activity activity, String customParams) {
        try {
            mAgent.openUserCenter(activity, customParams);
            Statistics.openUserCenter(activity);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG,
                    getChannelId() + " openUserCenter " + " error ", e);
        }
    }

    public void switchAccount(Activity activity, String customParams) {
        try {
            mAgent.switchAccount(activity, customParams);
            Statistics.switchAccount(activity, customParams);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " switchAccount " + " error ",
                    e);
        }
    }

    public void onCreateRole(Activity activity, RoleInfo info) {
        try {
            mAgent.onCreatRole(activity, info);
            mAgent.setRoleInfo(activity, info);
            Statistics.onCreateRole(activity, info);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onCreateRole " + info
                    + " error ", e);
        }
    }

    public void onEnterGame(Activity activity, XGUser user, RoleInfo roleInfo,
            GameServerInfo serverInfo) {
        try {
            mAgent.onEnterGame(activity, user, roleInfo, serverInfo);
            Statistics.onEnterGame(activity, user, roleInfo, serverInfo);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onEnterGame error ", e);
        }
    }

    public void onRoleLevelup(Activity activity, String level) {
        try {
            mAgent.onRoleLevelup(activity, level);
            Statistics.onRoleLevelup(activity, level);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onRoleLevelup " + level
                    + " error ", e);
        }
    }

    public void onVipLevelup(Activity activity, String vipLevel) {
        try {
            Statistics.onVipLevelup(activity, vipLevel);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onVipLevelup " + vipLevel
                    + " error ", e);
        }
    }

    public void onEvent(String eventId, String content) {
        try {
            Statistics.onEvent(eventId, content);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onEvent error ", e);
        }
    }

    public void onRoleConsume(String accountId, String accountName,
            String roleId, String roleName, String roleType, String roleLevel,
            String activity, String itemCatalog, String itemId,
            String itemName, String consumeGold, String consumeBindingGold) {
        Statistics.onRoleConsume(accountId, accountName, roleId, roleName,
                roleType, roleLevel, activity, itemCatalog, itemId, itemName,
                consumeGold, consumeBindingGold);
    }

    void onApplicationCreate(final Context context) {
        try {
            mAgent.onApplicationCreate(context);
            Statistics.onApplicationCreate(context);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " onApplicationCreate "
                    + " error ", e);
        }
    }

    void onApplicationAttachBaseContext(final Context context) {
        try {
            mAgent.onApplicationAttachBaseContext(context);
            Statistics.onApplicationCreate(context);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId()
                    + " onApplicationAttachBaseContext " + " error ", e);
        }
    }

    public boolean isMethodSupport(String methodName) {
        boolean isSupport = false;
        try {
            isSupport = XGAgent.isMethodSupported(mAgent, methodName);
        } catch (Exception e) {
            XGLogger.e(LOG_TAG, getChannelId() + " isMethodSupport "
                    + " error ", e);
        }
        return isSupport;
    }

}
