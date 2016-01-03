package com.buybuyall.market.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.HomeBannerInfo;

import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.DisplayUtil;

public class HomeBannerAdapter extends BaseListAdapter<HomeBannerInfo> {
    public HomeBannerAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return inflate(R.layout.item_home_goods);
    }
}
