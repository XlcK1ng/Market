
package com.buybuyall.market.fragment;

import com.buybuyall.market.adapter.GoodsSearchAdapter;
import com.buybuyall.market.entity.GoodsInfo;

import java.util.ArrayList;

import cn.common.ui.adapter.BaseListAdapter;

/**
 * 描述:分类列表
 *
 * @author jakechen
 * @since 2016/1/20 10:39
 */
public class ClassListFragment extends BasePullListFragment<GoodsInfo> {
    @Override
    protected BaseListAdapter<GoodsInfo> createAdapter() {
        return new GoodsSearchAdapter(getActivity());
    }

    @Override
    protected ArrayList<GoodsInfo> loadData(int pageIndex, int pageSize) {
        return null;
    }
}
