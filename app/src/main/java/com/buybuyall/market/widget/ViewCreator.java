package com.buybuyall.market.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

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
}
