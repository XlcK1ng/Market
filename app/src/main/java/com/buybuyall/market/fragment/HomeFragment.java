package com.buybuyall.market.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.HomeVenuesAdapter;
import com.buybuyall.market.adapter.HomeAdvAdapter;
import com.buybuyall.market.adapter.HomeGoodsAdapter;
import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.entity.HomeBannerInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.AdvListResponse;

import java.util.ArrayList;

import cn.common.bitmap.core.ImageLoader;
import cn.common.exception.AppException;
import cn.common.ui.widgt.HorizontalScrollGridView;
import cn.common.ui.widgt.banner.BannerView;
import cn.common.utils.DisplayUtil;
import cn.common.utils.ViewUtil;

public class HomeFragment extends StateFragment implements RadioGroup.OnCheckedChangeListener {
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private static final int MSG_BACK_LOAD_ALL_DATA = 0;
    private static final int MSG_UI_LOAD_GOODS_SUCCESS = 0;
    private static final int MSG_UI_LOAD_GOODS_FAIL = 1;
    private static final int MSG_UI_LOAD_ADV_SUCCESS = 2;
    private static final int MSG_UI_LOAD_ADV_FAIL = 3;
    private static final int MSG_UI_LOAD_VENUES_SUCCESS = 4;
    private static final int MSG_UI_LOAD_VENUES_FAIL = 5;
    private static final int MSG_UI_LOAD_BANNER_SUCCESS = 6;
    private static final int MSG_UI_LOAD_BANNER_FAIL = 7;


    private HorizontalScrollGridView hsgvAdv;

    private HomeVenuesAdapter mVenuesAdapter;
    private HomeAdvAdapter mAdvAdapter;
    private BannerView bannerView;
    private GridView gvVenues;
    private View vOutsideBar;
    private View vInsideBar;
    private RadioGroup rgOutside;
    private RadioGroup rgInside;
    private HomeListFragment homeListFragment;
    private ScrollView scrollView;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_home);
        scrollView = (ScrollView) findViewById(R.id.root);
        FrameLayout layout = (FrameLayout) findViewById(R.id.fl_outside);
        layout.addView(inflate(R.layout.view_home_select_bar));
        vOutsideBar = layout.findViewById(R.id.ll_select_bar);
        rgOutside = (RadioGroup) layout.findViewById(R.id.rg_select);
        vInsideBar = findViewById(R.id.ll_select_bar);
        gvVenues = (GridView) findViewById(R.id.gv_venues);
        rgInside = (RadioGroup) findViewById(R.id.rg_select);
        hsgvAdv = (HorizontalScrollGridView) findViewById(R.id.hsgv_adv);
        hsgvAdv.setColumnWidth(DisplayUtil.getSreenDimens().x - DisplayUtil.dip(70));
        bannerView = (BannerView) findViewById(R.id.banner_view);
        bannerView.setStyle(BannerView.STYLE_DOT_CENTER);
        bannerView.getDotView().setNormalColor(getColor(R.color.gray_dfdfdf));
        bannerView.getDotView().setSelectColor(getColor(R.color.orange_fb6e52));
        ViewUtil.setViewVisibility(hsgvAdv, View.GONE);
        ViewUtil.setViewVisibility(bannerView, View.GONE);
        homeListFragment = new HomeListFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.fl_content, homeListFragment).commitAllowingStateLoss();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        rgOutside.setOnCheckedChangeListener(this);
        rgInside.setOnCheckedChangeListener(this);
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
//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (vInsideBar.getTop() <= vOutsideBar.getTop()) {
//                    ViewUtil.setViewVisibility(vOutsideBar, View.VISIBLE);
//                } else {
//                    ViewUtil.setViewVisibility(vOutsideBar, View.GONE);
//                }
//            }
//        });
    }

    @Override
    protected void initData() {
        mVenuesAdapter = new HomeVenuesAdapter(getActivity());
        mAdvAdapter = new HomeAdvAdapter(getActivity());
        gvVenues.setAdapter(mVenuesAdapter);
        hsgvAdv.setAdapter(mAdvAdapter);
        showLoadingView();
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_ALL_DATA);
    }

    @Override
    public void reLoadData() {
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_ALL_DATA);
    }

    @Override
    public void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
        switch (msg.what) {
            case MSG_BACK_LOAD_ALL_DATA:
                loadAllDataTask();
                break;
        }
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_BANNER_FAIL:
                ViewUtil.setViewVisibility(bannerView, View.GONE);
                break;
            case MSG_UI_LOAD_BANNER_SUCCESS:
                ViewUtil.setViewVisibility(bannerView, View.VISIBLE);
                ArrayList<AdvInfo> bannerList = (ArrayList<AdvInfo>) msg.obj;
                bannerView.setBannerList(bannerList);
                bannerView.startScroll(5);
                break;
            case MSG_UI_LOAD_ADV_FAIL:
                ViewUtil.setViewVisibility(hsgvAdv, View.GONE);
                break;
            case MSG_UI_LOAD_ADV_SUCCESS:
                ViewUtil.setViewVisibility(hsgvAdv, View.VISIBLE);
                mAdvAdapter.notifyDataSetChanged();
                break;
            case MSG_UI_LOAD_VENUES_FAIL:
                ViewUtil.setViewVisibility(gvVenues, View.GONE);
                break;
            case MSG_UI_LOAD_VENUES_SUCCESS:
                ViewUtil.setViewVisibility(gvVenues, View.VISIBLE);
                mVenuesAdapter.notifyDataSetChanged();
                break;
            case MSG_UI_LOAD_GOODS_FAIL:
                showFailView();
                break;
            case MSG_UI_LOAD_GOODS_SUCCESS:
                showContentView();
                homeListFragment.notifyDataSetChanged();
                break;
        }
    }

    private void loadAllDataTask() {
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
            sendEmptyUiMessage(MSG_UI_LOAD_ADV_FAIL);
        }
        ArrayList<GoodsInfo> goodsInfos = new ArrayList<>();
        ArrayList<AdvInfo> advInfos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i < 4) {
                advInfos.add(new AdvInfo());
            }
            goodsInfos.add(new GoodsInfo());
        }

        mAdvAdapter.setData(advInfos);
        mVenuesAdapter.setData(advInfos);
        homeListFragment.setData(goodsInfos);
        sendEmptyUiMessage(MSG_UI_LOAD_GOODS_SUCCESS);
    }


    private int laseCheckedId;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == rgOutside) {
            rgInside.check(checkedId);
        } else if (group == rgInside) {
            rgOutside.check(checkedId);
        }
        if (laseCheckedId != checkedId) {
            check(checkedId);
            laseCheckedId = checkedId;
        }
    }

    private void check(int checkedId) {
        if (checkedId == R.id.rb_left) {
            //TODO
            homeListFragment.setKey("1");
        } else if (checkedId == R.id.rb_center) {
            homeListFragment.setKey("2");
        } else if (checkedId == R.id.rb_right) {
            homeListFragment.setKey("3");
        }
    }
}
