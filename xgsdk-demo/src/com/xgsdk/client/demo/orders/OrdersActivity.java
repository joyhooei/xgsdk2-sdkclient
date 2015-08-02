
package com.xgsdk.client.demo.orders;

import com.xgsdk.client.demo.GameInfo;
import com.xgsdk.client.demo.utils.RUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class OrdersActivity extends Activity {

    private ListView mLVOrders;
    private SimpleAdapter mAdapter;

    private ArrayList<HashMap<String, Object>> mOrderList = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(RUtil.getLayout(getApplicationContext(),
                "xg_demo_activity_orders"));
        mLVOrders = (ListView) findViewById(RUtil.getId(
                getApplicationContext(), "xg_lv_orders"));

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
                        map.put(OrderUtils.KEY_ORDER_DETAILS,
                                json.get(OrderUtils.KEY_ORDER_DETAILS));
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
}
