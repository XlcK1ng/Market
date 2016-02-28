package com.buybuyall.market.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.buybuyall.market.R;

import cn.common.ui.widgt.ResizeLinearLayout;

/**
 * 描述：
 * 作者：jake on 2016/2/28 12:39
 */
public class MineResizeLinearLayout extends ResizeLinearLayout {
    public MineResizeLinearLayout(Context context) {
        super(context);
    }

    public MineResizeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (height == 0) {
            height = getMeasuredHeight()+(int)(getResources().getDimension(R.dimen.main_bottom_menu_height));
        }
        super.onLayout(changed, l, t, r, b);
    }
}
