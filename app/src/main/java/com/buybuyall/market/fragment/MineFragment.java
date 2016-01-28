
package com.buybuyall.market.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.CommonFragmentPagerAdapter;
import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.LoginPopupWindow;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.ui.widgt.RoundImageView;
import cn.common.ui.widgt.indicator.IndicatorViewPager;
import cn.common.utils.DisplayUtil;

/**
 * 描述：我的页面 作者：jake on 2016/1/2 19:41
 */
public class MineFragment extends StateFragment implements View.OnClickListener {
    public static MineFragment newInstance() {
        return new MineFragment();
    }

    private Button btnLogin;

    private RoundImageView rivAvatar;

    private TextView tvName;

    private IndicatorViewPager indicatorView;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_mine);
        btnLogin = (Button) findViewById(R.id.btn_login);
        rivAvatar = (RoundImageView) findViewById(R.id.riv_avatar);
        indicatorView = ViewCreator.createIndicatorViewPager(getActivity());
        indicatorView.getIndicator().setPadding(DisplayUtil.dip(30), 0, DisplayUtil.dip(30), 0);
        indicatorView.getIndicator().setBackgroundColor(Color.WHITE);
        FrameLayout layout = (FrameLayout) findViewById(R.id.fl_content);
        layout.addView(indicatorView, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        ArrayList<String> tabList = new ArrayList<>();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(PurchasedGoodsFragment.newInstance());
        fragmentList.add(UserCenterFragment.newInstance());
        tabList.add("已购商品");
        tabList.add("个人中心");
        indicatorView.getIndicator().setTabList(tabList);
        indicatorView.getViewPager().setAdapter(
                new CommonFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void reLoadData() {

    }

    private LoginPopupWindow mLoginPopupWindow;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                if (mLoginPopupWindow == null) {
                    mLoginPopupWindow = new LoginPopupWindow(v.getContext());
                }
                mLoginPopupWindow.show(mStatusView,DisplayUtil.dip(49));
                break;
            case R.id.riv_avatar:
                break;
        }
        ToastUtil.show("id " + id);
    }
}
