package com.buybuyall.market.fragment;

import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.HomeGoodsAdapter;
import com.buybuyall.market.adapter.PartyAdapter;
import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.AdvListResponse;
import com.buybuyall.market.logic.http.response.PartyListResponse;
import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.bitmap.core.ImageLoader;
import cn.common.exception.AppException;
import cn.common.ui.widgt.banner.BannerView;
import cn.common.utils.DisplayUtil;

/**
 * 描述：拼团页面
 * 作者：jake on 2016/1/3 13:36
 */
public class PartyFragment extends StateFragment {
    public static PartyFragment newInstance() {
        return new PartyFragment();
    }

    private static final int MSG_UI_LOAD_DATA_SUCCESS = 0;
    private static final int MSG_UI_LOAD_DATA_FAIL = 1;
    private static final int MSG_UI_LOAD_ADV_SUCCESS = 2;
    private static final int MSG_UI_LOAD_ADV_FAIL = 3;
    private static final int MSG_BACK_LOAD_DATA = 0;
    private ListView lvContent;
    private PartyAdapter mPartyAdapter;
    private BannerView bannerView;

    @Override
    protected void initView() {
        lvContent = ViewCreator.createCommonListView(getActivity());
        lvContent.addHeaderView(inflate(R.layout.view_header_group));
        setContentView(lvContent);
        bannerView = (BannerView) findViewById(R.id.banner_view);
        bannerView.setStyle(BannerView.STYLE_DOT_LEFT);
        bannerView.getDotView().setNormalColor(getColor(R.color.gray_dfdfdf));
        bannerView.getDotView().setSelectColor(getColor(R.color.orange_fb6e52));
        bannerView.setBannerListener(new BannerView.IListener() {
            @Override
            public void itemClick(Object banner) {
                //do nothing
            }

            @Override
            public void loadImage(Object banner, ImageView ivBanner) {
                AdvInfo advInfo = (AdvInfo) banner;
                ImageLoader.getInstance().displayImage(advInfo.getAdvPic(), ivBanner);
            }
        });
    }

    private boolean isFirstIn = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstIn) {
            isFirstIn = false;
            sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
        }
    }

    @Override
    protected void initData() {
        mPartyAdapter = new PartyAdapter(getActivity());
        lvContent.setAdapter(mPartyAdapter);
        showLoadingView();
    }

    @Override
    public void reLoadData() {
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
        HttpRequest<AdvListResponse> reqAdv = new HttpRequest<>(UrlManager.GET_ADV, AdvListResponse.class);
        reqAdv.setIsGet(true);
        reqAdv.addParam("key", "6f16dea73b014286b4b71065d3897d84");
        try {
            AdvListResponse response = reqAdv.request();
            if (response != null && response.getList() != null && response.getList().size() > 0) {
                Message msg = obtainUiMessage();
                msg.what = MSG_UI_LOAD_ADV_SUCCESS;
                msg.obj = response.getList();
                msg.sendToTarget();
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_ADV_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        HttpRequest<PartyListResponse> reqParty = new HttpRequest<>(UrlManager.GET_PARTY_LIST, PartyListResponse.class);
        reqParty.setIsGet(true);
        try {
            PartyListResponse response = reqParty.request();
            if (response != null && response.getList() != null && response.getList().size() > 0) {
                mPartyAdapter.setData(response.getList());
                sendEmptyUiMessage(MSG_UI_LOAD_DATA_SUCCESS);
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_DATA_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_DATA_FAIL:
                showFailView();
                break;
            case MSG_UI_LOAD_DATA_SUCCESS:
                showContentView();
                mPartyAdapter.notifyDataSetChanged();
                lvContent.addFooterView(inflate(R.layout.view_footer_finish_load_data));
                break;
            case MSG_UI_LOAD_ADV_FAIL:
                bannerView.setVisibility(View.GONE);
                break;
            case MSG_UI_LOAD_ADV_SUCCESS:
                bannerView.setVisibility(View.VISIBLE);
                ArrayList<AdvInfo> bannerList = (ArrayList<AdvInfo>) msg.obj;
                bannerView.setBannerList(bannerList);
                bannerView.startScroll(5);
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bannerView != null) {
            bannerView.destroy();
        }
    }
}
