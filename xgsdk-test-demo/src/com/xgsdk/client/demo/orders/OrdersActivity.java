
package com.xgsdk.client.demo.orders;

import com.xgsdk.client.core.util.RUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrdersActivity extends Activity {

    private ListView mLVOrders;

    private ArrayList<HashMap<String, Object>> mOrderList = new ArrayList<HashMap<String, Object>>();

    private static final String KEY_ORDER_ID = "OrderId";
    private static final String KEY_ORDER_TIME = "OrderTime";
    private static final String KEY_ORDER_STATUS = "OrderStatus";

    private static final int STATUS_SUCCESS = 1;
    private static final int STATUS_FAIL = 2;
    private static final int STATUS_UNKNOWN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(RUtil.getLayout(getApplicationContext(),
                "xg_demo_activity_orders"));
        mLVOrders = (ListView) findViewById(RUtil.getId(
                getApplicationContext(), "xg_lv_orders"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Date date = new Date(System.currentTimeMillis() - 123456);
            sdf.format(date);
            map.put(KEY_ORDER_ID, System.currentTimeMillis() % 10000 + 100000);
            map.put(KEY_ORDER_TIME, sdf.format(date));
            String status = "Unknown";
            switch (i % 3) {
                case STATUS_SUCCESS:
                    status = "Success";
                    break;
                case STATUS_FAIL:
                    status = "Fail";
                    break;
                case STATUS_UNKNOWN:
                    status = "Unknown";
                    break;
            }

            map.put(KEY_ORDER_STATUS, status);
            mOrderList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mOrderList,
                RUtil.getLayout(getApplicationContext(), "xg_demo_item_order"),

                new String[] {
                        KEY_ORDER_ID, KEY_ORDER_TIME, KEY_ORDER_STATUS
                },

                new int[] {
                        RUtil.getId(getApplicationContext(), "xg_order_id"),
                        RUtil.getId(getApplicationContext(), "xg_order_time"),
                        RUtil.getId(getApplicationContext(), "xg_order_status")
                });

        mLVOrders.setAdapter(adapter);
        View header = getLayoutInflater()
                .inflate(
                        RUtil.getLayout(getApplicationContext(),
                                "xg_demo_orders_title"), null);
        mLVOrders.addHeaderView(header);
    }
}
