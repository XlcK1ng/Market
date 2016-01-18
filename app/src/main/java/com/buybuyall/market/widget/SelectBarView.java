
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
    private TextView tvNew;

    private View vSort;

    private View vPrize;

    private TextView tvSales;

    private TextView tvPrize;

    private ImageView ivUp;

    private ImageView ivDown;

    private IListener listener;

    private int lastId;

    private boolean lastIsUp = false;

    public interface IListener {
        void selectNew();

        void selectPrizeUp();

        void selectPrizeDown();

        void selectSales();

        void sort(boolean isRushBuy);
    }

    public void setListener(IListener listener) {
        this.listener = listener;
    }

    public SelectBarView(Context context) {
        this(context, null);
    }

    public SelectBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        inflate(context, R.layout.view_select_bar, this);
        tvNew = (TextView) findViewById(R.id.tv_new);
        tvSales = (TextView) findViewById(R.id.tv_sales);
        vSort = findViewById(R.id.fl_sort);
        vPrize = findViewById(R.id.fl_prize);
        ivUp = (ImageView) findViewById(R.id.iv_up);
        ivDown = (ImageView) findViewById(R.id.iv_down);
        vPrize = findViewById(R.id.fl_prize);
        tvPrize = (TextView) findViewById(R.id.tv_prize);
        vPrize.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        tvSales.setOnClickListener(this);
        vSort.setOnClickListener(this);
        showSelectState(0);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_new) {
            showSelectState(0);
            lastId = 0;
            if (listener != null) {
                listener.selectNew();
            }
        } else if (id == R.id.tv_sales) {
            showSelectState(2);
            lastId = 2;
            if (listener != null) {
                listener.selectSales();
            }
        } else if (id == R.id.fl_prize) {
            showSelectState(1);
            lastId = 1;
            if (lastIsUp) {
                if (listener != null) {
                    listener.selectPrizeUp();
                }
                ivUp.setSelected(true);
                ivDown.setSelected(false);
                lastIsUp = false;
            } else {
                if (listener != null) {
                    listener.selectPrizeDown();
                }
                ivUp.setSelected(false);
                ivDown.setSelected(true);
                lastIsUp = true;
            }
        } else if (id == R.id.fl_sort) {
            showSelectState(3);
            if (helper == null) {
                initPoPupWindow();
            }
            helper.showAsDropDown(vSort);
        }
    }

    private CheckBox cbRushBuy;

    private CheckBox cbDiscount;

    private TextView tvSortName;

    private View vTop;

    /**
     * 初始化popup window
     */
    private void initPoPupWindow() {
        helper = new PopupWindowHelper(getContext());
        helper.setView(R.layout.pop_sort);
        helper.setMeasuredDimension(getWidth(), -2);
        helper.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (lastId == 1) {
                    onClick(vPrize);
                } else {
                    showSelectState(lastId);
                }
            }
        });
        tvSortName = (TextView) helper.findViewById(R.id.tv_sort_name);
        cbRushBuy = (CheckBox) helper.findViewById(R.id.cb_rush_buy);
        cbDiscount = (CheckBox) helper.findViewById(R.id.cb_discount);
        vTop = helper.findViewById(R.id.ll_top);
        helper.findViewById(R.id.v_shadow).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.dismiss();
            }
        });
        helper.findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbRushBuy.isChecked()) {
                    if (listener != null) {
                        listener.sort(true);
                    }
                } else {
                    if (listener != null) {
                        listener.sort(false);
                    }
                }
                helper.dismiss();
            }
        });
        cbDiscount.setChecked(true);
        cbDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ViewUtil.setViewVisibility(vTop, VISIBLE);
                    cbRushBuy.setChecked(false);
                    tvSortName.setText("限时折扣");
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
                } else {
                    if (!cbDiscount.isChecked()) {
                        ViewUtil.setViewVisibility(vTop, GONE);
                    }
                }
            }
        });
    }

    /**
     * 显示选中状态
     * 
     * @param id
     */
    private void showSelectState(int id) {
        tvNew.setSelected(false);
        tvSales.setSelected(false);
        tvPrize.setSelected(false);
        vSort.setSelected(false);
        ivDown.setSelected(false);
        ivUp.setSelected(false);
        switch (id) {
            case 0:
                tvNew.setSelected(true);
                break;
            case 1:
                tvPrize.setSelected(true);
                break;
            case 2:
                tvSales.setSelected(true);
                break;
            case 3:
                vSort.setSelected(true);
                break;
        }

    }

    private PopupWindowHelper helper;
}
