package com.buybuyall.market.fragment;

import android.widget.ListView;

import com.buybuyall.market.R;
import com.buybuyall.market.widget.ViewCreator;

/**
 * 描述：
 * 作者：jake on 2016/1/10 22:03
 */
public class PurchasedGoodsFragment extends StateFragment {
    public static PurchasedGoodsFragment newInstance() {
        return new PurchasedGoodsFragment();
    }

    private ListView lvContent;

    @Override
    protected void initView() {
        lvContent = ViewCreator.createCommonListView(getActivity());
        lvContent.addHeaderView(inflate(R.layout.view_header_group));
        setContentView(lvContent);
        setNoDataTip("暂无已购商品");
        showNoDataView();
    }

    @Override
    public void reLoadData() {

    }
}
