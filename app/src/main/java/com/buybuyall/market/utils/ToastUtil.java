
package com.buybuyall.market.utils;

import cn.common.utils.BaseToastUtil;

/**
 * 描述：用于显示toast的工具
 *
 * @author Created by jakechen on 2015/8/11.
 */
public final class ToastUtil extends BaseToastUtil {
    public static void showError() {
        show("网络异常");
    }
}
