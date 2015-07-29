
package com.xgsdk.client.simulator.view;

import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.entity.PayInfo;

import android.content.Context;
import android.view.Gravity;
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
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        HashMap<String, String> keyValues = toMap(payInfo);
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
        for (Map.Entry<String, String> entry : keyValues.entrySet()) {
            TextView tt = new TextView(context);
            String attrName = entry.getKey().trim();
            String cnName = convertCnName(attrName);
            tt.setText(cnName + ":   " + entry.getValue());
            ll.addView(tt);
        }
        orderLayout.addView(ll);
        this.addView(orderLayout);
        this.addView(addOperatorSpecify(context));
    }

    public String convertCnName(String attrName) {
        String cnName = "";
        if (attrName.equalsIgnoreCase("uid")) {
            cnName = "用户ID";
        } else if (attrName.equals("productId")) {
            cnName = "产品ID";
        } else if (attrName.equals("productName")) {
            cnName = "产品名称";
        } else if (attrName.equals("productDesc")) {
            cnName = "产品描述";
        } else if (attrName.equals("productTotalPrice")) {
            cnName = "产品总价格(元)";
        } else if (attrName.equals("productUnitPrice")) {
            cnName = "产品单价(元)";
        } else if (attrName.equals("productCount")) {
            cnName = "产品数量";
        } else if (attrName.equals("exchangeRate")) {
            cnName = "交易率";
        } else if (attrName.equals("currencyName")) {
            cnName = "游戏金额单位";
        } else if (attrName.equals("ext")) {
            cnName = "透传字段";
        } else if (attrName.equals("notifyURL")) {
            cnName = "支付通知回调地址";
        } else if (attrName.equals("roleId")) {
            cnName = "游戏角色ID";
        } else if (attrName.equals("roleName")) {
            cnName = "游戏角色名称";
        } else if (attrName.equals("serverId")) {
            cnName = "游戏角色服务器ID";
        } else if (attrName.equals("serverName")) {
            cnName = "游戏角色服务器名称";
        } else if (attrName.equals("balance")) {
            cnName = "游戏内货币余额";
        } else if (attrName.equals("gameOrderId")) {
            cnName = "游戏创建订单Id";
        }
        return cnName;
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

    public HashMap<String, String> toMap(Object model) {
        if (model == null)
            return null;
        HashMap<String, String> map = new HashMap<String, String>();
        Field[] fields = model.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String attrName = fields[i].getName();
            if (attrName.equalsIgnoreCase("additionalParams"))
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
