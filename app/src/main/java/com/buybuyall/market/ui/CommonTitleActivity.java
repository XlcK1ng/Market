package com.buybuyall.market.ui;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.buybuyall.market.R;

import cn.common.ui.activity.BaseTitleActivity;
import cn.common.ui.widgt.ChangeThemeUtils;

public abstract class CommonTitleActivity extends BaseTitleActivity {
    protected ImageButton ibBack;
    protected TextView tvTitle;


    @Override
    protected View getTitleLayoutView() {
        View title = inflate(R.layout.view_common_title);
        initTitleBar(title);
        setBackgroundColor(getColor(R.color.background_gray));
        return title;
    }


    private void initTitleBar(View title) {
        ibBack = (ImageButton) title.findViewById(R.id.ib_back);
        tvTitle = (TextView) title.findViewById(R.id.tv_title);
        ChangeThemeUtils.setStatusBarTextColor(this);
        ChangeThemeUtils.adjustStatusBar(title.findViewById(R.id.iv_status_bar), this);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
                finish();
            }
        });
    }


    @Override
    public void setTitle(CharSequence title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    public void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    public void setTitle(int title) {
        if (tvTitle != null) {
            tvTitle.setText(getString(title));
        }
    }

    protected void onBack() {
    }

}
