
package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.OtherGoodsAdapter;
import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.AdvListResponse;
import com.buybuyall.market.logic.http.response.JPGoodsListResponse;

import cn.common.bitmap.core.ImageLoader;
import cn.common.exception.AppException;
import cn.common.ui.widgt.HorizontalScrollGridView;
import cn.common.ui.widgt.StickyScrollView;
import cn.common.ui.widgt.banner.BannerView;
import cn.common.utils.DisplayUtil;
import cn.common.utils.ViewUtil;

/**
 * 描述:物品详情页
 *
 * @author jakechen
 * @since 2016/1/21 18:07
 */
public class GoodsDetailActivity extends StateActivity {
    private static final String KEY_GOODS_ID = "key_goods_id";

    public static void start(Context context, long goodsId) {
        if (context != null) {
            Intent it = new Intent(context, GoodsDetailActivity.class);
            it.putExtra(KEY_GOODS_ID, goodsId);
            context.startActivity(it);
        }
    }

    private long goodsId = 0;

    @Override
    protected void dealIntent(Bundle data) {
        super.dealIntent(data);
        if (data != null) {
            goodsId = data.getLong(KEY_GOODS_ID, 0);
        }
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

    private HorizontalScrollGridView hsgvOtherGoods;

    private BannerView bannerView;

    private ImageView ivGoTop;

    private StickyScrollView scrollView;

    private OtherGoodsAdapter mOtherGoodsAdapter;

    private WebView webView;

    private ImageView ivTrolley;

    @Override
    protected void initView() {
        setTitle("商品详情");
        setContentView(R.layout.activtiy_goods_detail);
        ivTrolley = (ImageView) findViewById(R.id.iv_right);
        ivTrolley.setImageResource(R.drawable.img_title_trolley);
        ViewUtil.setViewVisibility(ivTrolley, View.VISIBLE);
        scrollView = (StickyScrollView) findViewById(R.id.root);
        webView = (WebView) findViewById(R.id.wv_content);
        ivGoTop = (ImageView) findViewById(R.id.iv_go_top);
        hsgvOtherGoods = (HorizontalScrollGridView) findViewById(R.id.hsgv);
        hsgvOtherGoods.setColumnWidth(DisplayUtil.getSreenDimens().x - DisplayUtil.dip(70));
        bannerView = (BannerView) findViewById(R.id.banner_view);
        bannerView.setStyle(BannerView.STYLE_DOT_CENTER);
        bannerView.getDotView().setNormalColor(getColor(R.color.gray_dfdfdf));
        bannerView.getDotView().setSelectColor(getColor(R.color.orange_fb6e52));
        ViewUtil.setViewVisibility(hsgvOtherGoods, View.GONE);
        ViewUtil.setViewVisibility(bannerView, View.GONE);
        webView.loadUrl("http://www.mi.com/mi4/");
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
        bannerView.setBannerListener(new BannerView.IListener() {
            @Override
            public void itemClick(Object banner) {
                // do nothing
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
        mOtherGoodsAdapter = new OtherGoodsAdapter(this);
        hsgvOtherGoods.setAdapter(mOtherGoodsAdapter);
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
                ViewUtil.setViewVisibility(hsgvOtherGoods, View.GONE);
                break;
            case MSG_UI_LOAD_ADV_SUCCESS:
                ViewUtil.setViewVisibility(hsgvOtherGoods, View.VISIBLE);
                hsgvOtherGoods.notifyDataSetChanged();
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
        HttpRequest<AdvListResponse> reqBanner = new HttpRequest<>(UrlManager.GOODS_DETAIL,
                AdvListResponse.class);
        reqBanner.setIsGet(true);
        reqBanner.addParam("goods_id", "" + goodsId);
        if (false) {
            // 已登录
            reqBanner.addParam("client", "app");
            reqBanner.addParam("key ", "");
        }
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
        // 请求精品物品
        HttpRequest<JPGoodsListResponse> request = new HttpRequest<>(UrlManager.HOME_JP,
                JPGoodsListResponse.class);
        request.setIsGet(true);
        try {
            JPGoodsListResponse response = request.request();
            if (response != null && response.isOk()) {
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
    public void onDestroy() {
        super.onDestroy();
        if (bannerView != null) {
            bannerView.destroy();
        }

    }
}
