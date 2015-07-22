
package com.xgsdk.client.simulator.view;

import com.xgsdk.client.simulator.util.CommonStr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.PixelFormat;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author ZHOUHAIBING
 */
public class GameFloatView {

    private LinearLayout mFloatLayout;
    private LinearLayout mItemLinearL;
    private int FLWidth;
    private LayoutParams mWmParams;
    private LayoutParams mItemsLWmParams;
    private WindowManager mWindowManager;
    private Boolean mShouldClick = false;// 悬浮窗拖拉的点击事件
    private final float MOVE_THRESHOLD = 10;
    private Button mMenuBtn;
    private Boolean mItemsIsOrder = true;
    private Activity activity;
    private String mUserSession;
    private static GameFloatView sGameFloatView;

    private GameFloatView() {
    }

    public static interface GameFloatListener {
        public void onSwitchAccountClick(Activity activity);

        public void onOpenUserCenterClick();
    }

    private GameFloatListener listener;

    private GameFloatView(Activity activity, GameFloatListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public static GameFloatView getInstance(Activity activity,
            GameFloatListener listener) {

        if (sGameFloatView == null) {
            synchronized (GameFloatView.class) {
                if (sGameFloatView == null) {
                    sGameFloatView = new GameFloatView(activity, listener);
                }
            }
        }
        sGameFloatView.setActivity(activity);// update activity.
        return sGameFloatView;
    }

    public void createFloatWindow() {
        if (mFloatLayout == null) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);

            mWindowManager = activity.getWindowManager();
            mWmParams = createWmParams();
            mItemsLWmParams = createWmParams();
            mFloatLayout = new LinearLayout(activity);
            mMenuBtn = new Button(activity);
            mMenuBtn.setText(CommonStr.MENU);
            mMenuBtn.setBackgroundColor(CommonStr.BLUE);
            mMenuBtn.setTextColor(CommonStr.BLACK);
            TextPaint tp = mMenuBtn.getPaint();
            tp.setFakeBoldText(true);
            mMenuBtn.setOnClickListener(mClickListener);
            mMenuBtn.setOnTouchListener(mTouchListener);

            mFloatLayout.addView(mMenuBtn);
            mFloatLayout.setAlpha(0.5F);
            mWindowManager.addView(mFloatLayout, mWmParams);
            mFloatLayout.measure(w, h);
            FLWidth = mFloatLayout.getMeasuredWidth();
            initItemsLayout();
            mItemLinearL.setAlpha(0.5F);
            mItemsLWmParams.x = mItemsLWmParams.x + FLWidth;
            mWindowManager.addView(mItemLinearL, mItemsLWmParams);
        }
    }

    public LayoutParams createWmParams() {
        LayoutParams mWmParams = new WindowManager.LayoutParams();
        mWmParams.type = LayoutParams.TYPE_APPLICATION_SUB_PANEL;
        mWmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        mWmParams.format = PixelFormat.RGBA_8888;
        mWmParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWmParams.x = 30;
        mWmParams.y = 400;
        mWmParams.width = LayoutParams.WRAP_CONTENT;
        mWmParams.height = LayoutParams.WRAP_CONTENT;
        return mWmParams;
    }

    public void initItemsLayout() {
        mItemLinearL = new LinearLayout(activity);
        Button mUserInfo = new Button(activity);
        mUserInfo.setText(CommonStr.USER_CENTER);
        mUserInfo.setOnClickListener(userCenterListener);
        Button mSwitchAccount = new Button(activity);
        mSwitchAccount.setText(CommonStr.SWITCH_ACCOUNT);
        mUserInfo.setBackgroundColor(CommonStr.GREEN);
        mSwitchAccount.setBackgroundColor(CommonStr.GREEN);
        mUserInfo.setTextColor(CommonStr.BLACK);
        mSwitchAccount.setTextColor(CommonStr.BLACK);
        mSwitchAccount.setOnClickListener(mSwitchAccountListener);
        LinearLayout itemsContainer = new LinearLayout(activity);
        itemsContainer.addView(mUserInfo);
        itemsContainer.addView(mSwitchAccount);
        mItemLinearL.addView(itemsContainer);
        mItemLinearL.setVisibility(TextView.INVISIBLE);
    }

    private View.OnClickListener userCenterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Activity acti = getActivity();
            String[] userInfo = getmUserSession().split(" ");
            AlertDialog.Builder builder = new Builder(acti);
            builder.setTitle("个人中心");
            final LinearLayout userLayout = new LinearLayout(acti);
            LinearLayout.LayoutParams layoutP = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            userLayout.setOrientation(LinearLayout.VERTICAL);
            userLayout.setPadding(15, 10, 15, 0);
            TextView userName = new TextView(acti);
            userName.setText(userInfo[0]);
            TextView balance = new TextView(acti);
            balance.setText("10元宝");
            userLayout.addView(userName);
            userLayout.addView(balance);
            builder.setView(userLayout);
            final AlertDialog userInfoDialog = builder.create();
            builder.setNeutralButton("OK", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    userInfoDialog.dismiss();
                }
            });
            builder.show();
        }
    };

    private View.OnClickListener mSwitchAccountListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // new LoginDialog(activity,mUserCallBack).showLoginDialog();
            if (listener != null) {
                listener.onSwitchAccountClick(getActivity());
            }
        }

    };

    public void reverseItemsLayout(LinearLayout linearLayout) {
        LinearLayout reverseLayout = new LinearLayout(activity);
        ArrayList<View> views = new ArrayList<View>();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            views.add(linearLayout.getChildAt(i));
        }
        linearLayout.removeAllViews();
        for (int j = views.size() - 1; j >= 0; j--) {
            linearLayout.addView(views.get(j));
        }
    }

    private OnTouchListener mTouchListener = new OnTouchListener() {
        int lastX, lastY;
        int paramX, paramY;
        int downX, downY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    paramX = mWmParams.x;
                    paramY = mWmParams.y;
                    mShouldClick = true;
                    break;
                case MotionEvent.ACTION_UP:
                    if (mShouldClick)
                        v.performClick();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if ((Math.abs(downX - event.getX()) > MOVE_THRESHOLD || Math
                            .abs(downY - event.getY()) > MOVE_THRESHOLD)) {
                        mItemLinearL.setVisibility(TextView.GONE);
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        mWmParams.x = paramX + dx;
                        mWmParams.y = paramY + dy;
                        int btnWidth = mMenuBtn.getMeasuredWidth();
                        float screenWidth = getScreenWidth();
                        if (mWmParams.x < 0)
                            mWmParams.x = 0;
                        if (mWmParams.x > screenWidth - btnWidth)
                            mWmParams.x = (int) (screenWidth - btnWidth);
                        // Update suspended window position
                        mWindowManager
                                .updateViewLayout(mFloatLayout, mWmParams);
                        mItemsLWmParams.y = mWmParams.y;
                        mItemsLWmParams.x = mWmParams.x
                                + mFloatLayout.getMeasuredWidth();

                        if (screenWidth - (mWmParams.x + btnWidth) < mItemLinearL
                                .getMeasuredWidth()) {
                            if (mItemsIsOrder == true) {
                                reverseItemsLayout((LinearLayout) mItemLinearL
                                        .getChildAt(0));
                                mItemsIsOrder = false;
                            }
                            mItemsLWmParams.x = mItemsLWmParams.x
                                    - mItemLinearL.getMeasuredWidth() - FLWidth;
                        } else {
                            if (mItemsIsOrder == false) {
                                reverseItemsLayout((LinearLayout) mItemLinearL
                                        .getChildAt(0));
                                mItemsIsOrder = true;
                            }
                        }
                        mWindowManager.updateViewLayout(mItemLinearL,
                                mItemsLWmParams);
                        mShouldClick = false;
                        break;
                    }
            }
            return true;
        }
    };

    public float getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        return dm.widthPixels;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if (mItemLinearL.getVisibility() != TextView.VISIBLE) {
                TranslateAnimation animate = null;
                if (mItemsIsOrder == true)
                    animate = new TranslateAnimation(-mItemLinearL
                            .getChildAt(0).getWidth(), 0, 0, 0);
                else {
                    if (mWmParams.x < mItemLinearL.getMeasuredWidth()) {
                        mWmParams.x = mItemLinearL.getMeasuredWidth();
                        mWindowManager
                                .updateViewLayout(mFloatLayout, mWmParams);
                    }
                    animate = new TranslateAnimation(mItemLinearL.getChildAt(0)
                            .getWidth(), 0, 0, 0);
                }

                animate.setDuration(500);
                animate.setFillAfter(true);
                mItemLinearL.getChildAt(0).startAnimation(animate);

                mItemLinearL.setVisibility(TextView.VISIBLE);

            } else {
                TranslateAnimation animate = null;
                if (mItemsIsOrder == true)
                    animate = new TranslateAnimation(0, -mItemLinearL
                            .getChildAt(0).getWidth(), 0, 0);
                else
                    animate = new TranslateAnimation(0, mItemLinearL
                            .getChildAt(0).getWidth(), 0, 0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                animate.setAnimationListener(mAnimateListener);
                mItemLinearL.getChildAt(0).startAnimation(animate);
            }
        }
    };

    private AnimationListener mAnimateListener = new AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            mItemLinearL.setVisibility(TextView.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

    };

    public void pauseFloatView() {
        if (mFloatLayout != null && mFloatLayout.getWindowToken() != null) {
            mWindowManager.removeView(mFloatLayout);
            // mFloatLayout = null;
        }
        if (mItemLinearL != null && mItemLinearL.getWindowToken() != null) {
            mWindowManager.removeView(mItemLinearL);
            // mItemLinearL = null;
        }
    }

    public void resumeFloatView() {
        if (mFloatLayout != null)
            mWindowManager.addView(mFloatLayout, mWmParams);
        if (mItemLinearL != null)
            mWindowManager.addView(mItemLinearL, mItemsLWmParams);
    }

    public void destroyFloatView() {
        mFloatLayout = null;
        mItemLinearL = null;
        mWindowManager = null;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * @return the mUserSession
     */
    public String getmUserSession() {
        return mUserSession;
    }

    /**
     * @param mUserSession the mUserSession to set
     */
    public void setmUserSession(String mUserSession) {
        this.mUserSession = mUserSession;
    }

}
