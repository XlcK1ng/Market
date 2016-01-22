package com.buybuyall.market.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.AdvInfo;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;
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
            convertView = inflate(R.layout.item_home_adv);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.vDivider = convertView.findViewById(R.id.v_divider);
            convertView.setTag(R.layout.item_home_adv, holder);
//            convertView.setOnClickListener(listener);
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_home_adv);
        }
        if (position == getCount() - 1) {
            ViewUtil.setViewVisibility(holder.vDivider, View.GONE);
        } else {
            ViewUtil.setViewVisibility(holder.vDivider, View.VISIBLE);
        }
        AdvInfo info = mDataList.get(position);
        if (info != null) {
            convertView.setTag(info);
            ImageLoader.getInstance().displayImage(info.getAdvPic(), holder.ivIcon);
        }
        return convertView;
    }

    final class ViewHolder {
        ImageView ivIcon;


        View vDivider;
    }
}