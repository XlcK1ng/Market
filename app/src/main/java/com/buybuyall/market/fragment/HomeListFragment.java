
package com.buybuyall.market.fragment;

import android.os.Message;
import android.widget.ListView;

import com.buybuyall.market.adapter.HomeDPAdapter;
import com.buybuyall.market.adapter.HomeJPAdapter;
import com.buybuyall.market.adapter.HomeJYAdapter;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.GoodsListResponse;
import com.buybuyall.market.logic.http.response.JPGoodsListResponse;
import com.buybuyall.market.logic.http.response.PackageListResponse;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.exception.AppException;
import cn.common.utils.CommonUtil;
import cn.common.utils.DisplayUtil;

/**
 * 描述：首页物品列表 作者：jake on 2016/1/21 22:58
 */
public class HomeListFragment extends StateFragment {
    private static final int MSG_BACK_LOAD_DATA = 0;

    private static final int MSG_UI_LOAD_SUCCESS = 0;

    private static final int MSG_UI_LOAD_FAIL = 1;

    private static final int MSG_UI_LOAD_NO_DATA = 2;

    private ListView listView;

    private HomeJPAdapter mJPAdapter;

    private HomeDPAdapter mDPAdapter;

    private HomeJYAdapter mJYAdapter;

    private int type = TYPE_JP;

    public static final int TYPE_JP = 0;

    public static final int TYPE_JY = 1;

    public static final int TYPE_DP = 2;

    public void loadData(int type) {
        this.type = type;
        reLoadData();
    }

    @Override
    protected void initView() {
        listView = ViewCreator.createInnerListView(getActivity());
        setContentView(listView);
        mStatusView.setMinimumHeight(DisplayUtil.dip(524));
        mJPAdapter = new HomeJPAdapter(getActivity());
        mDPAdapter = new HomeDPAdapter(getActivity());
        mJYAdapter = new HomeJYAdapter(getActivity());
        listView.setAdapter(mJPAdapter);
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
        switch (type) {
            case TYPE_JP:
                loadJPTask();
                break;
            case TYPE_JY:
                loadJYTask();
                break;
            case TYPE_DP:
                loadDPTask();
                break;
        }
    }

    public void setJPData(ArrayList<GoodsInfo> list) {
        if (CommonUtil.isListAvailable(list)) {
            mJPAdapter.setData(list);
            sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
        } else {
            sendEmptyUiMessage(MSG_UI_LOAD_NO_DATA);
        }
    }

    private void loadJPTask() {
        HttpRequest<JPGoodsListResponse> request = new HttpRequest<>(UrlManager.HOME_JP,
                JPGoodsListResponse.class);
        request.setIsGet(true);
        try {
            JPGoodsListResponse response = request.request();
            if (response != null && response.isOk()) {
                if (response.getList() != null && response.getList().size() > 0) {
                    mJPAdapter.setData(response.getList());
                    sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
                } else {
                    sendEmptyUiMessage(MSG_UI_LOAD_NO_DATA);
                }
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
        }
    }

    private void loadJYTask() {
        HttpRequest<PackageListResponse> request = new HttpRequest<>(UrlManager.HOME_JY,
                PackageListResponse.class);
        request.setIsGet(true);
        try {
            PackageListResponse response = request.request();
            if (response != null && response.isOk()) {
                if (response.getList() != null && response.getList().size() > 0) {
                    mJYAdapter.setData(response.getList());
                    sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
                } else {
                    sendEmptyUiMessage(MSG_UI_LOAD_NO_DATA);
                }
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
        }

    }

    private void loadDPTask() {
        HttpRequest<GoodsListResponse> request = new HttpRequest<>(UrlManager.HOME_DP,
                GoodsListResponse.class);
        request.setIsGet(true);
        try {
            GoodsListResponse response = request.request();
            if (response != null && response.isOk()) {
                if (response.getList() != null && response.getList().size() > 0) {
                    mDPAdapter.setData(response.getList());
                    sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
                } else {
                    sendEmptyUiMessage(MSG_UI_LOAD_NO_DATA);
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
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_FAIL:
                showFailView();
                break;
            case MSG_UI_LOAD_SUCCESS:
                showContent();
                break;
            case MSG_UI_LOAD_NO_DATA:
                showNoDataView();
                break;
        }
    }

    private void showContent() {
        showContentView();
        switch (type) {
            case TYPE_JP:
                listView.setAdapter(mJPAdapter);
                break;
            case TYPE_JY:
                listView.setAdapter(mJYAdapter);
                break;
            case TYPE_DP:
                listView.setAdapter(mDPAdapter);
                break;
        }

    }
}
