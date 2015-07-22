
package com.xgsdk.client.demo;

import com.seasun.powerking.sdkclient.ProductConfig;
import com.xgsdk.client.XGSDK;
import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.callback.UserCallBack;
import com.xgsdk.client.core.util.ToastUtil;
import com.xgsdk.client.entity.PayInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

//import org.season.demo.shuyou.R;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "XGSDK_DEMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.pay).setOnClickListener(this);
        findViewById(R.id.switch_account).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.exit).setOnClickListener(this);

        ToastUtil.showToast(this, ProductConfig.getAuthUrl());

        XGSDK.getInstance().onCreate(this);
        XGSDK.getInstance().init(this);
        XGSDK.getInstance().setUserCallBack(new UserCallBack() {

            @Override
            public void onLogoutSuccess(String msg) {
                ToastUtil.showToast(getApplicationContext(), "logout success."
                        + msg);

            }

            @Override
            public void onLogoutFail(String msg) {
                ToastUtil.showToast(getApplicationContext(), "logout fail."
                        + msg);

            }

            @Override
            public void onLoginSuccess(String authInfo) {
                TestUtil.auth(MainActivity.this, "2.0", authInfo);
            }

            @Override
            public void onLoginFail(String msg) {
                ToastUtil.showToast(getApplicationContext(), "login fail."
                        + msg);

            }

            @Override
            public void onInitFail(String msg) {
                ToastUtil
                        .showToast(getApplicationContext(), "init fail." + msg);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                XGSDK.getInstance().login(this, null);
                break;
            case R.id.pay:
                showDialog();
                break;
            case R.id.switch_account:
                XGSDK.getInstance().switchAccount(this, null);
                break;
            case R.id.logout:
                XGSDK.getInstance().logout(this, null);
                break;
            case R.id.exit:
                XGSDK.getInstance().exit(this, new ExitCallBack() {

                    @Override
                    public void onNoChannelExiter() {
                        Dialog dialog = new AlertDialog.Builder(
                                MainActivity.this)
                                .setTitle("退出游戏")
                                .setCancelable(false)
                                .setMessage("您确定要退出游戏吗？")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                dialog.dismiss();
                                                finish();
                                            }

                                        })
                                .setNegativeButton("取消",
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
                        // TODO Auto-generated method stub

                    }
                }, null);
                break;
            case R.id.show_orders:
                Intent intent = new Intent(this, OrdersActivity.class);
                startActivity(intent);
                break;
            default:

        }

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
        final View dialogView = inflater.inflate(R.layout.layout_dialog_pay,
                null);
        final EditText etMoney = (EditText) dialogView
                .findViewById(R.id.et_money);
        etMoney.setSelection(TextUtils.isEmpty(etMoney.getText().toString()) ? 0
                : etMoney.getText().toString().length());
        final EditText etCount = (EditText) dialogView
                .findViewById(R.id.et_count);
        etCount.setSelection(TextUtils.isEmpty(etCount.getText().toString()) ? 0
                : etCount.getText().toString().length());
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.ok, new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // String payInfo = getPayInfo("abac", "112", "大宝剑", "宝剑锋从磨砺出",
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
                int count = TextUtils.isEmpty(etCount.getText()) ? 1 : Integer
                        .valueOf(etCount.getText().toString());
                payment.setProductCount(count);
                payment.setCurrencyName("元宝");
                payment.setProductTotalPrice(totalPrice);
                payment.setProductUnitPrice(1);
                XGSDK.getInstance().pay(MainActivity.this, payment,
                        new PayCallBack() {

                            @Override
                            public void onSuccess(String msg) {
                                ToastUtil.showToast(getApplicationContext(),
                                        "pay success." + msg);

                            }

                            @Override
                            public void onFail(String msg) {
                                ToastUtil.showToast(getApplicationContext(),
                                        "pay fail." + msg);
                            }

                            @Override
                            public void onCancel(String msg) {
                                ToastUtil.showToast(getApplicationContext(),
                                        "pay cancel." + msg);

                            }
                        });
                payDialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.cancel,
                new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        payDialog.dismiss();
                    }
                });

        builder.show();

    }

}
