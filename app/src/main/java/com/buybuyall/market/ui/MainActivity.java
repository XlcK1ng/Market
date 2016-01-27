
package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.buybuyall.market.MarketApplication;
import com.buybuyall.market.R;
import com.buybuyall.market.adapter.CommonFragmentPagerAdapter;
import com.buybuyall.market.fragment.MineFragment;
import com.buybuyall.market.fragment.PartyFragment;
import com.buybuyall.market.fragment.HomeFragment;
import com.buybuyall.market.logic.BroadcastActions;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.common.ui.activity.BaseWorkerFragmentActivity;
import cn.common.ui.widgt.ChangeThemeUtils;
import cn.common.ui.widgt.MainTabViewPager;
import cn.common.ui.widgt.TabRadioGroup;

public class MainActivity extends BaseWorkerFragmentActivity implements
        ViewPager.OnPageChangeListener, TabRadioGroup.OnCheckedChangeListener {
    private static final int MSG_UI_INIT_DATA = 0;

    private MainTabViewPager vpContent;

    private TabRadioGroup rgMenu;

    private RadioButton rbHome;

    private RadioButton rbGroup;

    private RadioButton rbMine;

    private RadioButton rbTrolley;

    private boolean hasSetChecked = false;

    private long lastClickTime;

    private TextView tvTitle;

    private ImageView ivSearch;

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
        String show = "当前为测试环境";
        if (!UrlManager.isTest) {
            show = "当前为正式环境";
        }
        ToastUtil.show(show);
        // vpContent.setCanScroll(false);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivSearch = (ImageView) findViewById(R.id.iv_right);
        vpContent = (MainTabViewPager) findViewById(R.id.vp_content);
        rgMenu = (TabRadioGroup) findViewById(R.id.rg_menu);
        rbHome = (RadioButton) findViewById(R.id.rb_menu_home);
        rbTrolley = (RadioButton) findViewById(R.id.rb_menu_trolley);
        rbGroup = (RadioButton) findViewById(R.id.rb_menu_group);
        rbMine = (RadioButton) findViewById(R.id.rb_menu_mine);
        vpContent.setOffscreenPageLimit(4);
        ChangeThemeUtils.adjustStatusBar(findViewById(R.id.iv_status_bar), this);

    }

    private void initEvent() {
        vpContent.setOnPageChangeListener(this);
        rgMenu.setOnCheckedChangeListener(this);
        findViewById(R.id.iv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goActivity(SearchActivity.class);
            }
        });
    }

    private void initData() {
        ArrayList<Fragment> list = new ArrayList<Fragment>();
        list.add(HomeFragment.newInstance());
        list.add(PartyFragment.newInstance());
        list.add(HomeFragment.newInstance());
        list.add(MineFragment.newInstance());
        vpContent.setAdapter(new CommonFragmentPagerAdapter(getSupportFragmentManager(), list));
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

    @Override
    public void onPageScrollStateChanged(int status) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        changeMenu(-1);
    }

    @Override
    public void onCheckedChanged(TabRadioGroup group, int checkedId) {
        changeMenu(checkedId);
    }

    private void changeMenu(int checkedId) {
        if (hasSetChecked) {
            hasSetChecked = false;
        } else {
            hasSetChecked = true;
            int id = getMenuId(checkedId);
            if (checkedId > 0) {
                vpContent.setCurrentItem(id, false);
            } else {
                rgMenu.check(id);
            }
            changeTitle(id);
        }
    }

    private void changeTitle(int id) {
        switch (id) {
            case 0:
            case R.id.rb_menu_home:
                tvTitle.setText("首页");
                ivSearch.setVisibility(View.VISIBLE);
                break;
            case 1:
            case R.id.rb_menu_group:
                tvTitle.setText("团购");
                ivSearch.setVisibility(View.GONE);
                break;
            case 2:
            case R.id.rb_menu_trolley:
                tvTitle.setText("购物车");
                ivSearch.setVisibility(View.GONE);
                break;
            case 3:
            case R.id.rb_menu_mine:
                tvTitle.setText("我的");
                ivSearch.setVisibility(View.GONE);
                break;
        }
    }

    private int getMenuId(int checkedId) {
        int id = checkedId > 0 ? checkedId : 0;
        if (checkedId > 0) {
            switch (checkedId) {
                case R.id.rb_menu_home:
                    id = 0;
                    break;
                case R.id.rb_menu_group:
                    id = 1;
                    break;
                case R.id.rb_menu_trolley:
                    id = 2;
                    break;
                case R.id.rb_menu_mine:
                    id = 3;
                    break;
            }
        } else {
            switch (vpContent.getCurrentItem()) {
                case 0:
                    id = R.id.rb_menu_home;
                    break;
                case 1:
                    id = R.id.rb_menu_group;
                    break;
                case 2:
                    id = R.id.rb_menu_trolley;
                    break;
                case 3:
                    id = R.id.rb_menu_mine;
                    break;
            }
        }
        return id;
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
}
