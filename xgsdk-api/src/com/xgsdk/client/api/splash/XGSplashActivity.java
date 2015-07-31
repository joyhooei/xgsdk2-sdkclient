
package com.xgsdk.client.api.splash;

import com.xgsdk.client.core.ProductInfo;
import com.xgsdk.client.core.utils.XGLog;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class XGSplashActivity extends Activity {

    private static final String GAME_MAIN_ACTION = "xg.game.MAIN";
    private static final int MSG_SPLASH_REPLACE = 0;
    private static final int MSG_SPLASH_START_GAME = 1;
    private static final int MSG_SPLASH_FINISH = 2;
    private static final int DURATION_TIME_MS = 1500;
    private static final int MAX_IMAGE_COUNT = 5;
    private static final String IMG_PREFIX = "xg_splash_landscape_";
    private static final String PORTRAIT_IMG_PREFIX = "xg_splash_portrait_";

    private String mCurrentPrefix = IMG_PREFIX;

    private int mIndex;
    private boolean mIsPortrait = false;

    private Animation mAnimation1, mAnimation2;

    private ImageView mIVSplashPic;
    private LinearLayout mLLSplash;

    private Resources mRes;
    private ArrayList<Integer> mIds = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {// 解决小米手机，手动安装后第一次启动，按home切入后台，点桌面图标，再进游戏导致黑屏问题。（OPENGL实例在同一个TaskStack中只能存在一个）
            finish();
            return;
        }

        XGLog.i("xg version:" + ProductInfo.getXGVersion(this));
        XGLog.i("XGSplashActivity onCreate start");
        mRes = getResources();

        int o = mRes.getConfiguration().orientation;
        mIsPortrait = !ProductInfo.isLandspcape(this);
        if (mIsPortrait) {
            mCurrentPrefix = PORTRAIT_IMG_PREFIX;
        }
        mIndex = 0;
        mIds.clear();
        for (int i = 0; i < MAX_IMAGE_COUNT; i++) {
            int id = mRes.getIdentifier(mCurrentPrefix + i, "drawable",
                    getPackageName());
            if (id <= 0) {
                break;
            }
            mIds.add(id);
            XGLog.i("XGSplashActivity -" + mCurrentPrefix + i + ",id=" + id);
        }
        XGLog.i("XGSplashActivity onCreate start");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startGame() {
        XGLog.i("splash after start game=====");
        Intent intent = new Intent();
        intent.setAction(GAME_MAIN_ACTION);
        intent.setPackage(getPackageName());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        XGLog.i("splash after start game end=====");
    }

    protected boolean onSplashEnd() {
        return true;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SPLASH_START_GAME:
                    if (onSplashEnd()) {
                        startGame();
                    }
                    finish();
                    break;
                case MSG_SPLASH_REPLACE:
                    break;
                case MSG_SPLASH_FINISH:
                    mLLSplash.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        XGLog.i("XGSplashActivity onStart start");
        mLLSplash = new LinearLayout(getApplicationContext());
        mLLSplash.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        mLLSplash.setOrientation(LinearLayout.HORIZONTAL);
        setContentView(mLLSplash);

        mIVSplashPic = new ImageView(getApplicationContext());
        if (mIds.isEmpty()) {
            mHandler.sendEmptyMessage(MSG_SPLASH_START_GAME);
            return;
        } else {
            mIVSplashPic.setBackgroundResource(mIds.get(0));
        }
        mIVSplashPic.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mLLSplash.removeAllViews();
        mLLSplash.addView(mIVSplashPic);

        mAnimation1 = new AlphaAnimation(0.1f, 1.0f);
        mAnimation1.setDuration(DURATION_TIME_MS);
        mAnimation2 = new AlphaAnimation(1.0f, 0.1f);
        mAnimation2.setDuration(DURATION_TIME_MS);
        mIVSplashPic.startAnimation(mAnimation1);

        mAnimation1.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIndex++;
                if (mIndex < mIds.size()) {
                    mIVSplashPic.startAnimation(mAnimation2);
                } else {
                    mHandler.sendEmptyMessage(MSG_SPLASH_START_GAME);
                }
            }
        });
        mAnimation2.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    int resId = mIds.get(mIndex);
                    if (resId > 0) {
                        mIVSplashPic.setBackgroundResource(resId);
                        mIVSplashPic.startAnimation(mAnimation1);
                    }
                } catch (Exception e) {
                    XGLog.e("onAnimationEnd error " + mCurrentPrefix + (mIndex),
                            e);
                }
            }
        });
        XGLog.i("XGSplashActivity onStart end");
    }

}
