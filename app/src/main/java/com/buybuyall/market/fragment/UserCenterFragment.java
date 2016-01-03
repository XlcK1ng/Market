package com.buybuyall.market.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.utils.ToastUtil;

import cn.common.ui.widgt.RoundImageView;

/**
 * 描述：我的页面
 * 作者：jake on 2016/1/2 19:41
 */
public class UserCenterFragment extends StateFragment implements View.OnClickListener {
    public static UserCenterFragment newInstance() {
        return new UserCenterFragment();
    }

    private View vTopBg;
    private Button btnLogin;
    private RoundImageView rivAvatar;
    private TextView tvName;
    private TextView tvHelp;
    private View vTabUserCenter;
    private View vTabHasBuyGoods;

    private View vLineTabUserCenter;
    private View vLineTabHasBuyGoods;
    private View vUserCenter;
    private ListView lvHasBuyGoods;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_user_center);
        vTopBg = findViewById(R.id.fl_top_bg);
        btnLogin = (Button) findViewById(R.id.btn_login);
        rivAvatar = (RoundImageView) findViewById(R.id.riv_avatar);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHelp = (TextView) findViewById(R.id.tv_help);
        vTabHasBuyGoods = findViewById(R.id.ll_tab_has_buy_goods);
        vTabUserCenter = findViewById(R.id.ll_tab_user_center);
        vLineTabUserCenter = findViewById(R.id.line_user_center);
        vLineTabHasBuyGoods = findViewById(R.id.line_has_buy_goods);
        vUserCenter = findViewById(R.id.ll_user_center);
        lvHasBuyGoods = (ListView) findViewById(R.id.lv_has_buy_goods);
    }

    @Override
    protected void initData() {
        super.initData();
        tvHelp.setText("帮助&反馈");
        showTab(true);
    }

    private void showTab(boolean isShowUserCenter) {
        vUserCenter.setVisibility(isShowUserCenter ? View.VISIBLE : View.GONE);
        vLineTabUserCenter.setVisibility(isShowUserCenter ? View.VISIBLE : View.GONE);
        vLineTabHasBuyGoods.setVisibility(isShowUserCenter ? View.GONE : View.VISIBLE);
        vTabHasBuyGoods.setSelected(!isShowUserCenter);
        vTabUserCenter.setSelected(isShowUserCenter);
        lvHasBuyGoods.setVisibility(isShowUserCenter ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        vTabHasBuyGoods.setOnClickListener(this);
        vTabUserCenter.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
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
            case R.id.btn_login:
                break;
            case R.id.riv_avatar:
                break;
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
            case R.id.ll_tab_has_buy_goods:
                showTab(false);
                break;
            case R.id.ll_tab_user_center:
                showTab(true);
                break;
        }
        ToastUtil.show("id " + id);
    }
}
