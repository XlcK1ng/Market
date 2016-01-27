
package com.buybuyall.market.fragment;

import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.HomeAdvAdapter;
import com.buybuyall.market.adapter.HomeVenuesAdapter;
import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.AdvListResponse;
import com.buybuyall.market.logic.http.response.JPGoodsListResponse;
import com.buybuyall.market.utils.JumpUtil;

import cn.common.bitmap.core.ImageLoader;
import cn.common.exception.AppException;
import cn.common.ui.widgt.HorizontalScrollGridView;
import cn.common.ui.widgt.StickyScrollView;
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

    private RadioGroup rgSelect;

    private HomeListFragment homeListFragment;

    private ImageView ivGoTop;

    private StickyScrollView scrollView;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_home);
        scrollView = (StickyScrollView) findViewById(R.id.root);
        ivGoTop = (ImageView) findViewById(R.id.iv_go_top);
        gvVenues = (GridView) findViewById(R.id.gv_venues);
        rgSelect = (RadioGroup) findViewById(R.id.rg_select);
        hsgvAdv = (HorizontalScrollGridView) findViewById(R.id.hsgv_adv);
        hsgvAdv.setColumnWidth(DisplayUtil.getSreenDimens().x - DisplayUtil.dip(70));
        bannerView = (BannerView) findViewById(R.id.banner_view);
        bannerView.setStyle(BannerView.STYLE_DOT_CENTER);
        bannerView.getDotView().setNormalColor(getColor(R.color.gray_dfdfdf));
        bannerView.getDotView().setSelectColor(getColor(R.color.orange_fb6e52));
        ViewUtil.setViewVisibility(hsgvAdv, View.GONE);
        ViewUtil.setViewVisibility(bannerView, View.GONE);
        homeListFragment = new HomeListFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.fl_content, homeListFragment)
                .commitAllowingStateLoss();

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        scrollView.setScrollChangeListener(new StickyScrollView.IScrollChangeListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (t > DisplayUtil.getSreenDimens().y) {
                    ViewUtil.setViewVisibility(ivGoTop, View.VISIBLE);
                } else {
                    ViewUtil.setViewVisibility(ivGoTop, View.GONE);
                }
            }
        });
        ivGoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(0, 0);
            }
        });
        rgSelect.setOnCheckedChangeListener(this);
        bannerView.setBannerListener(new BannerView.IListener() {
            @Override
            public void itemClick(Object banner) {
                AdvInfo advInfo = (AdvInfo) banner;
                JumpUtil.advJump(getActivity(), advInfo);
            }

            @Override
            public void loadImage(Object banner, ImageView ivBanner) {
                AdvInfo advInfo = (AdvInfo) banner;
                ImageLoader.getInstance().displayImage(advInfo.getAdvPic(), ivBanner);
            }
        });
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
                bannerView.notifyDataSetChanged();
                bannerView.startScroll(5);
                break;
            case MSG_UI_LOAD_ADV_FAIL:
                ViewUtil.setViewVisibility(hsgvAdv, View.GONE);
                break;
            case MSG_UI_LOAD_ADV_SUCCESS:
                ViewUtil.setViewVisibility(hsgvAdv, View.VISIBLE);
                mAdvAdapter.notifyDataSetChanged();
                hsgvAdv.notifyDataSetChanged();
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
                break;
        }
    }

    private void loadAllDataTask() {
        // 请求banner
        HttpRequest<AdvListResponse> reqBanner = new HttpRequest<>(UrlManager.GET_ADV,
                AdvListResponse.class);
        reqBanner.setIsGet(true);
        reqBanner.addParam("key", UrlManager.Keys.HOME_BANNER);
        try {
            AdvListResponse response = reqBanner.request();
            if (response != null && response.getList() != null && response.getList().size() > 0) {
                bannerView.setBannerList(response.getList());
                sendEmptyUiMessage(MSG_UI_LOAD_BANNER_SUCCESS);
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_BANNER_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_LOAD_BANNER_FAIL);
        }
        // 请求国家馆
        HttpRequest<AdvListResponse> reqVenues = new HttpRequest<>(UrlManager.GET_ADV,
                AdvListResponse.class);
        reqVenues.setIsGet(true);
        reqVenues.addParam("key", UrlManager.Keys.HOME_VENUES);
        try {
            AdvListResponse response = reqVenues.request();
            if (response != null && response.getList() != null && response.getList().size() > 0) {
                mVenuesAdapter.setData(response.getList());
                sendEmptyUiMessage(MSG_UI_LOAD_VENUES_SUCCESS);
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_VENUES_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_LOAD_VENUES_FAIL);
        }
        // 请求专题
        HttpRequest<AdvListResponse> reqAdv = new HttpRequest<>(UrlManager.GET_ADV,
                AdvListResponse.class);
        reqAdv.setIsGet(true);
        reqAdv.addParam("key", UrlManager.Keys.HOME_ADV);
        try {
            AdvListResponse response = reqAdv.request();
            if (response != null && response.getList() != null && response.getList().size() > 0) {
                mAdvAdapter.setData(response.getList());
                sendEmptyUiMessage(MSG_UI_LOAD_ADV_SUCCESS);
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_ADV_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_LOAD_ADV_FAIL);
        }
        // 请求精品物品
        HttpRequest<JPGoodsListResponse> request = new HttpRequest<>(UrlManager.HOME_JP,
                JPGoodsListResponse.class);
        request.setIsGet(true);
        try {
            JPGoodsListResponse response = request.request();
            if (response != null && response.isOk()) {
                homeListFragment.setJPData(response.getList());
                sendEmptyUiMessage(MSG_UI_LOAD_GOODS_SUCCESS);
            } else {
                sendEmptyUiMessage(MSG_UI_LOAD_GOODS_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_LOAD_GOODS_FAIL);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        check(checkedId);
    }

    private void check(int checkedId) {
        if (checkedId == R.id.rb_left) {
            homeListFragment.loadData(HomeListFragment.TYPE_JP);
        } else if (checkedId == R.id.rb_center) {
            homeListFragment.loadData(HomeListFragment.TYPE_JY);
        } else if (checkedId == R.id.rb_right) {
            homeListFragment.loadData(HomeListFragment.TYPE_DP);
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
