package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.OtherGoodsAdapter;
import com.buybuyall.market.entity.ImageInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.GoodsDetailResponse;
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

  private static final int MSG_BACK_LOAD_DATA = 0;

  private static final int MSG_UI_LOAD_SUCCESS = 0;

  private static final int MSG_UI_LOAD_FAIL = 1;

  private HorizontalScrollGridView hsgvOtherGoods;

  private BannerView bannerView;

  private ImageView ivGoTop;

  private StickyScrollView scrollView;

  private OtherGoodsAdapter mOtherGoodsAdapter;

  private WebView webView;

  private ImageView ivTrolley;
  private TextView tvName;
  private TextView tvPrize;
  private GoodsDetailResponse mGoodsDetailResponse;

  @Override
  protected void initView() {
    setTitle("商品详情");
    setContentView(R.layout.activtiy_goods_detail);
    ivTrolley = (ImageView) findViewById(R.id.iv_right);
    ivTrolley.setImageResource(R.drawable.img_title_trolley);
    ViewUtil.setViewVisibility(ivTrolley, View.VISIBLE);
    scrollView = (StickyScrollView) findViewById(R.id.root);
    webView = (WebView) findViewById(R.id.wv_content);
    tvName = (TextView) findViewById(R.id.tv_name);
    tvPrize = (TextView) findViewById(R.id.tv_prize);
    ivGoTop = (ImageView) findViewById(R.id.iv_go_top);
    hsgvOtherGoods = (HorizontalScrollGridView) findViewById(R.id.hsgv);
    hsgvOtherGoods.setColumnWidth(DisplayUtil.getSreenDimens().x/2 - DisplayUtil.dip(20));
    bannerView = (BannerView) findViewById(R.id.banner_view);
    bannerView.setStyle(BannerView.STYLE_DOT_CENTER);
    bannerView.getDotView().setNormalColor(getColor(R.color.gray_dfdfdf));
    bannerView.getDotView().setSelectColor(getColor(R.color.orange_fb6e52));
    ViewUtil.setViewVisibility(hsgvOtherGoods, View.GONE);
    ViewUtil.setViewVisibility(bannerView, View.GONE);
    webView.setWebViewClient(new WebViewClient());
    webView.setDrawingCacheEnabled(false);
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    settings.setDefaultTextEncodingName(Xml.Encoding.UTF_8.toString());
    settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    settings.setAppCacheEnabled(false);
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
        ImageInfo advInfo = (ImageInfo) banner;
        ImageLoader.getInstance().displayImage(advInfo.getThumb(), ivBanner);
      }
    });
  }

  @Override
  protected void initData() {
    mOtherGoodsAdapter = new OtherGoodsAdapter(this);
    hsgvOtherGoods.setAdapter(mOtherGoodsAdapter);
    showLoadingView();
    sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
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

  @Override
  public void handleUiMessage(Message msg) {
    super.handleUiMessage(msg);
    switch (msg.what) {
      case MSG_UI_LOAD_FAIL:
        showFailView();
        break;
      case MSG_UI_LOAD_SUCCESS:
        showContentView();
        handleLoadSuccess(mGoodsDetailResponse);
        break;
    }
  }

  private void handleLoadSuccess(GoodsDetailResponse response) {
    if (response == null) {
      return;
    }
    if (response.getImageList() != null && response.getImageList().size() > 0) {
      ViewUtil.setViewVisibility(bannerView, View.VISIBLE);
      bannerView.setBannerList(response.getImageList());
      bannerView.notifyDataSetChanged();
    } else {
      ViewUtil.setViewVisibility(bannerView, View.GONE);
    }
    ViewUtil.setText2TextView(tvName, response.getGoodsName());
    ViewUtil.setText2TextView(tvPrize, "¥" + response.getGoodPrize());
    if (TextUtils.isEmpty(response.getGoodsBody())) {
      ViewUtil.setViewVisibility(webView, View.GONE);
    } else {
      ViewUtil.setViewVisibility(webView, View.VISIBLE);
      // 加载显示WebView
      webView.loadDataWithBaseURL(null, response.getGoodsBody(), "text/html", Xml.Encoding.UTF_8.toString(), "about:blank");
    }
    if (mOtherGoodsAdapter.getCount() > 0) {
      ViewUtil.setViewVisibility(hsgvOtherGoods, View.VISIBLE);
      mOtherGoodsAdapter.notifyDataSetChanged();
      hsgvOtherGoods.notifyDataSetChanged();
    } else {
      ViewUtil.setViewVisibility(hsgvOtherGoods, View.GONE);
    }
  }

  private void loadDataTask() {
    // 请求底部数据
    HttpRequest<JPGoodsListResponse> reqSame = new HttpRequest<>(UrlManager.SAME_GOODS_LIST, JPGoodsListResponse.class);
    reqSame.setIsGet(true);
    reqSame.addParam("goods_id", "" + goodsId);
    reqSame.addParam("limit", "0");
    try {
      JPGoodsListResponse resSame = reqSame.request();
      if (reqSame != null) {
        mOtherGoodsAdapter.setData(resSame.getList());
      }
    } catch (AppException e) {
      e.printStackTrace();
    }
    HttpRequest<GoodsDetailResponse> request = new HttpRequest<>(UrlManager.GOODS_DETAIL, GoodsDetailResponse.class);
    request.setIsGet(true);
    request.addParam("goods_id", "" + goodsId);
    if (false) {
      // 已登录
      request.addParam("client", "app");
      request.addParam("key ", "");
    }
    try {
      mGoodsDetailResponse = request.request();
      if (mGoodsDetailResponse != null && mGoodsDetailResponse.isOk()) {
        sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
      } else {
        sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
      }
    } catch (AppException e) {
      e.printStackTrace();
      sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
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
