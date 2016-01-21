package com.buybuyall.market.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.entity.ClassInfo;
import com.buybuyall.market.entity.HomeBannerInfo;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.DisplayUtil;
import cn.common.utils.ViewUtil;

public class HomeAdvAdapter extends BaseListAdapter<AdvInfo> {

    public HomeAdvAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflate(R.layout.item_class_child);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.vDivider = convertView.findViewById(R.id.v_divider);
            convertView.setTag(R.layout.item_class_child, holder);
//            convertView.setOnClickListener(listener);
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_class_child);
        }
        if (position == getCount() - 1) {
            ViewUtil.setViewVisibility(holder.vDivider, View.GONE);
        } else {
            ViewUtil.setViewVisibility(holder.vDivider, View.VISIBLE);
        }
        AdvInfo info = mDataList.get(position);
        if (info != null) {
            convertView.setTag(info);
            ImageLoader.getInstance().displayImage("http://img3.imgtn.bdimg.com/it/u=2018844086,1655614987&fm=21&gp=0.jpg", holder.ivIcon);
        }
        return convertView;
    }

    final class ViewHolder {
        ImageView ivIcon;


        View vDivider;
    }
}