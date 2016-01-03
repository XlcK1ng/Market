package com.buybuyall.market.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buybuyall.market.R;

import org.w3c.dom.Text;

import cn.common.utils.DisplayUtil;

/**
 * 描述：通用的item，左边为icon，右边为箭头，中间为title和tip
 * 作者：jake on 2016/1/3 11:49
 */
public class CommonItemView extends LinearLayout {
    private TextView tvTitle;
    private TextView tvTip;
    private ImageView ivIcon;
    private ImageView ivArrowRight;

    public CommonItemView(Context context) {
        this(context, null);
    }

    public CommonItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_common_item_layout, this);
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        ivArrowRight = (ImageView) findViewById(R.id.iv_arrow_right);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CommonItemView);
        String title_text = typedArray.getString(R.styleable.CommonItemView_title_text);
        String tip_text = typedArray.getString(R.styleable.CommonItemView_tip_text);
        tvTitle.setText(title_text);
        tvTip.setText(tip_text);
        float title_margin_icon = typedArray.getDimension(R.styleable.CommonItemView_title_margin_icon, DisplayUtil.dip(10));
        LinearLayout.LayoutParams tl = (LayoutParams) tvTitle.getLayoutParams();
        tl.leftMargin = (int) title_margin_icon;
        tvTitle.setLayoutParams(tl);
        float tip_margin_title = typedArray.getDimension(R.styleable.CommonItemView_tip_margin_title, DisplayUtil.dip(10));
        float tip_margin_arrow_right = typedArray.getDimension(R.styleable.CommonItemView_tip_margin_arrow_right, DisplayUtil.dip(10));
        LinearLayout.LayoutParams tt = (LayoutParams) tvTip.getLayoutParams();
        tt.leftMargin = (int) tip_margin_title;
        tt.rightMargin = (int) tip_margin_arrow_right;
        tvTip.setLayoutParams(tt);
        float title_text_size = typedArray.getDimension(R.styleable.CommonItemView_title_text_size, DisplayUtil.dip(14));
        float tip_text_size = typedArray.getDimension(R.styleable.CommonItemView_tip_text_size, DisplayUtil.dip(14));
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,title_text_size);
        tvTip.setTextSize(TypedValue.COMPLEX_UNIT_PX,tip_text_size);
        int title_text_color = typedArray.getColor(R.styleable.CommonItemView_title_text_color, Color.parseColor("#363636"));
        int tip_text_color = typedArray.getColor(R.styleable.CommonItemView_tip_text_color, Color.parseColor("#757575"));
        tvTitle.setTextColor(title_text_color);
        tvTip.setTextColor(tip_text_color);
        boolean icon_visible = typedArray.getBoolean(R.styleable.CommonItemView_icon_visible, true);
        ivIcon.setVisibility(icon_visible ? VISIBLE : GONE);
        boolean tip_visible = typedArray.getBoolean(R.styleable.CommonItemView_tip_visible, false);
        tvTip.setVisibility(tip_visible ? VISIBLE : GONE);
        boolean arrow_visible = typedArray.getBoolean(R.styleable.CommonItemView_arrow_visible, true);
        ivArrowRight.setVisibility(arrow_visible ? VISIBLE : GONE);
        int icon_src = typedArray.getResourceId(R.styleable.CommonItemView_icon_src, R.drawable.ic_launcher);
        int arrow_src = typedArray.getResourceId(R.styleable.CommonItemView_arrow_src, R.drawable.arrow_right);
        ivIcon.setImageResource(icon_src);
        ivArrowRight.setImageResource(arrow_src);
        typedArray.recycle();
    }
}
