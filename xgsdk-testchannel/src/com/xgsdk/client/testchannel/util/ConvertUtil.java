
package com.xgsdk.client.testchannel.util;

import com.xgsdk.client.core.utils.XGLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ConvertUtil {

    public static HashMap<String, String> toMap(Object model) {
        if (model == null)
            return null;
        HashMap<String, String> map = new HashMap<String, String>();
        Field[] fields = model.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String attrName = fields[i].getName();
            if (attrName.equalsIgnoreCase("additionalParams")
                    || attrName.equalsIgnoreCase("KEY_XG_ORDER_ID"))
                continue;
            String nameGetter = attrName.substring(0, 1).toUpperCase()
                    + attrName.substring(1);
            String type = fields[i].getGenericType().toString();
            Method m = null;
            try {
                m = model.getClass().getMethod("get" + nameGetter);
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                XGLog.e("no such method", e);
            }
            Object value = null;
            try {
                if (m != null)
                    value = m.invoke(model);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                XGLog.e("IllegalAccessException", e);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                XGLog.e("IllegalArgumentException", e);
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                XGLog.e("InvocationTargetException", e);
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
