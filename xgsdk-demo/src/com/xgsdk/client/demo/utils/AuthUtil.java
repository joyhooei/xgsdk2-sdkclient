
package com.xgsdk.client.demo;

import com.xgsdk.client.core.service.AuthService;
import com.xgsdk.client.demo.utils.ToastUtil;

import android.app.Activity;

public class TestUtil {
    private static final String TAG = "TestUtil";

    public static void auth(Activity activity, String version, String authInfo) {

        try {
            String result = AuthService.sessionAuthInThread(activity, authInfo);
            ToastUtil.showToast(activity, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private static void sendRequestWithHttpClient(final Activity activity,
    // final String url) {
    // new Thread(new Runnable() {
    //
    // @Override
    // public void run() {
    // HttpClient httpCient = new DefaultHttpClient();
    // HttpGet httpGet = new HttpGet(url);
    // Log.d(TAG, url);
    //
    // try {
    // HttpResponse httpResponse = httpCient.execute(httpGet);
    // if (httpResponse.getStatusLine().getStatusCode() == 200) {
    // HttpEntity entity = httpResponse.getEntity();
    // final String response = EntityUtils.toString(entity,
    // "utf-8");
    //
    // Log.w(TAG, "auth = " + response);
    // activity.runOnUiThread(new Runnable() {
    //
    // @Override
    // public void run() {
    // ToastUtil.showToast(activity, response);
    //
    // }
    // });
    // }
    //
    // } catch (Exception e) {
    // Log.e(TAG, "auth error : ", e);
    // }
    //
    // }
    // }).start();

    // }
}
