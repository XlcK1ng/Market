package com.buybuyall.market.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import cn.common.ui.activity.BaseWorkerFragmentActivity;
import cn.common.ui.fragment.BaseWorkerFragment;

public class HomeFragment extends StateFragment {
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initView() {
        View view = new View(getActivity());
        view.setBackgroundColor(Color.GRAY);
        setContentView(view);
        showLoadingView();
    }

    @Override
    public void reLoadData() {

    }
}
