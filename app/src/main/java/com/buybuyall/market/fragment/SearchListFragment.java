
package com.buybuyall.market.fragment;

import com.buybuyall.market.adapter.GoodsSearchAdapter;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.GoodsListResponse;

import java.util.ArrayList;

import cn.common.exception.AppException;
import cn.common.ui.adapter.BaseListAdapter;

/**
 * 描述:分类列表
 *
 * @author jakechen
 * @since 2016/1/20 10:39
 */
public class SearchListFragment extends BasePullListFragment<GoodsInfo> {
    public static final int TYPE_GC = 1;

    public static final int TYPE_BRAND = 2;

    public static final int TYPE_COUNTRY_STORE = 3;

    public static final int ORDER_UP = 1;

    public static final int ORDER_DOWN = 2;

    public static final int KEY_NEW = 4;

    public static final int KEY_SALES = 1;

    public static final int KEY_PRIZE = 3;

    private String id;

    private int order = ORDER_DOWN;

    private int key = KEY_NEW;

    private int xianshi = 0;

    private int groupbuy = 0;

    private String typeIdKey = "gc_id";

    public void setType(int type) {
        switch (type) {
            case TYPE_GC:
                typeIdKey = "gc_id";
                break;
            case TYPE_BRAND:
                typeIdKey = "brand_id";
                break;
            case TYPE_COUNTRY_STORE:
                typeIdKey = "country_store_id";
                break;
        }

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setXianshi(int xianshi) {
        this.xianshi = xianshi;
    }

    public void setGroupbuy(int groupbuy) {
        this.groupbuy = groupbuy;
    }

    @Override
    protected BaseListAdapter<GoodsInfo> createAdapter() {
        return new GoodsSearchAdapter(getActivity());
    }

    @Override
    protected ArrayList<GoodsInfo> loadData(int pageIndex, int pageSize) {
        HttpRequest<GoodsListResponse> request = new HttpRequest<>(UrlManager.SEARCH,
                GoodsListResponse.class);
        request.setIsGet(true);
        request.addParam(typeIdKey, id);
        request.addParam("key", "" + key);
        request.addParam("order", "" + order);
        request.addParam("xianshi", "" + xianshi);
        request.addParam("groupbuy", "" + groupbuy);
        request.addParam("page", "1");
        request.addParam("curpage", "" + pageIndex);
        try {
            GoodsListResponse response = request.request();
            if (response != null) {
                setHasMore(response.isHasMore());
                if (response.isOk()) {
                    if (response.getList() != null && response.getList().size() > 0) {
                        return response.getList();
                    } else {
                        return new ArrayList<GoodsInfo>();
                    }
                }
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        return null;
    }
}
