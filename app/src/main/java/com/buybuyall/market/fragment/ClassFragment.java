package com.buybuyall.market.fragment;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.GridView;
import android.widget.ListView;

import com.buybuyall.market.R;

/**
 * 描述：分类页面
 * 作者：jake on 2016/1/13 21:14
 */
public class ClassFragment extends StateFragment {
    private static final int MSG_BACK_LOAD_DATA = 0;
    private static final int MSG_UI_LOAD_FAIL = 0;
    private static final int MSG_UI_LOAD_SUCCESS = 1;
    private ListView lvAdv;
    private GridView gvClass;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_class);
        lvAdv = (ListView) findViewById(R.id.lv_adv);
        gvClass = (GridView) findViewById(R.id.gv_class);
        loadData();
    }

    private void loadData() {
        showLoadingView();
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
    }

    @Override
    public void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
        switch (msg.what) {
            case MSG_BACK_LOAD_DATA:
                loadDataTask();
                break;
        }
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_FAIL:
                loadDataTask();
                break;
            case MSG_UI_LOAD_SUCCESS:
                loadDataTask();
                break;
        }
    }

    private void loadDataTask() {
    }

    @Override
    public void reLoadData() {
        loadData();
    }

    public static ClassFragment newInstance() {
        return new ClassFragment();
    }
}
