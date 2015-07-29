
package com.xgsdk.client.simulator.view;

import com.xgsdk.client.simulator.util.CommonStr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class UserCenter {

    private static UserCenter userCenter;

    private UserCenter() {
    }

    public static UserCenter getInstance() {
        if (userCenter == null) {
            synchronized (UserCenter.class) {
                if (userCenter == null)
                    userCenter = new UserCenter();
            }
        }
        return userCenter;
    }

    public void showDialog(Activity activity, String userSession) {
        AlertDialog.Builder builder = new Builder(activity);
        TextView title = new TextView(activity);
        title.setText(CommonStr.USER_CENTER);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setTextSize(22);
        builder.setCustomTitle(title);

        LinearLayout body = new LinearLayout(activity);
        body.setOrientation(LinearLayout.VERTICAL);
        LayoutParams bodyParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        body.setLayoutParams(bodyParams);
        body.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView textTitle = new TextView(activity);
        textTitle.setText("个人信息");
        textTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        body.addView(textTitle);

        body.addView(usersInfo(activity, userSession));
        builder.setView(body);
        final AlertDialog infoDialog = builder.create();
        builder.setPositiveButton("确认", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                infoDialog.dismiss();
            }
        });
        builder.show();
    }

    public ScrollView usersInfo(Activity activity, String userSession) {
        String[] infos = userSession.split("\\s+");
        ScrollView sv = new ScrollView(activity);
        LayoutParams params = new LayoutParams(400, 100);
        sv.setLayoutParams(params);

        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < infos.length; i++) {
            TextView tt = new TextView(activity);
            tt.setText(infos[i]);
            ll.addView(tt);
        }
        TextView balance = new TextView(activity);
        balance.setText("10元宝");
        ll.addView(balance);

        TextView games = new TextView(activity);
        games.setText("在玩的游戏数量：4");
        ll.addView(games);
        sv.addView(ll);
        return sv;
    }

}
