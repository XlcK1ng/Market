package com.buybuyall.market.ui;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.CommonFragmentPagerAdapter;
import com.buybuyall.market.adapter.GoodsSearchAdapter;
import com.buybuyall.market.fragment.BrandFragment;
import com.buybuyall.market.fragment.ClassFragment;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.GoodsListResponse;
import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.exception.AppException;
import cn.common.ui.widgt.ChangeThemeUtils;
import cn.common.ui.widgt.indicator.IndicatorViewPager;
import cn.common.ui.widgt.pull.PullDragHelper;
import cn.common.ui.widgt.pull.PullListener;
import cn.common.utils.DisplayUtil;
import cn.common.utils.ViewUtil;

public class SearchActivity extends CommonTitleActivity {
    private static final int MSG_BACK_SEARCH = 0;
    private static final int MSG_UI_SEARCH_FAIL = 0;
    private static final int MSG_UI_SEARCH_SUCCESS = 1;
    private EditText evSearch;
    private IndicatorViewPager indicatorView;
    private View vSearchResult;
    private View vNoResult;
    private ListView lvResult;
    private GoodsSearchAdapter resultAdapter;

    @Override
    protected boolean hasTitle() {
        return false;
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_search);
        ChangeThemeUtils.adjustStatusBar(findViewById(R.id.iv_status_bar), this);
        evSearch = (EditText) findViewById(R.id.ev_search);
        vSearchResult = findViewById(R.id.ll_result);
        vNoResult = findViewById(R.id.ll_no_result);
        lvResult = (ListView) findViewById(R.id.lv_result);
        lvResult.addHeaderView(new View(this));
        FrameLayout layout = (FrameLayout) findViewById(R.id.fl_content);
        indicatorView = ViewCreator.createIndicatorViewPager(this);
        layout.addView(indicatorView, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        indicatorView.setTabHeight(DisplayUtil.dip(50));
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("分类");
        tabs.add("品牌");
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ClassFragment.newInstance());
        fragmentList.add(BrandFragment.newInstance());
        indicatorView.getIndicator().setTabList(tabs);
        indicatorView.getViewPager().setAdapter(new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        indicatorView.setDividerHeight(DisplayUtil.dip(1));
        indicatorView.setDividerColor(getColor(R.color.gray_dfdfdf));
        ViewUtil.setViewVisibility(vSearchResult, View.GONE);
        ViewUtil.setViewVisibility(indicatorView, View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        resultAdapter = new GoodsSearchAdapter(this);
        lvResult.setAdapter(resultAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        evSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (!TextUtils.isEmpty(evSearch.getText())) {
                        sendEmptyBackgroundMessage(MSG_BACK_SEARCH);
                    } else {
                        ToastUtil.show("请输入搜索的关键字");
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_SEARCH_FAIL:
                ToastUtil.show("网络异常，请重试");
                break;
            case MSG_UI_SEARCH_SUCCESS:
                ViewUtil.setViewVisibility(vSearchResult, View.VISIBLE);
                ViewUtil.setViewVisibility(indicatorView, View.GONE);
                if (msg.arg1 <= 0) {
                    ViewUtil.setViewVisibility(vNoResult, View.VISIBLE);
                }
                if (resultAdapter != null) {
                    resultAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
        switch (msg.what) {
            case MSG_BACK_SEARCH:
                searchTask();
                break;
        }
    }

    private void searchTask() {
        HttpRequest<GoodsListResponse> request = new HttpRequest<>(UrlManager.SEARCH, GoodsListResponse.class);
        request.setIsGet(true);
        request.addParam("keyword", evSearch.getText().toString());
        try {
            GoodsListResponse response = request.request();
            if (response != null && response.getList() != null && response.getList().size() > 0) {
                resultAdapter.setData(response.getList());
                Message uiMsg = obtainUiMessage();
                uiMsg.what = MSG_UI_SEARCH_SUCCESS;
                uiMsg.arg1 = response.getCount();
                uiMsg.sendToTarget();
            } else {
                sendEmptyUiMessage(MSG_UI_SEARCH_FAIL);
            }
        } catch (AppException e) {
            e.printStackTrace();
            sendEmptyUiMessage(MSG_UI_SEARCH_FAIL);
        }
    }
}
