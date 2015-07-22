
package com.xgsdk.client.demo;

import com.seasun.powerking.sdkclient.ProductConfig;
import com.seasun.powerking.sdkclient.Util;
import com.seasun.powerking.sdkclient.callback.IConstant;
import com.seasun.xgsdk.XGSDKAndroidImpl;
import com.xgsdk.client.core.util.ToastUtil;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.plugin.listenses.Listener;

import org.json.JSONException;
import org.json.JSONObject;
//import org.season.demo.shuyou.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity2 extends Activity implements OnClickListener {

    private static final String TAG = "XGSDK_DEMO";

    private XGSDKAndroidImpl xgsdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.setContext(this);
        Util.setActivity(this);
        setContentView(R.layout.activity_main);
        xgsdk = new XGSDKAndroidImpl(new Listener() {

            @Override
            public void onListener(String json) {
                JSONObject parseObject;
                try {
                    parseObject = new JSONObject(json);

                    XGLogger.d(json);
                    String callbackType = parseObject.getString("callbackType");
                    String flag = parseObject
                            .getString(IConstant.KEY_successFlag);
                    // 登录类型的回调
                    if (IConstant.XGSDK_CALLBACK_LOGIN_FUNC
                            .equalsIgnoreCase(callbackType)) {

                        // 登录成功标示
                        if (IConstant.F_YES.equalsIgnoreCase(flag)) {
                            String data = parseObject
                                    .getString(IConstant.KEY_data);
                            // 渠道用户信息
                            JSONObject dataInfo = new JSONObject(data);
                            String authInfo = dataInfo
                                    .getString(IConstant.KEY_retMsg);
                            TestUtil.auth(MainActivity2.this, "1.0", authInfo);
                            byte[] decode = Base64.decode(authInfo,
                                    Base64.NO_WRAP);
                            String msg = new String(decode);
                            XGLogger.d(msg);
                            ToastUtil.showToast(getApplicationContext(),
                                    "login success:\n" + msg);

                            xgsdk.showToolBar(MainActivity2.this, "1");
                        } else {
                            ToastUtil.showToast(getApplicationContext(),
                                    "login fail:\n" + json);
                        }
                    } else if (IConstant.XGSDK_CALLBACK_EXIT_FUNC
                            .equalsIgnoreCase(callbackType)) {
                        if (IConstant.F_YES.equalsIgnoreCase(flag)) {
                            finish();
                        }
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, this);
        xgsdk.init(this, null);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.pay).setOnClickListener(this);
        findViewById(R.id.switch_account).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.exit).setOnClickListener(this);

        ToastUtil.showToast(this, ProductConfig.getAuthUrl());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                xgsdk.login(this, "");
                // test();

                break;
            case R.id.pay:
                showDialog();
                break;
            case R.id.switch_account:
                xgsdk.switchUser(this, "");
                break;
            case R.id.logout:
                xgsdk.logout(this, "");
                break;
            case R.id.exit:
                xgsdk.onExitGame(this, "");
                break;
            case R.id.show_orders:
                Intent intent = new Intent(this, OrdersActivity.class);
                startActivity(intent);
                break;
            default:

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xgsdk.hideToolBar(this);
    }

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog payDialog = builder.create();
        final View dialogView = inflater.inflate(R.layout.layout_dialog_pay,
                null);
        final EditText etMoney = (EditText) dialogView
                .findViewById(R.id.et_money);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.ok, new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String payInfo = getPayInfo("abac", "112", "大宝剑", "宝剑锋从磨砺出",
                        "1", "1", "元宝", "1", "桃园结义", "su27", "侧卫", "10",
                        "1234567890" + System.currentTimeMillis());
                xgsdk.pay(MainActivity2.this, payInfo);
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

    public String getPayInfo(String accountId, String productId,
            String productName, String productDec, String price, String amount,
            String currencyName, String serverId, String serverName,
            String roleId, String roleName, String balance, String ext) {

        JSONObject json = new JSONObject();
        try {
            json.put("accountID", accountId);
            json.put("productID", productId);
            json.put("productName", productName);
            json.put("productDec", productDec);
            json.put("price", price);
            json.put("amount", amount);
            json.put("currencyName", currencyName);
            json.put("serverId", serverId);
            json.put("serverName", serverName);
            json.put("roleId", roleId);
            json.put("roleName", roleName);
            json.put("balance", balance);
            json.put("ext", ext);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json.toString();
    }

    public static void test() {
        String str = "{\"appId\":\"1bb3630e3779ef9e86eb\",\"merId\":\"D97E168D6459\",\"transData\":\"\"}";
        try {
            JSONObject json = new JSONObject(str);
            XGLogger.d(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
