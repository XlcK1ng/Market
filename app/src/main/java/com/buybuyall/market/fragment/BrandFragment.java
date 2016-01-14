package com.buybuyall.market.fragment;

import android.os.Message;
import android.widget.ListView;

import com.buybuyall.market.adapter.BrandAdapter;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.BrandListResponse;
import com.buybuyall.market.widget.ViewCreator;

import cn.common.exception.AppException;

/**
 * 描述：品牌页面
 * 作者：jake on 2016/1/13 21:14
 */
public class BrandFragment extends StateFragment {
    private static final int MSG_BACK_LOAD_DATA = 0;
    private static final int MSG_UI_LOAD_FAIL = 0;
    private static final int MSG_UI_LOAD_SUCCESS = 1;
    private static final int MSG_UI_NO_DATA = 3;
    private ListView lvBrand;
    private BrandAdapter adapter;

    @Override
    protected void initView() {
        lvBrand = ViewCreator.createCommonListView(getActivity());
        setContentView(lvBrand);
        adapter = new BrandAdapter(getActivity());
        lvBrand.setAdapter(adapter);
        setNoDataTip("暂无品牌相关的信息");
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
            case MSG_UI_NO_DATA:
                showNoDataView();
                break;
            case MSG_UI_LOAD_FAIL:
                showFailView();
                break;
            case MSG_UI_LOAD_SUCCESS:
                showContentView();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void loadDataTask() {
        HttpRequest<BrandListResponse> request = new HttpRequest<>(UrlManager.GET_BRAND_LIST, BrandListResponse.class);
        request.setIsGet(true);
        try {
            BrandListResponse response = request.request();
            if (response != null && response.isOk()) {
                if (response.getList() != null && response.getList().size() > 0) {
                    adapter.setData(response.getList());
                    sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
                } else {
                    sendEmptyUiMessage(MSG_UI_NO_DATA);
                }
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
        }
    }

    @Override
    public void reLoadData() {
        loadData();
    }

    public static BrandFragment newInstance() {
        return new BrandFragment();
    }
}
