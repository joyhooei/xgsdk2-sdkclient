
package com.xgsdk.client.simulator.view;

import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.entity.PayInfo;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailLayout extends LinearLayout {
    private PayInfo payInfo;
    private ScrollView orderLayout;

    public OrderDetailLayout(Context context, PayInfo payInfo) {
        super(context);
        this.payInfo = payInfo;
        HashMap<String, String> keyValues = toMap(payInfo);
        ScrollView orderLayout = new ScrollView(context);
        this.setOrientation(LinearLayout.VERTICAL);
        // this.removeAllViews();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 200);
        orderLayout.setPadding(15, 10, 15, 10);
        orderLayout.setLayoutParams(params);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        for (Map.Entry<String, String> entry : keyValues.entrySet()) {
            TextView tt = new TextView(context);
            tt.setText(entry.getKey() + ":   " + entry.getValue());
            ll.addView(tt);
        }
        orderLayout.addView(ll);
        this.addView(orderLayout);
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public HashMap<String, String> toMap(Object model) {
        if (model == null)
            return null;
        HashMap<String, String> map = new HashMap<String, String>();
        Field[] fields = model.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String attrName = fields[i].getName();
            if (attrName.equalsIgnoreCase("extraData"))
                continue;
            String nameGetter = attrName.substring(0, 1).toUpperCase()
                    + attrName.substring(1);
            String type = fields[i].getGenericType().toString();
            Method m = null;
            try {
                m = model.getClass().getMethod("get" + nameGetter);
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                XGLogger.e("no such method", e);
            }
            Object value = null;
            try {
                if (m != null)
                    value = m.invoke(model);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                XGLogger.e("IllegalAccessException", e);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                XGLogger.e("IllegalArgumentException", e);
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                XGLogger.e("InvocationTargetException", e);
            }
            String strValue = "";
            if (type.equals("class java.lang.String")) {
                strValue = (String) value;
            } else if (type.equals("class java.lang.Integer")
                    || type.equals("int")) {
                strValue = String.valueOf((Integer) value);
            } else if (type.equals("class java.lang.Double")) {
                strValue = String.valueOf((Double) value);
            } else if (type.equals("class java.lang.Boolean")) {
                strValue = String.valueOf((Boolean) value);
            }
            if (type.equals("java.util.HashMap<java.lang.String, java.lang.String>")) {
                map.putAll(map);
            } else {
                map.put(attrName, strValue);
            }

        }
        return map;
    }

}
