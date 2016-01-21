package com.buybuyall.market.fragment;

import android.os.Message;
import android.widget.ListView;

import com.buybuyall.market.adapter.HomeGoodsAdapter;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

/**
 * 描述：首页物品列表
 * 作者：jake on 2016/1/21 22:58
 */
public class HomeListFragment extends StateFragment {
    private static final int MSG_BACK_LOAD_DATA = 0;
    private static final int MSG_UI_LOAD_SUCCESS = 0;
    private static final int MSG_UI_LOAD_FAIL = 1;
    private ListView listView;
    private HomeGoodsAdapter mGoodsAdapter;
    private String key;

    public void setKey(String key) {
        this.key = key;
        reLoadData();
    }

    @Override
    protected void initView() {
        listView = ViewCreator.createInnerListView(getActivity());
        setContentView(listView);
        mGoodsAdapter = new HomeGoodsAdapter(getActivity());
        listView.setAdapter(mGoodsAdapter);
    }

    public void setData(ArrayList<GoodsInfo> list) {
        mGoodsAdapter.setData(list);
    }

    public void notifyDataSetChanged() {
        mGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void reLoadData() {
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

    private void loadDataTask() {
        ArrayList<GoodsInfo> goodsInfos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            goodsInfos.add(new GoodsInfo());
        }
        mGoodsAdapter.setData(goodsInfos);
        sendEmptyUiMessageDelayed(MSG_UI_LOAD_SUCCESS, 1000);
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_FAIL:
                showFailView();
                break;
            case MSG_UI_LOAD_SUCCESS:
                showContentView();
                if (mGoodsAdapter != null) {
                    mGoodsAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
