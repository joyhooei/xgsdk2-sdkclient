
package com.xgsdk.client.simulator.view;

import com.xgsdk.client.callback.ExitCallBack;
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

public class ExitDialog {

    public ExitDialog() {

    }

    public void showDialog(Activity activity, final ExitCallBack exitCallBack,
            String customParams) {
        AlertDialog.Builder builder = new Builder(activity);
        TextView title = new TextView(activity);
        title.setText(CommonStr.CLICKEXITORBACK);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setTextSize(22);
        builder.setCustomTitle(title);
        final AlertDialog exitDialog = builder.create();

        builder.setView(exitInfo(activity));
        builder.setPositiveButton(CommonStr.EXIT_CANCEL, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                exitCallBack.onCancel();
            }

        });
        builder.setNeutralButton(CommonStr.EXIT_USEGAMER,
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        exitCallBack.onNoChannelExiter();

                    }

                });
        builder.setNegativeButton(CommonStr.EXIT_IMMEDIATE,
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        exitCallBack.onExit();
                    }

                });
        builder.show();
    }

    public ScrollView exitInfo(Activity activity) {
        ScrollView sv = new ScrollView(activity);
        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(550, 200);
        sv.setPadding(15, 10, 15, 20);
        sv.setLayoutParams(params);
        TextView title = new TextView(activity);
        title.setText("操作说明");
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.addView(title);
        TextView exitInfo = new TextView(activity);
        exitInfo.setText("使用游戏方退出：跳转到游戏方的退出框\n" + "直接退出：直接退出游戏，不弹出游戏方的退出框\n"
                + "取消退出：取消退出");
        exitInfo.setPadding(50, 0, 0, 0);
        ll.addView(exitInfo);
        sv.addView(ll);

        return sv;
    }

}
