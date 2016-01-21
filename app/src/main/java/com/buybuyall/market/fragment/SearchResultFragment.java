
package com.buybuyall.market.fragment;

import android.os.Message;
import android.view.View;

import com.buybuyall.market.adapter.GoodsSearchAdapter;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.GoodsListResponse;

import java.util.ArrayList;

import cn.common.exception.AppException;
import cn.common.ui.adapter.BaseListAdapter;
import cn.common.utils.ViewUtil;

/**
 * 描述:搜索结果列表
 *
 * @author jakechen
 * @since 2016/1/20 10:39
 */
public class SearchResultFragment extends BasePullListFragment<GoodsInfo> {
    private static final int MSG_UI_NO_RESULT = 0;

    private static final int MSG_UI_HAVA_RESULT = 1;

    private String keyWord;

    private View noResult;

    public void setNoResult(View noResult) {
        this.noResult = noResult;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
        reLoadData();
    }

    @Override
    protected BaseListAdapter<GoodsInfo> createAdapter() {
        return new GoodsSearchAdapter(getActivity());
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_NO_RESULT:
                ViewUtil.setViewVisibility(noResult, View.VISIBLE);
                break;
            case MSG_UI_HAVA_RESULT:
                ViewUtil.setViewVisibility(noResult, View.GONE);
                break;
        }
    }

    @Override
    protected ArrayList<GoodsInfo> loadData(int pageIndex, int pageSize) {
        HttpRequest<GoodsListResponse> request = new HttpRequest<>(UrlManager.SEARCH,
                GoodsListResponse.class);
        request.setIsGet(true);
        request.addParam("keyword", keyWord);
        request.addParam("order", "1");
        request.addParam("xianshi", "0");
        request.addParam("groupbuy", "0");
        request.addParam("page", "1");
        request.addParam("curpage", "" + pageIndex);
        try {
            GoodsListResponse response = request.request();
            if (response != null) {
                setHasMore(response.isHasMore());
                if (pageIndex == PAGE_START) {
                    if (response.getCount() < 1) {
                        sendEmptyUiMessage(MSG_UI_NO_RESULT);
                    } else {
                        sendEmptyUiMessage(MSG_UI_HAVA_RESULT);
                    }
                }
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
