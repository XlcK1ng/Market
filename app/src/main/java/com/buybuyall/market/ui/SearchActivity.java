package com.buybuyall.market.ui;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.CommonFragmentPagerAdapter;
import com.buybuyall.market.fragment.BrandFragment;
import com.buybuyall.market.fragment.ClassFragment;
import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.ui.widgt.ChangeThemeUtils;
import cn.common.ui.widgt.indicator.IndicatorViewPager;
import cn.common.ui.widgt.pull.PullDragHelper;
import cn.common.ui.widgt.pull.PullListener;
import cn.common.ui.widgt.pull.PullToRefreshLayout;
import cn.common.ui.widgt.pull.PullableListView;
import cn.common.utils.DisplayUtil;

public class SearchActivity extends CommonTitleActivity {
    private EditText evSearch;
    private IndicatorViewPager indicatorView;
    private View vSearchResult;
    private View vNoResult;
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView lvResult;

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
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pl_result);
        lvResult = new PullableListView(this);
        pullToRefreshLayout.setContentView(lvResult);
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
        vSearchResult.setVisibility(View.GONE);
        indicatorView.setVisibility(View.VISIBLE);
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        pullToRefreshLayout.setPullListener(new PullListener() {
            @Override
            public void onLoadMore(PullDragHelper pullDragHelper) {

            }

            @Override
            public void onRefresh(PullDragHelper pullDragHelper) {

            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        evSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    ToastUtil.show("搜索");
                }
                return true;
            }
        });
    }

}
