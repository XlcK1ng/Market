package com.buybuyall.market.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import com.buybuyall.market.R;

import cn.common.ui.widgt.indicator.IndicatorViewPager;
import cn.common.utils.DisplayUtil;

/**
 * 描述：用于创建公用的View
 * 作者：jake on 2015/12/29
 */
public final class ViewCreator {
    private ViewCreator() {
    }

    public static ListView createCommonListView(Context context) {
        ListView listView = new ListView(context);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        listView.setDividerHeight(0);
        return listView;
    }

    public static IndicatorViewPager createIndicatorViewPager(Context context) {
        if (context == null) {
            return null;
        }
        IndicatorViewPager indicatorView = new IndicatorViewPager(context);
        indicatorView.setTabHeight(DisplayUtil.dip(45));
        indicatorView.getIndicator().setAverage(false);
        indicatorView.getIndicator().setSelectLineHeight(DisplayUtil.dip(2));
        indicatorView.getIndicator().setShowNormalLine(false);
        indicatorView.getIndicator().setChangeTabColor(true);
        indicatorView.getIndicator().setLineMarginBottom(DisplayUtil.dip(8));
        indicatorView.getIndicator().setTabSelectColor(context.getResources().getColor(R.color.orange_d06048));
        indicatorView.getIndicator().setTextColor(context.getResources().getColor(R.color.black_363636));
        indicatorView.getIndicator().setTextSize(DisplayUtil.dip(14));
        return indicatorView;
    }
}
