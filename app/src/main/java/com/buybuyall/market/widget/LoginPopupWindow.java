
package com.buybuyall.market.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.buybuyall.market.R;

import cn.common.ui.helper.PopupWindowHelper;
import cn.common.utils.DisplayUtil;

/**
 * 描述:
 *
 * @author jakechen
 * @since 2016/1/28 17:54
 */
public class LoginPopupWindow {
    private PopupWindowHelper helper;

    private Context mContext;

    public LoginPopupWindow(Context context) {
        mContext = context;
        helper = new PopupWindowHelper(context, R.style.slide_bottom_top_animation);
        helper.setWidth(DisplayUtil.getSreenDimens().x);
        helper.setView(R.layout.pop_login);
    }

    public void show(View root) {
        helper.showAtLocation(root, Gravity.BOTTOM, 0, 0);
    }
    public void show(View root,int offY) {
        helper.showAtLocation(root, Gravity.BOTTOM, 0, -offY);
    }
}
