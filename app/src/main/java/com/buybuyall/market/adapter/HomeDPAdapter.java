
package com.buybuyall.market.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.GoodsInfo;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.ViewUtil;

/**
 * 描述：首页大牌商品列表适配器 作者：jake on 2015/12/29 23:45
 */
public class HomeDPAdapter extends BaseListAdapter<GoodsInfo> {
    public HomeDPAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflate(R.layout.item_home_dp);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvDes = (TextView) convertView.findViewById(R.id.tv_des);
            holder.tvPrize = (TextView) convertView.findViewById(R.id.tv_prize);
            holder.tvTag = (TextView) convertView.findViewById(R.id.tv_tag);
            convertView.setTag(R.layout.item_home_dp, holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ClassInfo info = (ClassInfo) v.getTag();
                    // ClassListActivity.start(v.getContext(), info.getGcId(),
                    // info.getGcName());
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_home_dp);
        }
        GoodsInfo info = mDataList.get(position);
        if (info != null) {
            convertView.setTag(info);
            ImageLoader.getInstance().displayImage(info.getGoodsImage(), holder.ivIcon);
            ViewUtil.setText2TextView(holder.tvName, info.getGoodsName());
            ViewUtil.setText2TextView(holder.tvDes, info.getGoodsJingLe());
            ViewUtil.setText2TextView(holder.tvPrize, "¥" + info.getGoodsPrice());
            if (info.isBS() || info.isBY()) {
                ViewUtil.setViewVisibility(holder.tvTag, View.VISIBLE);
                String show = null;
                if (info.isBS() && info.isBY()) {
                    show = "包邮包税";
                } else if (info.isBY()) {
                    show = "包邮";
                } else if (info.isBS()) {
                    show = "包税";
                }
                ViewUtil.setText2TextView(holder.tvTag, show);
            } else {
                ViewUtil.setViewVisibility(holder.tvTag, View.GONE);
            }

        }
        return convertView;
    }

    final class ViewHolder {
        ImageView ivIcon;

        TextView tvName;

        TextView tvDes;

        TextView tvPrize;

        TextView tvTag;
    }
}
