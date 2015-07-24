
package com.xgsdk.client.simulator;

import com.seasun.powerking.sdkclient.PayService;
import com.seasun.powerking.sdkclient.ProductConfig;
import com.xgsdk.client.agent.XGAgent;
import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.entity.PayInfo;
import com.xgsdk.client.simulator.util.CommonStr;
import com.xgsdk.client.simulator.view.GameFloatView;
import com.xgsdk.client.simulator.view.GameFloatView.GameFloatListener;
import com.xgsdk.client.simulator.view.LoginDialog;
import com.xgsdk.client.simulator.view.OrderDetailLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
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
        TextView title = new TextView(activity);
        title.setText(CommonStr.PAY_ORDER);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setTextSize(22);
        builder.setCustomTitle(title);
        final AlertDialog payDialog = builder.create();
        OrderDetailLayout orderLayout = new OrderDetailLayout(activity, payment);
        builder.setView(orderLayout);
        builder.setPositiveButton(CommonStr.PAY_SUCCESS, new OnClickListener() {
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

        builder.setNeutralButton(CommonStr.PAY_CANCEL, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mPayCallBack.onCancel("pay cancel!");
                payDialog.dismiss();
            }
        });
        builder.setNegativeButton(CommonStr.PAY_FAIL, new OnClickListener() {

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
        TextView title = new TextView(activity);
        title.setText(CommonStr.CLICKEXITORBACK);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setTextSize(22);
        builder.setCustomTitle(title);
        final AlertDialog exitDialog = builder.create();

        builder.setView(exitInfo(activity));
        builder.setPositiveButton(CommonStr.EXIT_CANCEL, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                exitCallBack.onCancel();
            }

        });
        builder.setNeutralButton(CommonStr.EXIT_USEGAMER,
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        exitCallBack.onNoChannelExiter();

                    }

                });
        builder.setNegativeButton(CommonStr.EXIT_IMMEDIATE,
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        exitCallBack.onExit();
                    }

                });
        builder.show();
    }

    public ScrollView exitInfo(Activity activity) {
        ScrollView sv = new ScrollView(activity);
        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(550, 200);
        sv.setPadding(15, 10, 15, 20);
        sv.setLayoutParams(params);
        TextView title = new TextView(activity);
        title.setText("操作说明");
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.addView(title);
        TextView exitInfo = new TextView(activity);
        exitInfo.setText("使用游戏方退出：跳转到游戏方的退出框\n" + "直接退出：直接退出游戏，不弹出游戏方的退出框\n"
                + "取消退出：取消退出");
        exitInfo.setPadding(50, 0, 0, 0);
        ll.addView(exitInfo);
        sv.addView(ll);

        return sv;
    }

}
