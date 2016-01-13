package com.buybuyall.market.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.BrandInfo;
import com.buybuyall.market.utils.ToastUtil;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.CommonUtil;

/**
 * 描述：
 * 作者：jake on 2016/1/13 22:31
 */
public class BrandAdapter extends BaseListAdapter<BrandInfo> {
    public BrandAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflate(R.layout.item_brand);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvDes = (TextView) convertView.findViewById(R.id.tv_des);
            convertView.setTag(R.layout.item_party, holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BrandInfo info = (BrandInfo) v.getTag();
                    ToastUtil.show(info.getBrandName()+"点击item");
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_party);
        }
        BrandInfo info = mDataList.get(position);
        if (info != null) {
            convertView.setTag(info);
            ImageLoader.getInstance().displayImage(info.getBrandPic(), holder.ivIcon);
            CommonUtil.setText2TextView(holder.tvName, info.getBrandName());
            CommonUtil.setText2TextView(holder.tvDes, info.getBrandDescription());
        }
        return convertView;
    }


    final class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvDes;
    }
}