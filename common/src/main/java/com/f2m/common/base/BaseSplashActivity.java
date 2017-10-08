package com.f2m.common.base;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.f2m.common.R;
import com.f2m.common.utils.CommonUltil;

import static com.f2m.common.utils.Constants.SPLASH_TIME;

/**
 * Created by sev_user on 9/25/2017.
 */

public abstract class BaseSplashActivity extends Activity {

    private static final String TAG = CommonUltil.buildTag(BaseSplashActivity.class);

    private Class mActivityClassAfterSplash = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CountDownTimer(SPLASH_TIME, 1) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {
                Log.d(TAG, "mSplashCountTimer finish");
                if (mActivityClassAfterSplash != null) {
                    new Handler().postDelayed(new Runnable() {

                        @Override

                        public void run() {
                            Intent iApp = new Intent(getApplicationContext(), mActivityClassAfterSplash);
                            startActivity(iApp);
                            overridePendingTransition(R.anim.slide_in_right, 0);
                            finish();
                        }

                    }, 250);
                }
            }
        }.start();
    }

    protected void setActivityClassAfterSplash(Class activity) {
        mActivityClassAfterSplash = activity;
    }
}
