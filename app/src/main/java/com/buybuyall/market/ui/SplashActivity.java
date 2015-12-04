package com.buybuyall.market.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.buybuyall.market.logic.InitSharedData;

import cn.common.ui.activity.BaseWorkerFragmentActivity;

public class SplashActivity extends BaseWorkerFragmentActivity {
    private ImageView mIvSplash;

    /**
     * 延时进入页面时间
     */
    private static final long DELAYED_TIME = 1 * 1000 - 500;

    /**
     * 进入引导页
     */
    private static final int MSG_GUIDE = 0;

    /**
     * 进入主页面
     */
    private static final int MSG_MAIN = 1;

    private static final int MSG_BACK_AUTO_LOGIN = 0;

    private static final int MSG_UI_AUTO_LOGIN = 2;

    /**
     * 进入登录页面
     */
    private static final int MSG_LOGIN = 2;

    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIvSplash = new ImageView(this);
//        mIvSplash.setImageResource(R.drawable.loading_page_ishoe);
        mIvSplash.setScaleType(ImageView.ScaleType.FIT_XY);
        mIvSplash.setBackgroundColor(Color.BLUE);
        setContentView(mIvSplash);
        sendEmptyUiMessage(MSG_MAIN);
//        if (InitSharedData.hasLogin() && !TextUtils.isEmpty(InitSharedData.getDeviceData())) {
//            lastTime = System.currentTimeMillis();
//            sendEmptyBackgroundMessage(MSG_BACK_AUTO_LOGIN);
//        } else {
//            sendEmptyUiMessageDelayed(MSG_LOGIN, DELAYED_TIME);
//        }
    }

    @Override
    public void handleUiMessage(Message msg) {
        switch (msg.what) {
            case MSG_GUIDE:
                break;
            case MSG_LOGIN:
                break;
            case MSG_MAIN:
                goActivity(MainActivity.class);
                break;
        }
        finish();
    }

    @Override
    public void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
        switch (msg.what) {
            case MSG_BACK_AUTO_LOGIN:

                break;
        }
    }
}
