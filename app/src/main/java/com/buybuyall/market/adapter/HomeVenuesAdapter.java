
package com.buybuyall.market.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.utils.JumpUtil;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.adapter.BaseListAdapter;

/**
 * 描述：首页广告适配器 作者：jake on 2015/12/29 23:45
 */
public class HomeVenuesAdapter extends BaseListAdapter<AdvInfo> {
    public HomeVenuesAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflate(R.layout.item_venues);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(R.layout.item_venues, holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdvInfo info = (AdvInfo) v.getTag();
                    JumpUtil.advJump(v.getContext(), info);
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_venues);
        }
        AdvInfo info = mDataList.get(position);
        if (info != null) {
            convertView.setTag(info);
            ImageLoader.getInstance().displayImage(info.getAdvPic(), holder.ivIcon);
            // ViewUtil.setText2TextView(holder.tvName, info.getGcName());
        }
        return convertView;
    }

    final class ViewHolder {
        ImageView ivIcon;

        TextView tvName;
    }
}
