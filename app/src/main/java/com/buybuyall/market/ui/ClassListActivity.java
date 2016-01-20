
package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.ChildClassAdapter;
import com.buybuyall.market.entity.ClassInfo;
import com.buybuyall.market.fragment.ClassListFragment;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.ClassListResponse;
import com.buybuyall.market.widget.SelectBarView;

import cn.common.exception.AppException;
import cn.common.ui.widgt.HorizontalScrollGridView;
import cn.common.utils.DisplayUtil;
import cn.common.utils.ViewUtil;

/**
 * 描述：分类列表 作者：jake on 2016/1/17 22:19
 */
public class ClassListActivity extends StateActivity {
    private static final int MSG_BACK_LOAD_DATA = 0;

    private static final int MSG_UI_LOAD_SUCCESS = 0;

    private static final int MSG_UI_LOAD_FINISH = 1;

    private static final String KEY_TITLE = "key_title";

    private static final String KEY_ID = "key_id";

    private HorizontalScrollGridView hsgvTop;

    private SelectBarView selectBarView;


    private ChildClassAdapter mChildClassAdapter;

    private ClassListFragment fragment;

    private long classId = 0;

    private String title = "分类列表";

    public static void start(Context context, long classId, String className) {
        Intent it = new Intent(context, ClassListActivity.class);
        it.putExtra(KEY_ID, classId);
        it.putExtra(KEY_TITLE, className);
        context.startActivity(it);
    }

    @Override
    protected void dealIntent(Bundle data) {
        super.dealIntent(data);
        if (data != null) {
            title = data.getString(KEY_TITLE, "分类列表");
            classId = data.getLong(KEY_ID, 0);
        }
    }

    @Override
    protected void initView() {
        setTitle(title);
        setContentView(R.layout.activtiy_class_list);
        hsgvTop = (HorizontalScrollGridView) findViewById(R.id.hsgv_adv);
        selectBarView = (SelectBarView) findViewById(R.id.select_bar);
        fragment = new ClassListFragment();
        mChildClassAdapter = new ChildClassAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassInfo info = (ClassInfo) v.getTag();
                if (info != null) {
                    fragment.setGcId(info.getGcId());
                }
            }
        });
        hsgvTop.setColumnWidth(DisplayUtil.getSreenDimens().x / 4);
        hsgvTop.setAdapter(mChildClassAdapter);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment)
                .commitAllowingStateLoss();
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
        showLoadingView();
    }

    @Override
    public void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
        switch (msg.what) {
            case MSG_BACK_LOAD_DATA:
                loadClassListTask();
                break;
        }
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_SUCCESS:
                ViewUtil.setViewVisibility(hsgvTop, View.VISIBLE);
                mChildClassAdapter.notifyDataSetChanged();
                hsgvTop.notifyDataSetChanged();
                ClassInfo info = (ClassInfo) mChildClassAdapter.getItem(0);
                if (info != null) {
                    fragment.setGcId(info.getGcId());
                }
                break;
            case MSG_UI_LOAD_FINISH:
                showContentView();
                break;
        }
    }

    private void loadClassListTask() {
        HttpRequest<ClassListResponse> request = new HttpRequest<>(UrlManager.GET_CHILD_CLASS_LIST,
                ClassListResponse.class);
        request.addParam("gc_id", "" + classId);
        request.setIsGet(true);
        try {
            ClassListResponse response = request.request();
            if (response != null && response.getList() != null && response.getList().size() > 0) {
                mChildClassAdapter.setData(response.getList());
                sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        sendEmptyUiMessage(MSG_UI_LOAD_FINISH);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        selectBarView.setListener(new SelectBarView.IListener() {
            @Override
            public void selectNew(int sortType) {
                setSortType(sortType);
                fragment.setKey(ClassListFragment.KEY_NEW);
                fragment.setOrder(ClassListFragment.ORDER_DOWN);
                fragment.reLoadData();

            }

            @Override
            public void selectPrizeUp(int sortType) {
                fragment.setKey(ClassListFragment.KEY_PRIZE);
                fragment.setOrder(ClassListFragment.ORDER_UP);
                setSortType(sortType);
                fragment.reLoadData();
            }

            @Override
            public void selectPrizeDown(int sortType) {
                setSortType(sortType);
                fragment.setKey(ClassListFragment.KEY_PRIZE);
                fragment.setOrder(ClassListFragment.ORDER_DOWN);
                fragment.reLoadData();
            }

            @Override
            public void selectSales(int sortType) {
                fragment.setKey(ClassListFragment.KEY_SALES);
                fragment.setOrder(ClassListFragment.ORDER_DOWN);
                setSortType(sortType);
                fragment.reLoadData();
            }
        });
    }

    private void setSortType(int sortType) {
        switch (sortType) {
            case SelectBarView.SORT_DISCOUNT:
                fragment.setXianshi(1);
                fragment.setGroupbuy(0);
                break;
            case SelectBarView.SORT_RUSH_BUY:
                fragment.setXianshi(0);
                fragment.setGroupbuy(1);
                break;
            case SelectBarView.SORT_DEFAULT:
            default:
                fragment.setXianshi(0);
                fragment.setGroupbuy(0);
                break;
        }
    }

    @Override
    public void reLoadData() {
    }
}
