package com.buybuyall.market.fragment;

import android.os.Message;
import android.widget.GridView;
import android.widget.ListView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.ClassAdapter;
import com.buybuyall.market.adapter.ClassTopAdapter;
import com.buybuyall.market.entity.ClassInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.BrandListResponse;
import com.buybuyall.market.logic.http.response.ClassListResponse;

import java.util.ArrayList;

import cn.common.exception.AppException;

/**
 * 描述：分类页面
 * 作者：jake on 2016/1/13 21:14
 */
public class ClassFragment extends StateFragment {
    private static final int MSG_BACK_LOAD_DATA = 0;
    private static final int MSG_UI_LOAD_FAIL = 0;
    private static final int MSG_UI_LOAD_SUCCESS = 1;
    private static final int MSG_UI_NO_DATA = 3;
    private ListView lvAdv;
    private GridView gvClass;
    private ClassTopAdapter topAdapter;
    private ClassAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_class);
        lvAdv = (ListView) findViewById(R.id.lv_adv);
        gvClass = (GridView) findViewById(R.id.gv_class);
        topAdapter = new ClassTopAdapter(getActivity());
        adapter = new ClassAdapter(getActivity());
        lvAdv.setAdapter(topAdapter);
        gvClass.setAdapter(adapter);
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
                if (topAdapter != null) {
                    topAdapter.notifyDataSetChanged();
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void loadDataTask() {
        HttpRequest<ClassListResponse> request = new HttpRequest<>(UrlManager.GET_CLASS_LIST, ClassListResponse.class);
        request.setIsGet(true);
        try {
            ClassListResponse response = request.request();
            if (response != null && response.isOk()) {
                if (response.getList() != null && response.getList().size() > 0) {
                    ArrayList<ClassInfo> list = response.getList();
                    if (list.size() >= 2) {
                        topAdapter.setData(list.subList(0, 2));
                        adapter.setData(list.subList(2, list.size()));
                    } else {
                        adapter.setData(list);
                    }
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

    public static ClassFragment newInstance() {
        return new ClassFragment();
    }
}
