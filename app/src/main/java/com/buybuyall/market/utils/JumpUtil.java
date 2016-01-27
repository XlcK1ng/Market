package com.buybuyall.market.utils;

import android.content.Context;

import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.ui.GoodsDetailActivity;
import com.buybuyall.market.ui.SpecialActivity;
import com.buybuyall.market.ui.StateActivity;
import com.buybuyall.market.ui.VenuesListActivity;

/**
 * 描述：
 * 作者：jake on 2016/1/27 23:08
 */
public final class JumpUtil {
    private JumpUtil() {
    }

    public static void advJump(Context context, AdvInfo info) {
        if (context == null || info == null) {
            return;
        }
//        link_type 广告链接类型：0无链接，1国家馆页，2商品详情页，3专题页，4套装列表
        switch (info.getLinkType()) {
            case 1:
                if (info.getParamMap() != null) {
                    VenuesListActivity.start(context, info.getParamMap().get("country_store_id"), info.getParamMap().get("key"));
                } else {
                    ToastUtil.show("本馆尚未开发，去其他场馆看看");
                }
                break;
            case 2:
                if (info.getParamMap() != null) {
                    long goodsId = 0;
                    try {
                        goodsId = Integer.valueOf(info.getParamMap().get("goods_id"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    GoodsDetailActivity.start(context, goodsId);
                }
                break;
            case 3:
                if (info.getParamMap() != null) {
                    long goodsId = 0;
                    try {
                        goodsId = Long.valueOf(info.getParamMap().get("special_id"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SpecialActivity.start(context, goodsId);
                }
                break;
        }
    }
}
