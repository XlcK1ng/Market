package com.buybuyall.market.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.CommonFragmentPagerAdapter;
import com.buybuyall.market.widget.LoginView;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.ui.widgt.RoundImageView;
import cn.common.ui.widgt.indicator.IndicatorViewPager;
import cn.common.utils.CommonUtil;
import cn.common.utils.DisplayUtil;

/**
 * 描述：我的页面 作者：jake on 2016/1/2 19:41
 */
public class MineFragment extends StateFragment implements View.OnClickListener {
  public static MineFragment newInstance() {
    return new MineFragment();
  }
  private boolean isHidingLoginView = false;
  private Button btnLogin;

  private RoundImageView rivAvatar;

  private TextView tvName;

  private IndicatorViewPager indicatorView;
  private View loginLayout;

  @Override
  protected void initView() {
    setContentView(R.layout.fragment_mine);
    loginLayout = findViewById(R.id.login);
    loginLayout.setVisibility(View.INVISIBLE);
    loginView = (LoginView) findViewById(R.id.login_view);
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
    indicatorView.getViewPager().setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
  }

  @Override
  protected void initData() {
    super.initData();
  }

  @Override
  protected void initEvent() {
    super.initEvent();
    btnLogin.setOnClickListener(this);
//        findViewById(R.id.v_login).setOnClickListener(this);
  }

  @Override
  public void reLoadData() {
  }

  private LoginView loginView;

  @Override
  public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      case R.id.btn_login:
        showLoginView();
        break;
      case R.id.riv_avatar:
        break;
      case R.id.v_login:
        hideLoginView();
        break;
    }
  }

  private void showLoginView() {
    TranslateAnimation animation = new TranslateAnimation(0, 0, loginView.getHeight(), 0);
    animation.setDuration(300);
    animation.setInterpolator(new LinearInterpolator());
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {
        loginView.setVisibility(View.VISIBLE);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }
    });
    loginView.clearAnimation();
    loginView.startAnimation(animation);
    loginLayout.setVisibility(View.VISIBLE);
  }

  private void hideLoginView() {
    if (!isHidingLoginView) {
      TranslateAnimation animation = new TranslateAnimation(0, 0, 0, loginView.getHeight());
      animation.setDuration(300);
      animation.setInterpolator(new LinearInterpolator());
      animation.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
          isHidingLoginView = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
          loginView.setVisibility(View.GONE);
          loginLayout.setVisibility(View.GONE);
          isHidingLoginView = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
      });
      loginView.clearAnimation();
      loginView.startAnimation(animation);
      CommonUtil.hideSoftInput(getActivity());
    }
  }



  public boolean onKeyBack() {
    if (loginLayout.getVisibility() == View.VISIBLE) {
      hideLoginView();
      return true;
    }
    return false;

  }
}
