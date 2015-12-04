
package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;

import com.buybuyall.market.MarketApplication;
import com.buybuyall.market.R;
import com.buybuyall.market.adapter.CommonFragmentPagerAdapter;
import com.buybuyall.market.fragment.HomeFragment;
import com.buybuyall.market.logic.BroadcastActions;
import com.buybuyall.market.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.common.ui.activity.BaseWorkerFragmentActivity;
import cn.common.ui.widgt.ChangeThemeUtils;
import cn.common.ui.widgt.MainTabViewPager;
import cn.common.ui.widgt.TabRadioGroup;

public class MainActivity extends BaseWorkerFragmentActivity
        implements ViewPager.OnPageChangeListener, TabRadioGroup.OnCheckedChangeListener {
    private static final int MSG_UI_INIT_DATA = 0;


    private MainTabViewPager vpContent;

    private TabRadioGroup rgMenu;

    private RadioButton rbHome;

    private RadioButton rbGroup;

    private RadioButton rbMine;

    private RadioButton rbHealth;


    private long lastClickTime;
    private View vStatus;

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastClickTime > 2000) {
            ToastUtil.show("再按一次退出");
            lastClickTime = now;
        } else {
            MarketApplication.getInstance().exitApp();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        sendEmptyUiMessage(MSG_UI_INIT_DATA);
        vpContent.setCanScroll(false);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
//        vStatus = findViewById(R.id.v_status);
        vpContent = (MainTabViewPager) findViewById(R.id.vp_content);
        rgMenu = (TabRadioGroup) findViewById(R.id.rg_menu);
        rbHome = (RadioButton) findViewById(R.id.rb_menu_home);
        rbHealth = (RadioButton) findViewById(R.id.rb_menu_trolley);
        rbGroup = (RadioButton) findViewById(R.id.rb_menu_group);
        rbMine = (RadioButton) findViewById(R.id.rb_menu_mine);
        vpContent.setOffscreenPageLimit(4);
        ChangeThemeUtils.adjustStatusBar(findViewById(R.id.v_title), this);

    }

    private void initEvent() {
        vpContent.setOnPageChangeListener(this);
        rgMenu.setOnCheckedChangeListener(this);
        findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goActivity(SearchActivity.class);
            }
        });
    }

    private void initData() {
        vpContent.setAdapter(
                new CommonFragmentPagerAdapter(getSupportFragmentManager(), getFragments()));
        //初始化设备信息
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_INIT_DATA:
                initData();
                break;
        }
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> list = new ArrayList<Fragment>();
        list.add(HomeFragment.newInstance());
        list.add(HomeFragment.newInstance());
        list.add(HomeFragment.newInstance());
        list.add(HomeFragment.newInstance());
        return list;
    }

    @Override
    public void onPageScrollStateChanged(int status) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (vpContent.getCurrentItem()) {
            case 0:
                rbHome.setChecked(true);
                break;
            case 1:
                rbGroup.setChecked(true);
                break;
            case 2:
                rbMine.setChecked(true);
                break;
            case 3:
                rbHealth.setChecked(true);
                break;
        }
    }

    @Override
    public void setupBroadcastActions(List<String> actionList) {
        super.setupBroadcastActions(actionList);
        actionList.add(BroadcastActions.ACTION_MAIN_ACTIVITY_SELECT_TAB_LOCATE);
    }

    @Override
    public void handleBroadcast(Context context, Intent intent) {
        super.handleBroadcast(context, intent);
        String action = intent.getAction();
        if (TextUtils.equals(action, BroadcastActions.ACTION_MAIN_ACTIVITY_SELECT_TAB_LOCATE)) {
            if (vpContent != null) {
                vpContent.setCurrentItem(0, false);
            }
        }
    }

    @Override
    public void onCheckedChanged(TabRadioGroup group, int checkedId) {
        int position = 0;
        switch (checkedId) {
            case R.id.rb_menu_home:
                position = 0;
                break;
            case R.id.rb_menu_group:
                position = 1;
                break;
            case R.id.rb_menu_trolley:
                position = 2;
                break;
            case R.id.rb_menu_mine:
                position = 3;
                break;

        }
        vpContent.setCurrentItem(position, false);
    }
}
