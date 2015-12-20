package com.buybuyall.market.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.buybuyall.market.R;

import cn.common.ui.activity.BaseWorkerFragmentActivity;
import cn.common.ui.widgt.ChangeThemeUtils;

public abstract class CommonTitleActivity extends BaseWorkerFragmentActivity {
    protected ImageButton ibBack;
    protected TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    protected void initEvent() {
    }

    protected void initData() {
    }

    protected abstract void initView();

    @Override
    public void setContentView(View view) {
        if (view != null) {
            super.setContentView(R.layout.activity_common_title);
            FrameLayout layout = (FrameLayout) findViewById(R.id.root_content);
            if (layout.getChildCount() > 0) {
                layout.removeAllViews();
            }
            layout.addView(view, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            initTitleBar();
        }
    }

    private void initTitleBar() {
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ChangeThemeUtils.adjustStatusBar(findViewById(R.id.iv_status_bar), this);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
                finish();
            }
        });
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    protected void onBack() {
    }

}
