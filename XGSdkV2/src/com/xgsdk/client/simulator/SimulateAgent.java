
package com.xgsdk.client.simulator;

import com.seasun.powerking.sdkclient.PayService;
import com.seasun.powerking.sdkclient.ProductConfig;
import com.xgsdk.client.agent.XGAgent;
import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.entity.PayInfo;
import com.xgsdk.client.simulator.view.GameFloatView;
import com.xgsdk.client.simulator.view.GameFloatView.GameFloatListener;
import com.xgsdk.client.simulator.view.LoginDialog;
import com.xgsdk.client.simulator.view.OrderDetailLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimulateAgent extends XGAgent {

    private static final String CHANNEL_TAG = "test";
    private GameFloatView mFVInstance;

    @Override
    public String channelTag() {
        return CHANNEL_TAG;
    }

    @Override
    public void init(final Activity activity) {
        mFVInstance = GameFloatView.getInstance(activity,
                new GameFloatListener() {

                    @Override
                    public void onSwitchAccountClick(Activity activity) {
                        new LoginDialog(activity, mUserCallBack)
                                .showLoginDialog();

                    }

                    @Override
                    public void onOpenUserCenterClick() {
                        // TODO Auto-generated method stub

                    }
                });
    }

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
        // TODO Auto-generated method stub
        super.pay(activity, payment, payCallBack);

        showPayDialog(activity, payment, payCallBack);
    }

    private void showPayDialog(Activity activity, final PayInfo payment,
            PayCallBack payCallBack) {
        AlertDialog.Builder builder = new Builder(activity);
        builder.setTitle("please verify your order");
        final AlertDialog payDialog = builder.create();
        OrderDetailLayout orderLayout = new OrderDetailLayout(activity, payment);
        builder.setView(orderLayout);
        builder.setPositiveButton("SUCCESS", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mPayCallBack.onSuccess("pay success!");
                String xgsdkAppId = ProductConfig.getXgAppId();
                String xgsdkAppKey = ProductConfig.getXgAppKey();
                String channelId = ProductConfig.getChannelId();
                // accountId priceTitle is null
                try {
                    String orderId = PayService.createOrderInThread(xgsdkAppId,
                            xgsdkAppKey, channelId, payment.getUid(),
                            payment.getProductId(), payment.getProductName(),
                            payment.getProductDesc(),
                            String.valueOf(payment.getProductCount()),
                            String.valueOf(payment.getProductTotalPrice()),
                            payment.getServerId(),
                            String.valueOf(payment.getRoleId()),
                            payment.getRoleName(), payment.getCurrencyName(),
                            payment.getExt());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        builder.setNeutralButton("CANCEL", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mPayCallBack.onCancel("pay cancel!");
                payDialog.dismiss();
            }
        });
        builder.setNegativeButton("FAIL", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mPayCallBack.onFail("sorry,pay failed!");
            }
        });
        builder.show();
    }

    @Override
    public void exit(Activity activity, final ExitCallBack exitCallBack,
            String customParams) {
        // TODO Auto-generated method stub
        // super.exit(activity, exitCallBack, customParams);
        AlertDialog.Builder builder = new Builder(activity);
        builder.setTitle("退出遊戲");
        final AlertDialog exitDialog = builder.create();
        LinearLayout infoLayout = new LinearLayout(activity);
        TextView text = new TextView(activity);
        text.setText("退出遊戲.....");
        infoLayout.addView(text);
        builder.setView(infoLayout);
        builder.setPositiveButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                exitCallBack.onCancel();
            }

        });
        builder.setNeutralButton("使用遊戲方退出", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                exitCallBack.onNoChannelExiter();

            }

        });
        builder.setNegativeButton("直接退出", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                exitCallBack.onExit();
            }

        });
        builder.show();
    }

}
