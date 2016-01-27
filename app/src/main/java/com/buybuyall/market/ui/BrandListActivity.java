
package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.BrandInfo;
import com.buybuyall.market.fragment.SearchListFragment;
import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.SelectBarView;

import cn.common.bitmap.core.ImageLoader;
import cn.common.utils.ViewUtil;

/**
 * 描述:品牌列表
 *
 * @author jakechen
 * @since 2016/1/27 17:33
 */
public class BrandListActivity extends StateActivity {
    public static final String KEY_INFO = "key_info";

    public static void start(Context context, BrandInfo info) {
        if (context != null && info != null) {
            Intent it = new Intent(context, BrandListActivity.class);
            it.putExtra(KEY_INFO, info);
            context.startActivity(it);
        }
    }

    private BrandInfo mBrandInfo;

    private ImageView ivIcon;

    private TextView tvName;

    private TextView tvDes;

    private SelectBarView selectBarView;

    private SearchListFragment fragment;

    @Override
    protected void dealIntent(Bundle data) {
        super.dealIntent(data);
        if (data != null) {
            mBrandInfo = (BrandInfo) data.getSerializable(KEY_INFO);
        }
        if (mBrandInfo == null) {
            ToastUtil.show("初始化失败，请重试");
            finish();
        }
    }

    @Override
    protected void initView() {
        setTitle(mBrandInfo.getBrandName());
        setContentView(R.layout.activtiy_brand_list);
        selectBarView = (SelectBarView) findViewById(R.id.select_bar);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDes = (TextView) findViewById(R.id.tv_des);
        ImageLoader.getInstance().displayImage(mBrandInfo.getBrandPic(), ivIcon);
        ViewUtil.setText2TextView(tvName, mBrandInfo.getBrandName());
        ViewUtil.setText2TextView(tvDes, mBrandInfo.getBrandDescription());
        fragment = new SearchListFragment();
        fragment.setType(SearchListFragment.TYPE_BRAND);
        fragment.setId("" + mBrandInfo.getBrandId());
        fragment.setPreLoading(true);
        fragment.setPreNoDataTip("暂无本品牌产品，去别处逛逛");
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
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
