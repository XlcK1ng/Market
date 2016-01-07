package com.buybuyall.market.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.PartyInfo;
import com.buybuyall.market.utils.ToastUtil;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.CommonUtil;

/**
 * 描述：
 * 作者：jake on 2016/1/7 21:04
 */
public class PartyAdapter extends BaseListAdapter<PartyInfo> {
    public PartyAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflate(R.layout.item_party);
            holder.vDividerTop=convertView.findViewById(R.id.v_divider_top);
            holder.ivBanner = (ImageView) convertView.findViewById(R.id.iv_banner);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvPartyNum = (TextView) convertView.findViewById(R.id.tv_party_num);
            holder.tvPartyPrize = (TextView) convertView.findViewById(R.id.tv_party_prize);
            holder.tvMarketPrize = (TextView) convertView.findViewById(R.id.tv_market_price);
            holder.btnParty = (Button) convertView.findViewById(R.id.btn_party);
            holder.tvMarketPrize.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
            holder.tvMarketPrize.getPaint().setAntiAlias(true);// 抗锯齿
            convertView.setTag(R.layout.item_party, holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PartyInfo info = (PartyInfo) v.getTag();
                    ToastUtil.show("点击item");
                }
            });
            holder.btnParty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PartyInfo info = (PartyInfo) v.getTag();
                    ToastUtil.show("点击去开团");

                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_party);
        }
        PartyInfo info = mDataList.get(position);
        if (info != null) {
            holder.btnParty.setTag(info);
            convertView.setTag(info);
            ImageLoader.getInstance().displayImage(info.getPartyImage(), holder.ivBanner);
            CommonUtil.setText2TextView(holder.tvTitle, info.getTitle());
            CommonUtil.setText2TextView(holder.tvContent, info.getPartyDesc());
            CommonUtil.setText2TextView(holder.tvPartyNum, info.getPartyNum() + "人团");
            CommonUtil.setText2TextView(holder.tvPartyPrize, "¥" + info.getPartyGoodsPrice());
            CommonUtil.setText2TextView(holder.tvMarketPrize, "¥" + info.getGoodsMarketprice());
        }
        return convertView;
    }


    final class ViewHolder {
        View vDividerTop;
        ImageView ivBanner;
        TextView tvTitle;
        TextView tvContent;
        TextView tvPartyNum;
        TextView tvPartyPrize;
        TextView tvMarketPrize;
        Button btnParty;
    }
}
