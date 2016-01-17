package com.buybuyall.market.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buybuyall.market.R;

import cn.common.ui.helper.PopupWindowHelper;
import cn.common.utils.DisplayUtil;

/**
 * 描述：筛选的控件
 * 作者：jake on 2016/1/17 21:11
 */
public class SelectBarView extends LinearLayout implements View.OnClickListener {
    private TextView tvNew;
    private View vSort;
    private TextView tvSales;
    private ImageView ivUp;
    private ImageView ivDown;
    private IListener listener;


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
        findViewById(R.id.fl_prize).setOnClickListener(this);
        tvNew.setOnClickListener(this);
        tvSales.setOnClickListener(this);
        vSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_new) {
        } else if (id == R.id.tv_sales) {
        } else if (id == R.id.fl_prize) {
        } else if (id == R.id.fl_sort) {
            if (helper == null) {
                helper = new PopupWindowHelper(getContext());
                helper.setView(R.layout.pop_sort);
                helper.setMeasuredDimension(getWidth(), -2);
            }
            helper.showAsDropDown(vSort);
        }
    }


    private PopupWindowHelper helper;
}
