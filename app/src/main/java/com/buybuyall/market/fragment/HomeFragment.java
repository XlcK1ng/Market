package com.buybuyall.market.fragment;

import android.graphics.Color;
import android.view.View;

import cn.common.ui.activity.BaseWorkerFragmentActivity;
import cn.common.ui.fragment.BaseWorkerFragment;

/**
 * Created by Administrator on 2015/11/22.
 */
public class HomeFragment extends BaseWorkerFragment {
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initView() {
        View view = new View(getActivity());
        view.setBackgroundColor(Color.GRAY);
        setContentView(view);
    }
}
