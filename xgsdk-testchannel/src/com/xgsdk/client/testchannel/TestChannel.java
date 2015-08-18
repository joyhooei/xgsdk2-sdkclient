
package com.xgsdk.client.testchannel;

import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.entity.GameServerInfo;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.api.entity.RoleInfo;
import com.xgsdk.client.api.entity.XGUser;
import com.xgsdk.client.inner.XGChannel;
import com.xgsdk.client.testchannel.check.Check;
import com.xgsdk.client.testchannel.view.ExitDialog;
import com.xgsdk.client.testchannel.view.GameFloatView;
import com.xgsdk.client.testchannel.view.GameFloatView.GameFloatListener;
import com.xgsdk.client.testchannel.view.LoginDialog;
import com.xgsdk.client.testchannel.view.PayDialog;
import com.xgsdk.client.testchannel.view.UserCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Map;

public class TestChannel extends XGChannel {

    private static final String CHANNEL_ID = "xgtest";
    private GameFloatView mFVInstance;
    private UserCenter userCenter;

    @Override
    public String getChannelAppId(Context context) {
        return String.valueOf(context.getPackageName().hashCode());
    }

    @Override
    public void init(final Activity activity) {
        mFVInstance = GameFloatView.getInstance(activity, gfListener);
        Check.init(activity);
    }

    private GameFloatListener gfListener = new GameFloatListener() {

        @Override
        public void onSwitchAccountClick(Activity activity) {
            new LoginDialog(activity, mUserCallBack).showLoginDialog();
        }

        @Override
        public void onOpenUserCenterClick() {

        }
    };

    @Override
    public void login(final Activity activity, String customParams) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoginDialog(activity, mUserCallBack).showLoginDialog();
            }
        });
        Check.login(activity, customParams);
    }

    @Override
    public void onCreate(Activity activity) {
        Check.onCreate(activity);
    }

    @Override
    public void onDestroy(Activity activity) {
        mFVInstance.destroyFloatView();
        Check.onDestroy(activity);
    }

    @Override
    public void onPause(Activity activity) {
        mFVInstance.pauseFloatView();
        Check.onPause(activity);
    }

    @Override
    public void onResume(Activity activity) {
        mFVInstance.resumeFloatView();
        Check.onResume(activity);
    }

    @Override
    public void pay(final Activity activity, final PayInfo payment,
            final PayCallBack payCallBack) {
        new PayDialog().showDialog(activity, payment, payCallBack);
        Check.pay(activity, payment, payCallBack);

    }

    @Override
    public void exit(Activity activity, final ExitCallBack exitCallBack,
            String customParams) {
        new ExitDialog().showDialog(activity, exitCallBack, customParams);
        Check.exit(activity, exitCallBack, customParams);
    }

    @Override
    public void logout(Activity activity, String customParams) {
        super.logout(activity, customParams);
        Check.logout(activity, customParams);
    }

    @Override
    public String getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    public void openUserCenter(Activity activity, String customParams) {
        super.openUserCenter(activity, customParams);
        if (userCenter == null)
            userCenter = UserCenter.getInstance();
        userCenter.showDialog(activity, customParams);
        Check.openUserCenter(activity);
    }

    @Override
    public void switchAccount(Activity activity, String customParams) {
        super.switchAccount(activity, customParams);
        Check.switchAccount(activity, customParams);
    }

    @Override
    public void onNewIntent(Activity activity, Intent intent) {
        super.onNewIntent(activity, intent);
        Check.onNewIntent(activity, intent);
    }

    @Override
    public void onRestart(Activity activity) {
        super.onRestart(activity);
        Check.onRestart(activity);
    }

    @Override
    public void onStart(Activity activity) {
        super.onStart(activity);
        Check.onStart(activity);
        ;
    }

    @Override
    public void onStop(Activity activity) {
        super.onStop(activity);
        Check.onStop(activity);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        Check.onActivityResult(activity, requestCode, resultCode, data);
    }

    @Override
    public void onApplicationAttachBaseContext(Context context) {
        super.onApplicationAttachBaseContext(context);
        Check.onApplicationAttachBaseContext(context);
    }

    @Override
    public void onApplicationCreate(Context context) {
        super.onApplicationCreate(context);
        Check.onApplicationCreate(context);
    }

    @Override
    public void onCreateRole(Activity activity, XGUser user,RoleInfo roleInfo,GameServerInfo server) {
        super.onCreateRole(activity, user,roleInfo,server);
        Check.onCreateRole(activity, user,roleInfo,server);
    }

    @Override
    public void onEnterGame(Activity activity, XGUser user, RoleInfo roleInfo,
            GameServerInfo serverInfo) {
        super.onEnterGame(activity, user, roleInfo, serverInfo);
        Check.onEnterGame(activity, user, roleInfo, serverInfo);
    }

    @Override
    public void onRoleLevelup(Activity activity,XGUser user, RoleInfo roleInfo,GameServerInfo server) {
        super.onRoleLevelup(activity, user,roleInfo,server);
        Check.onRoleLevelup(activity, user,roleInfo,server);
    }
    
    @Override
    public void onEvent(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String eventId, String eventDesc,
            int eventVal, Map<String, Object> eventBody, String customParams) {
        super.onEvent(activity, user, role, server, eventId, eventDesc, 
                eventVal, eventBody, customParams);
        Check.onEvent(activity, user, role, server, eventId, eventDesc, 
                eventVal, eventBody, customParams);
    }
    
    @Override
    public void onMissionBegin(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String missionId, String missionName,
            String customParams) {
        super.onMissionBegin(activity, user, role, server, missionId, missionName, customParams);
        Check.onMissionBegin(activity, user, role, server, missionId, missionName, customParams);
    }
    
    @Override
    public void onMissionSuccess(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String missionId, String missionName,
            String customParams) {
        super.onMissionSuccess(activity, user, role, server, missionId, missionName, customParams);
        Check.onMissionSuccess(activity, user, role, server, missionId, missionName, customParams);
    }
    
    @Override
    public void onMissionFail(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String missionId, String missionName,
            String customParams) {
        super.onMissionFail(activity, user, role, server, missionId, missionName, customParams);
        Check.onMissionFail(activity, user, role, server, missionId, missionName, customParams);
    }

}
