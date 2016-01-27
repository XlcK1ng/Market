package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.HomeDPAdapter;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.SpecialResponse;
import com.buybuyall.market.widget.ViewCreator;

import cn.common.bitmap.core.ImageLoader;
import cn.common.exception.AppException;
import cn.common.utils.ViewUtil;

/**
 * 描述：
 * 作者：jake on 2016/1/27 22:02
 */
public class SpecialActivity extends StateActivity {
    private static final int MSG_BACK_LOAD_DATA = 0;

    private static final int MSG_UI_LOAD_SUCCESS = 0;

    private static final int MSG_UI_LOAD_FAIL = 1;
    private static final String KEY_SPECIAL_ID = "key_special_id";

    public static void start(Context context, long specialId) {
        if (context != null) {
            Intent it = new Intent(context, SpecialActivity.class);
            it.putExtra(KEY_SPECIAL_ID, specialId);
            context.startActivity(it);
        }
    }

    private ListView listView;
    private ImageView ivBanner;
    private TextView tvDes;
    private View vHeader;
    private HomeDPAdapter mAdapter;
    private long specialId = 0;

    @Override
    protected void dealIntent(Bundle data) {
        super.dealIntent(data);
        if (data != null) {
            specialId = data.getLong(KEY_SPECIAL_ID, 0);
        }
    }

    @Override
    protected void initView() {
        listView = ViewCreator.createCommonListView(this);
        listView.addHeaderView(inflate(R.layout.header_special));
        setTitle("专题");
        setContentView(listView);
        ivBanner = (ImageView) findViewById(R.id.iv_banner);
        tvDes = (TextView) findViewById(R.id.tv_des);
        vHeader = findViewById(R.id.ll_root);
        setNoDataTip("暂无专题数据，去其他页面看看");
        mAdapter = new HomeDPAdapter(this);
        listView.setAdapter(mAdapter);
        reLoadData();
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

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_FAIL:
                showFailView();
                break;
            case MSG_UI_LOAD_SUCCESS:
                handLoadSuccess((SpecialResponse) msg.obj);
                break;
        }
    }

    private void handLoadSuccess(SpecialResponse response) {
        if (response != null) {
            if (response.getList() != null && response.getList().size() > 0) {
                showContentView();
                mAdapter.setDataAndNotifyDataSetChanged(response.getList());
                if (TextUtils.isEmpty(response.getSpecialDescription()) && TextUtils.isEmpty(response.getSpecialName()) && TextUtils.isEmpty(response.getSpecialPic())) {
                    ViewUtil.setViewVisibility(vHeader, View.GONE);
                } else {
                    ViewUtil.setViewVisibility(vHeader, View.VISIBLE);
                }
                if (!TextUtils.isEmpty(response.getSpecialPic())) {
                    ViewUtil.setViewVisibility(ivBanner, View.VISIBLE);
                    ImageLoader.getInstance().displayImage(response.getSpecialPic(), ivBanner);
                } else {
                    ViewUtil.setViewVisibility(ivBanner, View.GONE);
                }
                if (!TextUtils.isEmpty(response.getSpecialDescription())) {
                    ViewUtil.setViewVisibility(tvDes, View.VISIBLE);
                    ViewUtil.setText2TextView(tvDes, response.getSpecialDescription());
                } else {
                    ViewUtil.setViewVisibility(tvDes, View.GONE);
                }
                if (!TextUtils.isEmpty(response.getSpecialName())) {
                    setTitle(response.getSpecialName());
                }

            } else {
                showNoDataView();
            }
        }
    }

    private void loadDataTask() {
        HttpRequest<SpecialResponse> request = new HttpRequest<>(UrlManager.SPECIAL, SpecialResponse.class);
        request.setIsGet(true);
        request.addParam("special_id", "" + specialId);
        try {
            SpecialResponse response = request.request();
            if (response != null && response.isOk()) {
                Message message = obtainUiMessage();
                message.what = MSG_UI_LOAD_SUCCESS;
                message.obj = response;
                message.sendToTarget();
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
        }
    }
}
