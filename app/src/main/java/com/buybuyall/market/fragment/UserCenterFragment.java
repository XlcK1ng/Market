package com.buybuyall.market.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.CommonFragmentPagerAdapter;
import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.ui.widgt.RoundImageView;
import cn.common.ui.widgt.indicator.IndicatorViewPager;
import cn.common.utils.DisplayUtil;

/**
 * 描述：我的页面
 * 作者：jake on 2016/1/2 19:41
 */
public class UserCenterFragment extends StateFragment implements View.OnClickListener {
    public static UserCenterFragment newInstance() {
        return new UserCenterFragment();
    }

    private TextView tvHelp;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_user_center);
        tvHelp = (TextView) findViewById(R.id.tv_help);
    }

    @Override
    protected void initData() {
        super.initData();
        tvHelp.setText("帮助&反馈");

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.civ_my_order).setOnClickListener(this);
        findViewById(R.id.civ_my_group).setOnClickListener(this);
        findViewById(R.id.civ_coupon).setOnClickListener(this);
        findViewById(R.id.civ_contact_us).setOnClickListener(this);
        findViewById(R.id.civ_my_address).setOnClickListener(this);
        findViewById(R.id.civ_question).setOnClickListener(this);
    }

    @Override
    public void reLoadData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.civ_my_order:
                break;
            case R.id.civ_my_group:
                break;
            case R.id.civ_coupon:
                break;
            case R.id.civ_contact_us:
                break;
            case R.id.civ_my_address:
                break;
            case R.id.civ_question:
                break;
        }
        ToastUtil.show("id " + id);
    }
}
