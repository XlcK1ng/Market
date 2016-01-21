
package com.buybuyall.market.ui;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.CommonFragmentPagerAdapter;
import com.buybuyall.market.fragment.BrandFragment;
import com.buybuyall.market.fragment.ClassFragment;
import com.buybuyall.market.fragment.SearchResultFragment;
import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.ui.widgt.ChangeThemeUtils;
import cn.common.ui.widgt.indicator.IndicatorViewPager;
import cn.common.utils.DisplayUtil;
import cn.common.utils.ViewUtil;

public class SearchActivity extends CommonTitleActivity {

    private EditText evSearch;

    private IndicatorViewPager indicatorView;

    private View vSearchResult;

    private SearchResultFragment resultFragment;

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
        indicatorView.getViewPager().setAdapter(
                new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        indicatorView.setDividerHeight(1);
        indicatorView.setDividerColor(getColor(R.color.gray_dfdfdf));
        ViewUtil.setViewVisibility(vSearchResult, View.GONE);
        ViewUtil.setViewVisibility(indicatorView, View.VISIBLE);
        resultFragment = new SearchResultFragment();
        resultFragment.setNoResult(findViewById(R.id.ll_no_result));
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_result, resultFragment)
                .commitAllowingStateLoss();
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
                        ViewUtil.setViewVisibility(indicatorView,View.GONE);
                        ViewUtil.setViewVisibility(vSearchResult,View.VISIBLE);
                        resultFragment.setKeyWord(evSearch.getText().toString());
                    } else {
                        ToastUtil.show("请输入搜索的关键字");
                    }
                }
                return false;
            }
        });
    }
}
