
package com.xgsdk.client.simulator.view;

import android.content.Context;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginLayout extends LinearLayout {
    public EditText mETUsername;
    public EditText mETPassword;

    public LoginLayout(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.removeAllViews();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        mETUsername = new EditText(context);
        mETUsername.setLayoutParams(params);
        mETUsername.setHint("username");
        mETUsername.setMaxEms(30);
        this.addView(mETUsername);

        mETPassword = new EditText(context);
        mETPassword.setLayoutParams(params);
        mETPassword.setHint("password");
        mETPassword.setMaxEms(16);
        mETPassword.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        this.addView(mETPassword);
    }

}
