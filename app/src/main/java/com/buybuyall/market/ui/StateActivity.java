
package com.buybuyall.market.ui;

import android.view.View;

import com.buybuyall.market.widget.StatusView;

import cn.common.ui.activity.BaseWorkerFragmentActivity;
import cn.common.ui.fragment.BaseWorkerFragment;

public abstract class StateActivity extends CommonTitleActivity implements
        StatusView.StatusListener {
    protected StatusView mStatusView;

    @Override
    public void setContentView(View view) {
        if (view != null) {
            mStatusView = new StatusView(this);
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

    public void showContentView() {
        if (mStatusView != null) {
            mStatusView.showContentView();
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
