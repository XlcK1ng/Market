
package com.buybuyall.market.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.ui.GoodsDetailActivity;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.ViewUtil;

/**
 * 描述：搜索物品的适配器 作者：jake on 2016/1/17 11:02
 */
public class GoodsSearchAdapter extends BaseListAdapter<GoodsInfo> {
    public GoodsSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflate(R.layout.item_search_goods);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPrize = (TextView) convertView.findViewById(R.id.tv_prize);
            holder.tvMarketPrize = (TextView) convertView.findViewById(R.id.tv_market_price);
            holder.tvMarketPrize.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中间横线
            holder.tvMarketPrize.getPaint().setAntiAlias(true);// 抗锯齿
            convertView.setTag(R.layout.item_search_goods, holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsInfo info = (GoodsInfo) v.getTag();
                    if (info != null) {
                        GoodsDetailActivity.start(v.getContext(), info.getGoodsId());
                    }
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_search_goods);
        }
        GoodsInfo info = mDataList.get(position);
        if (info != null) {
            convertView.setTag(info);
            ImageLoader.getInstance().displayImage(info.getGoodsImage(), holder.ivIcon);
            ViewUtil.setText2TextView(holder.tvName, info.getGoodsName());
            ViewUtil.setText2TextView(holder.tvPrize, "¥" + info.getGoodsPrice());
            ViewUtil.setText2TextView(holder.tvMarketPrize, "¥" + info.getGoodsMarketPrice());
        }
        return convertView;
    }

    final class ViewHolder {
        ImageView ivIcon;

        TextView tvName;

        TextView tvPrize;

        TextView tvMarketPrize;
    }
}
