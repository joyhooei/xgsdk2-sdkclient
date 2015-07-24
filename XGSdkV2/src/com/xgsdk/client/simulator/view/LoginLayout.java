
package com.xgsdk.client.simulator.view;

import com.xgsdk.client.simulator.util.CommonStr;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginLayout extends LinearLayout {
    public EditText mETUsername;
    public EditText mETPassword;
    public LinearLayout bodyLayout;

    public LoginLayout(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.removeAllViews();
        this.addView(fillBodyLayout(context));
    }

    public LinearLayout fillBodyLayout(Context context) {
        bodyLayout = new LinearLayout(context);
        bodyLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        bodyLayout.setLayoutParams(params);
        bodyLayout.setPadding(15, 10, 15, 0);
        bodyLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView smallNote = new TextView(context);
        smallNote.setText("注：该登录是测试使用，任意用户名和密码都可以");
        smallNote.setTextColor(CommonStr.RED);
        LayoutParams noteParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        smallNote.setLayoutParams(noteParams);
        // noteParams.setMargins(0, 0, 0, 10);
        bodyLayout.addView(smallNote);

        LayoutParams editParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        TextView textUsername = new TextView(context);
        textUsername.setText("用户名:");
        textUsername.setWidth(160);
        textUsername.setGravity(Gravity.RIGHT);
        mETUsername = new EditText(context);
        mETUsername.setLayoutParams(editParams);
        mETUsername.setHint("username");
        mETUsername.setMaxEms(30);
        LinearLayout usernameLl = new LinearLayout(context);
        usernameLl.setOrientation(LinearLayout.HORIZONTAL);
        usernameLl.addView(textUsername);
        usernameLl.addView(mETUsername);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);

        TextView textPassword = new TextView(context);
        textPassword.setText("密码:");
        textUsername.measure(w, h);
        Log.i("username text width",
                String.valueOf(textUsername.getMeasuredWidth()));
        textPassword.setMinWidth(textUsername.getMeasuredWidth());
        textPassword.setGravity(Gravity.RIGHT);
        mETPassword = new EditText(context);
        mETPassword.setLayoutParams(editParams);
        mETPassword.setHint("password");
        mETPassword.setMaxEms(16);
        LinearLayout passwordLl = new LinearLayout(context);
        passwordLl.setOrientation(LinearLayout.HORIZONTAL);
        passwordLl.addView(textPassword);
        passwordLl.addView(mETPassword);

        bodyLayout.addView(usernameLl);
        bodyLayout.addView(passwordLl);

        // bodyLayout.addView(child);
        return bodyLayout;

    }

}
