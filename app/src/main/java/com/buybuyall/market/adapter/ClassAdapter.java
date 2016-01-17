package com.buybuyall.market.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.BrandInfo;
import com.buybuyall.market.entity.ClassInfo;
import com.buybuyall.market.ui.ClassListActivity;
import com.buybuyall.market.utils.ToastUtil;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.CommonUtil;
import cn.common.utils.ViewUtil;

/**
 * 描述：分类页面
 * 作者：jake on 2016/1/13 22:31
 */
public class ClassAdapter extends BaseListAdapter<ClassInfo> {
    public ClassAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflate(R.layout.item_class);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(R.layout.item_class, holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassInfo info = (ClassInfo) v.getTag();
                    ClassListActivity.start(v.getContext(), info.getGcId(), info.getGcName());
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_class);
        }
        ClassInfo info = mDataList.get(position);
        if (info != null) {
            convertView.setTag(info);
            ImageLoader.getInstance().displayImage(info.getGcThumb(), holder.ivIcon);
            ViewUtil.setText2TextView(holder.tvName, info.getGcName());
        }
        return convertView;
    }


    final class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
    }
}