
package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.fragment.SearchListFragment;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.AdvListResponse;
import com.buybuyall.market.widget.SelectBarView;

import cn.common.bitmap.core.ImageLoader;
import cn.common.exception.AppException;
import cn.common.ui.widgt.banner.BannerView;
import cn.common.utils.ViewUtil;

/**
 * 描述：分类列表 作者：jake on 2016/1/17 22:19
 */
public class VenuesListActivity extends StateActivity {
    private static final int MSG_BACK_LOAD_DATA = 0;

    private static final int MSG_UI_LOAD_SUCCESS = 0;

    private static final int MSG_UI_LOAD_FINISH = 1;


    private static final String KEY_ID = "key_type";

    private static final String KEY_KEY = "key_key";

    private String id;

    private String key;

    private BannerView bannerView;

    private SelectBarView selectBarView;

    private SearchListFragment fragment;

    private String title = "国家馆";

    public static void start(Context context, String id, String key) {
        Intent it = new Intent(context, VenuesListActivity.class);
        it.putExtra(KEY_ID, id);
        it.putExtra(KEY_KEY, key);
        context.startActivity(it);
    }

    @Override
    protected void dealIntent(Bundle data) {
        super.dealIntent(data);
        if (data != null) {
            key = data.getString(KEY_KEY, "");
            id = data.getString(KEY_ID, "");
        }
    }

    @Override
    protected void initView() {
        if (TextUtils.equals(id, "1")) {
            title="澳洲馆";
        } else if (TextUtils.equals(id, "2")) {
            title="美洲馆";

        }  else if (TextUtils.equals(id, "3")) {
            title="欧洲馆";
        } else if (TextUtils.equals(id, "4")) {
            title="亚洲馆";
        } else if (TextUtils.equals(id, "5")) {
        } else if (TextUtils.equals(id, "6")) {
        }
        setTitle(title);
        setContentView(R.layout.activtiy_venues_list);
        bannerView = (BannerView) findViewById(R.id.banner_view);
        bannerView.setStyle(BannerView.STYLE_DOT_CENTER);
        selectBarView = (SelectBarView) findViewById(R.id.select_bar);
        fragment = new SearchListFragment();
        fragment.setId(id);
        fragment.setPreLoading(true);
        fragment.setType(SearchListFragment.TYPE_COUNTRY_STORE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment)
                .commitAllowingStateLoss();
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
        showLoadingView();
    }

    @Override
    public void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
        switch (msg.what) {
            case MSG_BACK_LOAD_DATA:
                loadBannerListTask();
                break;
        }
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_SUCCESS:
                ViewUtil.setViewVisibility(bannerView, View.VISIBLE);
                bannerView.notifyDataSetChanged();
                break;
            case MSG_UI_LOAD_FINISH:
                showContentView();
                break;
        }
    }

    private void loadBannerListTask() {
        HttpRequest<AdvListResponse> request = new HttpRequest<>(UrlManager.GET_ADV,
                AdvListResponse.class);
        request.setIsGet(true);
        request.addParam("key", key);
        try {
            AdvListResponse response = request.request();
            if (response != null && response.getList() != null && response.getList().size() > 0) {
                bannerView.setBannerList(response.getList());
                sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        sendEmptyUiMessage(MSG_UI_LOAD_FINISH);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
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
        selectBarView.setListener(new SelectBarView.IListener() {
            @Override
            public void selectNew(int sortType) {
                setSortType(sortType);
                fragment.setKey(SearchListFragment.KEY_NEW);
                fragment.setOrder(SearchListFragment.ORDER_DOWN);
                fragment.reLoadData();

            }

            @Override
            public void selectPrizeUp(int sortType) {
                fragment.setKey(SearchListFragment.KEY_PRIZE);
                fragment.setOrder(SearchListFragment.ORDER_UP);
                setSortType(sortType);
                fragment.reLoadData();
            }

            @Override
            public void selectPrizeDown(int sortType) {
                setSortType(sortType);
                fragment.setKey(SearchListFragment.KEY_PRIZE);
                fragment.setOrder(SearchListFragment.ORDER_DOWN);
                fragment.reLoadData();
            }

            @Override
            public void selectSales(int sortType) {
                fragment.setKey(SearchListFragment.KEY_SALES);
                fragment.setOrder(SearchListFragment.ORDER_DOWN);
                setSortType(sortType);
                fragment.reLoadData();
            }
        });
    }

    private void setSortType(int sortType) {
        switch (sortType) {
            case SelectBarView.SORT_DISCOUNT:
                fragment.setXianshi(1);
                fragment.setGroupbuy(0);
                break;
            case SelectBarView.SORT_RUSH_BUY:
                fragment.setXianshi(0);
                fragment.setGroupbuy(1);
                break;
            case SelectBarView.SORT_DEFAULT:
            default:
                fragment.setXianshi(0);
                fragment.setGroupbuy(0);
                break;
        }
    }

    @Override
    public void reLoadData() {
    }
}
