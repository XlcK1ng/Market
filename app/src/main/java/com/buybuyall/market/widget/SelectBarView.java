
package com.buybuyall.market.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.buybuyall.market.R;

import cn.common.ui.helper.PopupWindowHelper;
import cn.common.utils.ViewUtil;

/**
 * 描述：筛选的控件 作者：jake on 2016/1/17 21:11
 */
public class SelectBarView extends LinearLayout implements View.OnClickListener {
    public static final int SORT_DEFAULT = -1;

    public static final int SORT_RUSH_BUY = 1;

    public static final int SORT_DISCOUNT = 2;

    private TextView tvNew;

    private View vSort;

    private View vPrize;

    private TextView tvSales;

    private TextView tvPrize;

    private TextView tvHasSortName;

    private ImageView ivUp;

    private ImageView ivDown;

    private IListener listener;

    private int lastId;

    private int sortType;

    private View vHasSort;

    private PopupWindowHelper helper;

    private CheckBox cbRushBuy;

    private CheckBox cbDiscount;

    private TextView tvSortName;

    private View vTop;

    public interface IListener {
        void selectNew(int sortType);

        void selectPrizeUp(int sortType);

        void selectPrizeDown(int sortType);

        void selectSales(int sortType);
    }

    public void setListener(IListener listener) {
        this.listener = listener;
    }

    public SelectBarView(Context context) {
        this(context, null);
    }

    public SelectBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        inflate(context, R.layout.view_select_bar, this);
        tvNew = (TextView) findViewById(R.id.tv_new);
        tvSales = (TextView) findViewById(R.id.tv_sales);
        tvPrize = (TextView) findViewById(R.id.tv_prize);
        tvHasSortName = (TextView) findViewById(R.id.tv_sort_name);
        ivUp = (ImageView) findViewById(R.id.iv_up);
        ivDown = (ImageView) findViewById(R.id.iv_down);
        vSort = findViewById(R.id.fl_sort);
        vPrize = findViewById(R.id.fl_prize);
        vHasSort = findViewById(R.id.rl_sort);
        vPrize.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        tvSales.setOnClickListener(this);
        vSort.setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        changeState(0);
        sortType = SORT_DEFAULT;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_new) {
            changeState(0);
            click(0);
            lastId = 0;
        } else if (id == R.id.tv_sales) {
            changeState(2);
            lastId = 2;
            click(2);
        } else if (id == R.id.fl_prize) {
            changeState(1);
            lastId = 1;
            if (ivUp.isSelected()) {
                ivUp.setSelected(false);
                ivDown.setSelected(true);
            } else {
                ivUp.setSelected(true);
                ivDown.setSelected(false);
            }
            click(0);
        } else if (id == R.id.fl_sort) {
            changeState(3);
            if (helper == null) {
                initPoPupWindow();
            }
            helper.showAsDropDown(vSort);
        } else if (id == R.id.btn_ok) {
            hidePop();
            click(lastId);
            if (sortType != SORT_DEFAULT) {
                ViewUtil.setViewVisibility(vHasSort, VISIBLE);
            }
        } else if (id == R.id.v_shadow) {
            hidePop();
        } else if (id == R.id.btn_clear) {
            sortType = SORT_DEFAULT;
            ViewUtil.setViewVisibility(vHasSort, GONE);
            cbRushBuy.setChecked(false);
            cbDiscount.setChecked(false);
            click(lastId);
        }
    }

    private void hidePop() {
        if (helper != null) {
            helper.dismiss();
        }
    }

    /**
     * 初始化popup window
     */
    private void initPoPupWindow() {
        helper = new PopupWindowHelper(getContext());
        helper.setView(R.layout.pop_sort);
        helper.setMeasuredDimension(getWidth(), -2);
        tvSortName = (TextView) helper.findViewById(R.id.tv_sort_name);
        cbRushBuy = (CheckBox) helper.findViewById(R.id.cb_rush_buy);
        cbDiscount = (CheckBox) helper.findViewById(R.id.cb_discount);
        vTop = helper.findViewById(R.id.ll_top);
        helper.findViewById(R.id.v_shadow).setOnClickListener(this);
        helper.findViewById(R.id.btn_ok).setOnClickListener(this);
        helper.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (lastId == 1) {
                    ivUp.setSelected(true);
                }
                changeState(lastId);
            }
        });
        cbDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ViewUtil.setViewVisibility(vTop, VISIBLE);
                    cbRushBuy.setChecked(false);
                    tvSortName.setText("限时折扣");
                    tvHasSortName.setText("限时折扣");
                    sortType = SORT_DISCOUNT;
                } else {
                    if (!cbRushBuy.isChecked()) {
                        ViewUtil.setViewVisibility(vTop, GONE);
                    }
                }
            }
        });
        cbRushBuy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ViewUtil.setViewVisibility(vTop, VISIBLE);
                    cbDiscount.setChecked(false);
                    tvSortName.setText("抢购");
                    tvHasSortName.setText("抢购");
                    sortType = SORT_RUSH_BUY;
                } else {
                    if (!cbDiscount.isChecked()) {
                        ViewUtil.setViewVisibility(vTop, GONE);
                    }
                }
            }
        });
    }

    private void changeState(int id) {
        tvNew.setSelected(false);
        tvSales.setSelected(false);
        tvPrize.setSelected(false);
        vSort.setSelected(false);
        switch (id) {
            case 0:
                ivDown.setSelected(false);
                ivUp.setSelected(false);
                tvNew.setSelected(true);
                break;
            case 1:
                tvPrize.setSelected(true);
                break;
            case 2:
                ivDown.setSelected(false);
                ivUp.setSelected(false);
                tvSales.setSelected(true);
                break;
            case 3:
                ivDown.setSelected(false);
                ivUp.setSelected(false);
                vSort.setSelected(true);
                break;
        }

    }

    private void click(int id) {
        if (listener == null) {
            return;
        }
        switch (id) {
            case 0:
                listener.selectNew(sortType);
                break;
            case 1:
                if (ivUp.isSelected()) {
                    listener.selectPrizeUp(sortType);
                } else {
                    listener.selectPrizeDown(sortType);
                }
                break;
            case 2:
                listener.selectSales(sortType);
                break;
        }
    }

}
