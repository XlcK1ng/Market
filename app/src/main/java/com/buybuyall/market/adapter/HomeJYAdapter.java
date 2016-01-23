
package com.buybuyall.market.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.PackageInfo;
import com.buybuyall.market.widget.HomeDesItemView;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;
import cn.common.ui.widgt.HorizontalScrollGridView;
import cn.common.utils.DisplayUtil;
import cn.common.utils.ViewUtil;

/**
 * 描述：首页聚优惠商品列表适配器 作者：jake on 2015/12/29 23:45
 */
public class HomeJYAdapter extends BaseListAdapter<PackageInfo> {
    public HomeJYAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflate(R.layout.item_home_jy);
            holder.vGoods = convertView.findViewById(R.id.fl_goods);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvDiscount = (TextView) convertView.findViewById(R.id.tv_discount);
            holder.tvPrize = (TextView) convertView.findViewById(R.id.tv_prize);
            holder.tvMarketPrize = (TextView) convertView.findViewById(R.id.tv_market_price);
            holder.btnRushBuy = (Button) convertView.findViewById(R.id.btn_rush_buy);
            holder.homeDesItemView = (HomeDesItemView) convertView.findViewById(R.id.hdiv);
            holder.horizontalScrollGridView = (HorizontalScrollGridView) convertView
                    .findViewById(R.id.hsgv);
            holder.horizontalScrollGridView.setColumnWidth(DisplayUtil.getSreenDimens().x / 2
                    - DisplayUtil.dip(30));
            holder.tvMarketPrize.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中间横线
            holder.tvMarketPrize.getPaint().setAntiAlias(true);// 抗锯齿
            convertView.setTag(R.layout.item_home_jy, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_home_jy);
        }
        PackageInfo info = mDataList.get(position);
        if (info != null) {
            ImageLoader.getInstance().displayImage(info.getPackageImage(), holder.ivIcon);
            ViewUtil.setText2TextView(holder.tvName, info.getPackageName());
            if (info.getDiscount() > 0) {
                ViewUtil.setViewVisibility(holder.tvDiscount, View.VISIBLE);
                ViewUtil.setText2TextView(holder.tvDiscount, info.getDiscount() + "折");
            } else {
                ViewUtil.setViewVisibility(holder.tvDiscount, View.GONE);
            }
            ViewUtil.setText2TextView(holder.tvPrize, "¥" + info.getTotalPackagePrice());
            ViewUtil.setText2TextView(holder.tvMarketPrize, "¥" + info.getTotalOriPrice());
            if (!TextUtils.isEmpty(info.getPackageDesc())) {
                holder.homeDesItemView.setContent(info.getPackageDesc());
                ViewUtil.setViewVisibility(holder.homeDesItemView, View.VISIBLE);
            } else {
                ViewUtil.setViewVisibility(holder.homeDesItemView, View.GONE);
            }
            if (info.getGoodsList() != null && info.getGoodsList().size() > 0) {
                HomeJYItemGoodsAdapter adapter = new HomeJYItemGoodsAdapter(getContext());
                adapter.setData(info.getGoodsList());
                holder.horizontalScrollGridView.setAdapter(adapter);
                ViewUtil.setViewVisibility(holder.vGoods, View.VISIBLE);
            } else {
                ViewUtil.setViewVisibility(holder.vGoods, View.GONE);
            }
        }
        return convertView;
    }

    final class ViewHolder {
        View vGoods;

        ImageView ivIcon;

        TextView tvDiscount;

        TextView tvName;

        TextView tvPrize;

        TextView tvMarketPrize;

        Button btnRushBuy;

        HomeDesItemView homeDesItemView;

        HorizontalScrollGridView horizontalScrollGridView;
    }
}
