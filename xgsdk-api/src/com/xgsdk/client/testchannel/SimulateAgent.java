
package com.xgsdk.client.testchannel;

import com.xgsdk.client.agent.XGAgent;
import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.entity.PayInfo;
import com.xgsdk.client.testchannel.view.ExitDialog;
import com.xgsdk.client.testchannel.view.GameFloatView;
import com.xgsdk.client.testchannel.view.GameFloatView.GameFloatListener;
import com.xgsdk.client.testchannel.view.LoginDialog;
import com.xgsdk.client.testchannel.view.PayDialog;
import com.xgsdk.client.testchannel.view.UserCenter;

import android.app.Activity;

public class SimulateAgent extends XGAgent {

    private static final String CHANNEL_ID = "XG_TEST";
    private GameFloatView mFVInstance;
    private UserCenter userCenter;

    @Override
    public void init(final Activity activity) {
        mFVInstance = GameFloatView.getInstance(activity, gfListener);
    }

    private GameFloatListener gfListener = new GameFloatListener() {

        @Override
        public void onSwitchAccountClick(Activity activity) {
            // TODO Auto-generated method stub
            new LoginDialog(activity, mUserCallBack).showLoginDialog();
        }

        @Override
        public void onOpenUserCenterClick() {
            // TODO Auto-generated method stub

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
    }

    @Override
    public void onCreate(Activity activity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDestory(Activity activity) {
        // TODO Auto-generated method stub
        mFVInstance.destroyFloatView();
        /*
         * mFVInstance = null; activity.finish();
         */
    }

    @Override
    public void onPause(Activity activity) {
        // TODO Auto-generated method stub
        mFVInstance.pauseFloatView();
        /*
         * if(mLoginDialog != null){ mLoginDialog.dismiss(); }
         */
    }

    @Override
    public void onResume(Activity activity) {
        // TODO Auto-generated method stub
        mFVInstance.resumeFloatView();
        /*
         * if(mLoginDialog != null){ mLoginDialog.show(); }
         */
    }

    @Override
    public void pay(Activity activity, PayInfo payment, PayCallBack payCallBack) {
        new PayDialog().showDialog(activity, payment, payCallBack);
    }

    @Override
    public void exit(Activity activity, final ExitCallBack exitCallBack,
            String customParams) {
        new ExitDialog().showDialog(activity, exitCallBack, customParams);
    }

    @Override
    public void logout(Activity activity, String customParams) {
        // TODO Auto-generated method stub
        super.logout(activity, customParams);
        // mUserCallBack.onLogoutSuccess("login out success");
    }

    @Override
    public String getChannelId() {
        // TODO Auto-generated method stub
        return CHANNEL_ID;
    }

    @Override
    public void openUserCenter(Activity activity, String customParams) {
        // TODO Auto-generated method stub
        super.openUserCenter(activity, customParams);
        if (userCenter == null)
            userCenter = UserCenter.getInstance();
        userCenter.showDialog(activity, customParams);
    }

}
