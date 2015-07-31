
package com.xgsdk.client.demo;

import com.xgsdk.client.api.XGSDK;
import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.callback.UserCallBack;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.demo.orders.OrdersActivity;
import com.xgsdk.client.demo.utils.RUtil;
import com.xgsdk.client.demo.utils.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(RUtil.getLayout(getApplicationContext(),
                "xg_demo_activity_main"));

        findViewById(RUtil.getId(getApplicationContext(), "xg_login"))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        XGSDK.getInstance().login(MainActivity.this, null);

                    }
                });
        findViewById(RUtil.getId(getApplicationContext(), "xg_pay"))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showDialog();
                    }
                });
        findViewById(RUtil.getId(getApplicationContext(), "xg_switch_account"))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        XGSDK.getInstance().switchAccount(MainActivity.this,
                                null);
                    }
                });
        findViewById(RUtil.getId(getApplicationContext(), "xg_logout"))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        XGSDK.getInstance().logout(MainActivity.this, null);
                    }
                });

        findViewById(RUtil.getId(getApplicationContext(), "xg_exit"))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        XGSDK.getInstance().exit(MainActivity.this,
                                new ExitCallBack() {

                                    @Override
                                    public void onNoChannelExiter() {
                                        Dialog dialog = new AlertDialog.Builder(
                                                MainActivity.this)
                                                .setTitle("退出游戏")
                                                .setCancelable(false)
                                                .setMessage("您确定要退出游戏吗？")
                                                .setPositiveButton(
                                                        "确定",
                                                        new DialogInterface.OnClickListener() {

                                                            @Override
                                                            public void onClick(
                                                                    DialogInterface dialog,
                                                                    int which) {
                                                                dialog.dismiss();
                                                                finish();
                                                            }

                                                        })
                                                .setNegativeButton(
                                                        "取消",
                                                        new DialogInterface.OnClickListener() {

                                                            @Override
                                                            public void onClick(
                                                                    DialogInterface dialog,
                                                                    int which) {
                                                                dialog.dismiss();
                                                            }
                                                        }).create();
                                        dialog.show();

                                    }

                                    @Override
                                    public void onExit() {
                                        finish();

                                    }

                                    @Override
                                    public void onCancel() {
                                        ToastUtil.showToast(MainActivity.this,
                                                "回到游戏");

                                    }
                                }, null);
                    }
                });
        findViewById(RUtil.getId(getApplicationContext(), "xg_show_orders"))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,
                                OrdersActivity.class);
                        startActivity(intent);
                    }
                });

        ToggleButton tbSwitchMore = (ToggleButton) findViewById(RUtil.getId(
                getApplicationContext(), "xg_tb_switch_more"));

        final LinearLayout llMore = (LinearLayout) findViewById(RUtil.getId(
                getApplicationContext(), "xg_layout_more"));
        tbSwitchMore.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                if (isChecked) {
                    llMore.setVisibility(View.VISIBLE);
                } else {
                    llMore.setVisibility(View.GONE);
                }

            }
        });

        XGSDK.getInstance().onCreate(this);
        XGSDK.getInstance().init(this);
        XGSDK.getInstance().setUserCallBack(new UserCallBack() {

            @Override
            public void onLogoutSuccess(String msg) {
                ToastUtil.showToast(MainActivity.this, "logout success." + msg);

            }

            @Override
            public void onLogoutFail(int code, String msg) {
                ToastUtil.showToast(MainActivity.this, "logout fail." + code
                        + " " + msg);

            }

            @Override
            public void onLoginSuccess(String authInfo) {
                Log.d(TAG, authInfo);
            }

            @Override
            public void onLoginFail(int code, String msg) {
                ToastUtil.showToast(MainActivity.this, "login fail." + code
                        + " " + msg);

            }

            @Override
            public void onInitFail(int code, String msg) {
                ToastUtil.showToast(MainActivity.this, "init fail." + code
                        + " " + msg);

            }

            @Override
            public void onLoginCancel(String msg) {
                ToastUtil.showToast(MainActivity.this, "login cancel." + msg);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        XGSDK.getInstance().onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        XGSDK.getInstance().onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        XGSDK.getInstance().onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        XGSDK.getInstance().onStop(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        XGSDK.getInstance().onRestart(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        XGSDK.getInstance().onNewIntent(this, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XGSDK.getInstance().onActivityResult(this, requestCode, resultCode,
                data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XGSDK.getInstance().onDestory(this);
    }

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog payDialog = builder.create();
        final View dialogView = inflater.inflate(RUtil.getLayout(
                getApplicationContext(), "xg_demo_layout_dialog_pay"), null);
        final EditText etMoney = (EditText) dialogView.findViewById(RUtil
                .getId(getApplicationContext(), "xg_et_money"));
        etMoney.setSelection(TextUtils.isEmpty(etMoney.getText().toString()) ? 0
                : etMoney.getText().toString().length());
        final EditText etCount = (EditText) dialogView.findViewById(RUtil
                .getId(getApplicationContext(), "xg_et_count"));
        etCount.setSelection(TextUtils.isEmpty(etCount.getText().toString()) ? 0
                : etCount.getText().toString().length());
        builder.setView(dialogView);
        builder.setPositiveButton(
                RUtil.getString(getApplicationContext(), "xg_ok"),
                new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // String payInfo = getPayInfo("abac", "112", "大宝剑",
                        // "宝剑锋从磨砺出",
                        // "1", "1", "元宝", "1", "桃园结义", "su27", "侧卫", "10",
                        // "1234567890" + System.currentTimeMillis());
                        // xgsdk.pay(MainActivity1.this, payInfo);
                        PayInfo payment = new PayInfo();
                        payment.setUid("4fd0144f02840ae77b6f42346c90d8bd");
                        payment.setProductDesc("倚天不出谁与争锋");
                        payment.setServerId("11");
                        payment.setProductId("payment017");
                        payment.setProductName("大宝剑");
                        String extraInfo = XGSDK.getInstance().getChannelId()
                                + " extraInfo ";
                        payment.setExt(extraInfo);
                        int totalPrice = TextUtils.isEmpty(etMoney.getText()) ? 0
                                : Integer.valueOf(etMoney.getText().toString());
                        int count = TextUtils.isEmpty(etCount.getText()) ? 1
                                : Integer.valueOf(etCount.getText().toString());
                        payment.setProductCount(count);
                        payment.setCurrencyName("元宝");
                        payment.setProductTotalPrice(totalPrice);
                        payment.setProductUnitPrice(1);
                        XGSDK.getInstance().pay(MainActivity.this, payment,
                                new PayCallBack() {

                                    @Override
                                    public void onSuccess(String msg) {
                                        ToastUtil.showToast(MainActivity.this,
                                                "pay success." + msg);

                                    }

                                    @Override
                                    public void onFail(int code, String msg) {
                                        ToastUtil.showToast(MainActivity.this,
                                                "pay fail." + code + " " + msg);
                                    }

                                    @Override
                                    public void onCancel(String msg) {
                                        ToastUtil.showToast(MainActivity.this,
                                                "pay cancel." + msg);

                                    }
                                });
                        payDialog.dismiss();
                    }
                });

        builder.setNegativeButton(
                RUtil.getString(getApplicationContext(), "xg_cancel"),
                new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        payDialog.dismiss();
                    }
                });

        builder.show();

    }

}
