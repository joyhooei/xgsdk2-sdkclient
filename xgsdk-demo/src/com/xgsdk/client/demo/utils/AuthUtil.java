
package com.xgsdk.client.demo.utils;

import com.xgsdk.client.api.entity.XGUser;
import com.xgsdk.client.core.service.AuthService;
import com.xgsdk.client.core.service.Result;
import com.xgsdk.client.demo.GameInfo;

import org.json.JSONObject;

import android.app.Activity;

public class AuthUtil {
    private static final String TAG = "TestUtil";

    public static XGUser auth(Activity activity, String authInfo) {
        XGUser user = new XGUser();
        try {
            Result result = AuthService.sessionAuthInThread(activity, authInfo);
            if (result != null) {
                JSONObject jsonData = new JSONObject(result.getData());
                String uid = jsonData.optString("uId");
                GameInfo.getInstance().setUid(uid);
                user.setAuthInfo(authInfo);
                user.setUid(uid);
                ToastUtil.showToast(activity, result.getData());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
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
