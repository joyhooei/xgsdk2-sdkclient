
package com.xgsdk.client.testchannel.view;

import com.xgsdk.client.SystemInfo;
import com.xgsdk.client.callback.UserCallBack;
import com.xgsdk.client.core.util.ToastUtil;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.entity.XGErrorCode;
import com.xgsdk.client.service.AuthService;
import com.xgsdk.client.testchannel.util.CommonStr;
import com.xgsdk.client.testchannel.view.GameFloatView.GameFloatListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

        // builder.setTitle(CommonStr.XG_TEST_LOGIN);
        TextView title = new TextView(activity);
        title.setText(CommonStr.XG_TEST_LOGIN);
        title.setTextSize(22);

        LayoutParams linearParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        LinearLayout llTitle = new LinearLayout(activity);
        llTitle.setOrientation(LinearLayout.VERTICAL);
        llTitle.setLayoutParams(linearParams);
        llTitle.setPadding(0, 15, 0, 10);
        title.setLayoutParams(titleParams);
        llTitle.addView(title);
        llTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setCustomTitle(llTitle);

        final LoginLayout ll = new LoginLayout(activity);
        // add operator specification
        LinearLayout loginLay = (LinearLayout) ll.getChildAt(0);
        loginLay.addView(addOperatorSpecify(activity));
        builder.setView(ll);
        final AlertDialog loginDialog = builder.create();

        builder.setPositiveButton(CommonStr.LOGIN_SUCCESS,
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ll.mETUsername != null
                                && !TextUtils.isEmpty(ll.mETUsername.getText())) {
                            mUsername = ll.mETUsername.getText().toString();
                            mPassword = ll.mETPassword.getText().toString();
                        } else {
                            ToastUtil.showToast(activity,
                                    "username can not be null");
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showLoginDialog();
                                }
                            });
                            return;
                        }

                        String uid = SystemInfo.getDeviceUUID(activity, true)
                                + "_" + mUsername;
                        // String token =
                        // String.valueOf(System.currentTimeMillis());

                        String session = mUsername + " " + mPassword;
                        try {
                            String authInfo = AuthService.genAuthInfo(activity,
                                    session, String.valueOf(uid), mUsername);
                            XGLogger.d("generate authinfo:" + authInfo);
                            mUserCallBack.onLoginSuccess(authInfo);
                        } catch (Exception e) {
                            e.printStackTrace();
                            XGLogger.e(
                                    "login success, exception is :"
                                            + e.getMessage(), e);
                        }
                        ToastUtil.showToast(activity, mUsername
                                + " login success.");
                        // create user's float view
                        // mFVInstance = GameFloatView.getInstance();
                        mFVInstance = GameFloatView.getInstance(activity,
                                new GameFloatListener() {

                                    @Override
                                    public void onSwitchAccountClick(
                                            Activity activity) {
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
        builder.setNeutralButton(CommonStr.LOGIN_CANCEL, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // mUserCallBack.onLogin(new Result(Result.CODE_CANCEL,
                // channelTag() + " login cancel."));
                mUserCallBack.onLoginCancel("login cancel");
                loginDialog.dismiss();
            }
        });

        builder.setNegativeButton(CommonStr.LOGIN_FAIL, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUserCallBack.onLoginFail(XGErrorCode.OTHER_ERROR,
                        "login failed");
            }
        });
        builder.show();
        // mLoginDialog = builder.create();
        // mLoginDialog.show();

        // loginDialog.show();
    }

    public ScrollView addOperatorSpecify(Context context) {
        ScrollView sv = new ScrollView(context);
        LinearLayout operatorSLay = new LinearLayout(context);
        operatorSLay.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(650, 150);
        sv.setPadding(15, 10, 15, 0);
        sv.setLayoutParams(params);

        TextView title = new TextView(context);
        title.setText("操作说明");
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView info = new TextView(context);
        info.setText("登录成功，记录用户名和密码，启动悬浮窗，如果没有输入用户名和密码则再次输入;\n登录失败,回调登录失败方法onLogoutFail;\n"
                + "登录取消，取消登录，登录框消失，悬浮窗不出现");
        info.setTextSize(18);
        operatorSLay.addView(title);
        operatorSLay.addView(info);
        sv.addView(operatorSLay);
        return sv;
    }

}
