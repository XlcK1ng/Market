package com.buybuyall.market.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.GoodsInfo;

import java.util.Map;

import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.DisplayUtil;

/**
 * 描述：首页商品列表适配器
 * 作者：jake on 2015/12/29 23:45
 */
public class HomeGoodsAdapter extends BaseListAdapter<GoodsInfo> {
    public HomeGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       return inflate(R.layout.item_home_goods);
    }
}