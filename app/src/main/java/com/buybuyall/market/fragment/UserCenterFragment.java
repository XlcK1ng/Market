package com.buybuyall.market.fragment;

import android.view.View;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.ui.WebActivity;
import com.buybuyall.market.utils.ToastUtil;

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
        showContentView();
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
                WebActivity.start(v.getContext(),"联系我们","http://www.baidu.com/");
                break;
            case R.id.civ_my_address:
                break;
            case R.id.civ_question:
                WebActivity.start(v.getContext(),"关于百百购","http://www.mi.com/");
                break;
        }
        ToastUtil.show("id " + id);
    }
}
