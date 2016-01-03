package com.buybuyall.market.fragment;

import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.HomeAdvAdapter;
import com.buybuyall.market.adapter.HomeBannerAdapter;
import com.buybuyall.market.adapter.HomeGoodsAdapter;
import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.entity.HomeBannerInfo;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.ui.widgt.HorizontalScrollGridView;
import cn.common.ui.widgt.InnerListView;
import cn.common.utils.DisplayUtil;

public class HomeFragment extends StateFragment {
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private static final int MSG_UI_LOAD_DATA_SUCCESS = 0;
    private static final int MSG_UI_LOAD_DATA_FAIL = 1;
    private static final int MSG_BACK_LOAD_DATA = 0;
    private ListView lvContent;
    private HorizontalScrollGridView hsgvAdv;
    private View vRecommend;
    private InnerListView lvBanner;
    private HomeGoodsAdapter mGoodsAdapter;
    private HomeAdvAdapter mAdvAdapter;
    private HomeBannerAdapter mBannerAdapter;

    @Override
    protected void initView() {
        lvContent = ViewCreator.createCommonListView(getActivity());
        View header = inflate(R.layout.view_header_home);
        lvContent.addHeaderView(header);
        setContentView(lvContent);
        hsgvAdv = (HorizontalScrollGridView) findViewById(R.id.hsgv_adv);
        vRecommend = findViewById(R.id.ll_adv);
        lvBanner = (InnerListView) findViewById(R.id.lv_banner);
        hsgvAdv.setColumnWidth(DisplayUtil.getSreenDimens().x / 3);
    }

    @Override
    protected void initData() {
        mGoodsAdapter = new HomeGoodsAdapter(getActivity());
        mAdvAdapter = new HomeAdvAdapter(getActivity());
        mBannerAdapter = new HomeBannerAdapter(getActivity());
        lvContent.setAdapter(mGoodsAdapter);
        hsgvAdv.setAdapter(mAdvAdapter);
        lvBanner.setAdapter(mBannerAdapter);
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
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
        ArrayList<GoodsInfo> goodsInfos = new ArrayList<>();
        ArrayList<AdvInfo> advInfos = new ArrayList<>();
        ArrayList<HomeBannerInfo> bannerInfos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i < 3) {
                bannerInfos.add(new HomeBannerInfo());
            }
            if (i < 10) {
                advInfos.add(new AdvInfo());
            }
            goodsInfos.add(new GoodsInfo());
        }
        mBannerAdapter.setData(bannerInfos);
        mAdvAdapter.setData(advInfos);
        mGoodsAdapter.setData(goodsInfos);
        sendEmptyUiMessage(MSG_UI_LOAD_DATA_SUCCESS);
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
                mBannerAdapter.notifyDataSetChanged();
                mAdvAdapter.notifyDataSetChanged();
                hsgvAdv.notifyDataSetChanged();
                mGoodsAdapter.notifyDataSetChanged();
                break;
        }
    }
}
