package com.buybuyall.market.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cn.common.ui.activity.BaseWorkerFragmentActivity;

public class SearchActivity extends StateActivity {

    @Override
    public void reLoadData() {
    }

    @Override
    protected void initView() {
        View view = new View(this);
        setContentView(view);
    }
}
