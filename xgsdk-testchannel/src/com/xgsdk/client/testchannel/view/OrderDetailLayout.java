
package com.xgsdk.client.testchannel.view;

import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.testchannel.util.CommonStr;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class OrderDetailLayout extends LinearLayout {
    private PayInfo payInfo;
    private ScrollView orderLayout;

    public OrderDetailLayout(Context context, PayInfo payInfo) {
        super(context);
        this.payInfo = payInfo;
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        ScrollView orderLayout = new ScrollView(context);
        // this.removeAllViews();
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 200);
        // orderLayout.setPadding(45, 10, 45, 10);
        params.setMargins(45, 0, 45, 0);
        orderLayout.setLayoutParams(params);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView orderTitle = new TextView(context);
        orderTitle.setText("订单参数");
        orderTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        orderTitle.setTextSize(18);
        ll.addView(orderTitle);
        for (Map.Entry<String, String> entry : CommonStr.orderMap.entrySet()) {
            String attrName = entry.getKey();
            String value = getAttrValue(attrName, payInfo);
            TextView tt = new TextView(context);
            tt.setText(entry.getValue() + ":   " + value);
            ll.addView(tt);
        }
        orderLayout.addView(ll);
        this.addView(orderLayout);
        this.addView(addOperatorSpecify(context));
    }

    public String getAttrValue(String attrName, Object model) {
        String nameGetter = attrName.substring(0, 1).toUpperCase()
                + attrName.substring(1);
        Method m = null;
        try {
            m = payInfo.getClass().getMethod("get" + nameGetter);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object value = null;
        try {
            value = m.invoke(model, null);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String result = value == null ? "" : value.toString();
        return result;
    }

    public ScrollView addOperatorSpecify(Context context) {
        ScrollView sv = new ScrollView(context);
        LinearLayout operatorSLay = new LinearLayout(context);
        operatorSLay.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(650, 150);
        sv.setPadding(15, 10, 15, 20);
        sv.setLayoutParams(params);
        TextView title = new TextView(context);
        title.setText("操作说明");
        title.setTextSize(18);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView info = new TextView(context);
        info.setText("支付成功，支付成功回调，通知服务器创建订单;\n支付失败,支付失败回调;\n" + "支付取消，执行支付取消回调");
        operatorSLay.addView(title);
        operatorSLay.addView(info);
        sv.addView(operatorSLay);
        return sv;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }

    public PayInfo getPayInfo() {
        return payInfo;
    }

}
