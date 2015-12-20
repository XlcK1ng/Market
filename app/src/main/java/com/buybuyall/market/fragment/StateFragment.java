package com.buybuyall.market.fragment;

import android.view.View;

import com.buybuyall.market.widget.StatusView;

import cn.common.ui.fragment.BaseWorkerFragment;

public abstract class StateFragment extends BaseWorkerFragment implements StatusView.StatusListener {
    protected StatusView mStatusView;

    @Override
    public void setContentView(View view) {
        if (view != null) {
            mStatusView = new StatusView(getActivity());
            mStatusView.setStatusListener(this);
            mStatusView.setContentView(view);
            super.setContentView(mStatusView);
        }
    }

    public void showLoadingView() {
        if (mStatusView != null) {
            mStatusView.showLoadingView();
        }
    }

    public void showFailView() {
        if (mStatusView != null) {
            mStatusView.showFailView();
        }
    }

    public void showNoDataView() {
        if (mStatusView != null) {
            mStatusView.showNoDataView();
        }
    }

    public void setNoDataTip(int string) {
        if (mStatusView != null) {
            mStatusView.setNoDataTip(getString(string));
        }
    }

    public void setNoDataTip(CharSequence cs) {
        if (mStatusView != null) {
            mStatusView.setNoDataTip(cs);
        }
    }

}
