
package com.xgsdk.client.simulator.view;

import com.seasun.powerking.sdkclient.AuthService;
import com.seasun.powerking.sdkclient.ProductConfig;
import com.xgsdk.client.callback.UserCallBack;
import com.xgsdk.client.core.util.ConstInfo;
import com.xgsdk.client.core.util.ToastUtil;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.simulator.view.GameFloatView.GameFloatListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;

public class LoginDialog {
    private Activity activity;
    private GameFloatView mFVInstance;
    private String mUsername;
    private String mPassword;
    private UserCallBack mUserCallBack;

    public LoginDialog() {

    }

    public LoginDialog(Activity activity, UserCallBack mUserCallBack) {
        this.activity = activity;
        this.mUserCallBack = mUserCallBack;
    }

    public void showLoginDialog() {
        AlertDialog.Builder builder = new Builder(activity);
        builder.setTitle("XSJ Test Login");
        final LoginLayout ll = new LoginLayout(activity);
        builder.setView(ll);
        final AlertDialog loginDialog = builder.create();
        builder.setPositiveButton("OK", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ll.mETUsername != null
                        && !TextUtils.isEmpty(ll.mETUsername.getText())) {
                    mUsername = ll.mETUsername.getText().toString();
                    mPassword = ll.mETPassword.getText().toString();
                } else {
                    ToastUtil.showToast(activity, "username can not be null");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoginDialog();
                        }
                    });
                    return;
                }

                String uid = ConstInfo.getDeviceUUID(activity, true) + "_"
                        + mUsername;
                // String token = String.valueOf(System.currentTimeMillis());

                String session = mUsername + " " + mPassword;
                try {
                    String authInfo = AuthService.genAuthInfo(
                            ProductConfig.getXgAppId(),
                            ProductConfig.getXgAppKey(),
                            ProductConfig.getChannelId(), session,
                            String.valueOf(uid), mUsername);
                    XGLogger.d("generate authinfo:" + authInfo);
                    mUserCallBack.onLoginSuccess(authInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    XGLogger.e(
                            "login success, exception is :" + e.getMessage(), e);
                }
                ToastUtil.showToast(activity, mUsername + " login success.");
                // create user's float view
                // mFVInstance = GameFloatView.getInstance();
                mFVInstance = GameFloatView.getInstance(activity,
                        new GameFloatListener() {

                            @Override
                            public void onSwitchAccountClick(Activity activity) {
                                // TODO Auto-generated method stub
                                new LoginDialog(activity, mUserCallBack)
                                        .showLoginDialog();
                            }

                            @Override
                            public void onOpenUserCenterClick() {
                                // TODO Auto-generated method stub

                            }
                        });

                mFVInstance.setmUserSession(session);
                mFVInstance.createFloatWindow();
            }
        });
        builder.setNeutralButton("CANCEL", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // mUserCallBack.onLogin(new Result(Result.CODE_CANCEL,
                // channelTag() + " login cancel."));
                loginDialog.dismiss();
            }
        });

        builder.setNegativeButton("FAILED", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUserCallBack.onLogoutFail("login failed");
            }
        });
        builder.show();
        // mLoginDialog = builder.create();
        // mLoginDialog.show();
    }

}
