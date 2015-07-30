
package com.xgsdk.client.core.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Map;

public class CommonUtils {

    public static final <T, V> T[] getKeysArrayFromMap(Map<T, V> map,
            Class<T> type) {
        T[] keys = null;
        if (map != null && map.size() > 0) {
            keys = (T[]) Array.newInstance(type, map.size());
            map.keySet().toArray(keys);
        }
        return keys;
    }

    public void method() {
        Method m = null;
    }

    public static boolean supportMethodInSubClass(Object object,
            String methodName, Class<?>... parameterTypes) {

        if (object == null || methodName == null) {
            return false;
        }

        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            return false;
        }

        String declaringClassName = method.getDeclaringClass().getName();
        String objectClassName = object.getClass().getName();
        if (!declaringClassName.equals(objectClassName)) {
            return false;
        }

        String mName = method.getName();
        if (mName == null || !methodName.equals(mName)) {
            return false;
        }

        if (methodName.equals(mName)) {
            return true;
        }

        return false;
    }

    private static Method getDeclaredMethod(Object object, String methodName,
            Class<?>... parameterTypes) {
        Method method = null;

        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
                .getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz =
                // clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }

}
