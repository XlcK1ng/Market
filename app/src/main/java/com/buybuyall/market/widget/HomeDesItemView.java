
package com.buybuyall.market.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.buybuyall.market.R;

import cn.common.utils.DisplayUtil;

/**
 * 描述:
 *
 * @author jakechen
 * @since 2016/1/23 15:17
 */
public class HomeDesItemView extends FrameLayout {
    private boolean isShow = false;

    private View vContent;

    private TextView tvContent;

    public HomeDesItemView(Context context) {
        this(context, null);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public HomeDesItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_des_item, this);
        ((TextView) findViewById(R.id.tv_left)).setText("<");
        vContent = findViewById(R.id.fl_content);
        tvContent = (TextView) findViewById(R.id.tv_content);
        LayoutParams params = (LayoutParams) vContent.getLayoutParams();
        params.rightMargin = -DisplayUtil.dip(240);
        vContent.setLayoutParams(params);
        vContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    hideContent();
                } else {
                    showContent();
                }
            }
        });
        tvContent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void hideContent() {
        isShow = false;
        TranslateAnimation animation = new TranslateAnimation(0, DisplayUtil.dip(240), 0, 0);
        animation.setDuration(800);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LayoutParams params = (LayoutParams) vContent.getLayoutParams();
                params.rightMargin = -DisplayUtil.dip(240);
                vContent.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        vContent.startAnimation(animation);
    }

    private void showContent() {
        isShow = true;
        TranslateAnimation animation = new TranslateAnimation(DisplayUtil.dip(240), 0, 0, 0);
        animation.setDuration(800);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LayoutParams params = (LayoutParams) vContent.getLayoutParams();
                params.rightMargin = 0;
                vContent.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        vContent.startAnimation(animation);
    }
}
