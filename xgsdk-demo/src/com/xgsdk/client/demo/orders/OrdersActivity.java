
package com.xgsdk.client.demo.orders;

import com.xgsdk.client.core.service.PayService;
import com.xgsdk.client.core.service.PayStatus;
import com.xgsdk.client.core.service.Result;
import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.demo.GameInfo;
import com.xgsdk.client.demo.utils.RUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class OrdersActivity extends Activity {

    private ListView mLVOrders;
    private Button mBtnClean;
    private SimpleAdapter mAdapter;
    
    private static final int MSG_REFRESH = 1000;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch(msg.what){
                case MSG_REFRESH:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        };
    };

    private ArrayList<HashMap<String, Object>> mOrderList = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(RUtil.getLayout(getApplicationContext(),
                "xg_demo_activity_orders"));
        mLVOrders = (ListView) findViewById(RUtil.getId(
                getApplicationContext(), "xg_lv_orders"));
        mLVOrders.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                if (position < 1) {
                    return;
                }
                HashMap<String, Object> map = mOrderList.get(position - 1);
                if (map != null) {
                    String orderId = (String) map.get(OrderUtils.KEY_ORDER_ID);
                    if (!TextUtils.isEmpty(orderId)) {
                        try {
                            Result result = PayService
                                    .queryOrderStatusInThread(
                                            OrdersActivity.this, orderId);
                            if (result != null && result.getData() != null) {
                                JSONObject json = new JSONObject(result
                                        .getData());
                                String status = json.optString("status");
                                if (!TextUtils.isEmpty(status)) {
                                    PayStatus st = PayStatus.valueOf(Integer
                                            .valueOf(status));
                                    XGLog.d("query order status." + st);
                                    OrderUtils.updateOrderStatus(
                                            getApplicationContext(), GameInfo
                                                    .getInstance().getUid(),
                                            orderId, st.name());

                                    new LoadOrdersTask().execute();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
        mBtnClean = (Button) findViewById(RUtil.getId(getApplicationContext(),
                "xg_btn_clean"));

        mBtnClean.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showCleanDialog();
            }
        });

        mAdapter = new SimpleAdapter(this, mOrderList, RUtil.getLayout(
                getApplicationContext(), "xg_demo_item_order"),

        new String[] {
                OrderUtils.KEY_ORDER_ID, OrderUtils.KEY_ORDER_DETAILS,
                OrderUtils.KEY_ORDER_TIME, OrderUtils.KEY_ORDER_STATUS
        },

        new int[] {
                RUtil.getId(getApplicationContext(), "xg_order_id"),
                RUtil.getId(getApplicationContext(), "xg_order_details"),
                RUtil.getId(getApplicationContext(), "xg_order_time"),
                RUtil.getId(getApplicationContext(), "xg_order_status")
        });

        mLVOrders.setAdapter(mAdapter);
        View header = getLayoutInflater()
                .inflate(
                        RUtil.getLayout(getApplicationContext(),
                                "xg_demo_orders_title"), null);
        mLVOrders.addHeaderView(header);
        new LoadOrdersTask().execute();
    }

    private class LoadOrdersTask extends
            AsyncTask<Void, Integer, HashMap<String, JSONObject>> {

        @Override
        protected HashMap<String, JSONObject> doInBackground(Void... params) {
            return OrderUtils.getOrders(OrdersActivity.this, GameInfo
                    .getInstance().getUid());
        }

        @Override
        protected void onPostExecute(HashMap<String, JSONObject> result) {
            super.onPostExecute(result);
            mOrderList.clear();
            if (result != null && !result.isEmpty()) {
                Set<String> set = result.keySet();
                for (String orderid : set) {
                    try {
                        JSONObject json = result.get(orderid);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put(OrderUtils.KEY_ORDER_ID, orderid);
                        // map.put(OrderUtils.KEY_ORDER_DETAILS,
                        // json.get(OrderUtils.KEY_ORDER_DETAILS));
                        map.put(OrderUtils.KEY_ORDER_DETAILS, null);
                        map.put(OrderUtils.KEY_ORDER_TIME,
                                json.get(OrderUtils.KEY_ORDER_TIME));
                        map.put(OrderUtils.KEY_ORDER_STATUS,
                                json.get(OrderUtils.KEY_ORDER_STATUS));
                        mOrderList.add(map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                mAdapter.notifyDataSetChanged();
            } else {

            }
        }
    }

    private void showCleanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(RUtil.getString(getApplicationContext(),
                "xg_clean_msg"));
        builder.setPositiveButton(
                RUtil.getString(getApplicationContext(), "xg_ok"),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderUtils.cleanOrders(getApplicationContext(),
                                GameInfo.getInstance().getUid());
                        mOrderList.clear();
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton(
                RUtil.getString(getApplicationContext(), "xg_cancel"),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
