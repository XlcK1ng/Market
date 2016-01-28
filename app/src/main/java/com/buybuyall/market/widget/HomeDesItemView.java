package com.buybuyall.market.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.buybuyall.market.R;

import cn.common.utils.DisplayUtil;

/**
 * 描述:
 *
 * @author jakechen
 * @since 2016/1/23 15:17
 */
public class HomeDesItemView extends FrameLayout {


  private View vInsideContent;

  private TextView tvContent;

  private ImageView ivLeft;

  public HomeDesItemView(Context context) {
    this(context, null);
  }

  public void setContent(String content) {
    tvContent.setText(content);
  }

  private boolean contentIsShow = false;

  public HomeDesItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    scroller = new Scroller(context);
    setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
    inflate(context, R.layout.view_des_item, this);
    vContent = findViewById(R.id.fl_content);
    ivLeft = (ImageView) findViewById(R.id.iv_left);
    vInsideContent = findViewById(R.id.sv_content);
    tvContent = (TextView) findViewById(R.id.tv_content);
    if (attrs != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HomeDesItemView);
      int inside_bg = typedArray.getResourceId(R.styleable.HomeDesItemView_inside_bg, R.color.black_363636);
      int textColor = typedArray.getColor(R.styleable.HomeDesItemView_text_color, Color.WHITE);
      float textSize = typedArray.getDimension(R.styleable.HomeDesItemView_text_size, DisplayUtil.dip(14));
      tvContent.setTextColor(textColor);
      tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
      vInsideContent.setBackgroundResource(inside_bg);
    }
    ivLeft.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (contentIsShow) {
          scrollRight();

        } else {
          contentIsShow = true;
          scrollLeft();
        }
      }
    });
    tvContent.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (contentIsShow) {
          scrollRight();
          contentIsShow = false;
        } else {
          contentIsShow = true;
          scrollLeft();
        }
      }
    });
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    scrollRight(true);
  }

  private View vContent;

  /**
   * 滑动类
   */
  private Scroller scroller;


  @Override
  public void computeScroll() {
    // 调用startScroll的时候scroller.computeScrollOffset()返回true，
    if (scroller.computeScrollOffset()) {
      vContent.scrollTo(scroller.getCurrX(), scroller.getCurrY());
      postInvalidate();
    }
  }

  /**
   * 往右滑动，getScrollX()返回的是左边缘的距离，就是以View左边缘为原点到开始滑动的距离，所以向右边滑动为负值
   */
  private void scrollRight(boolean isFast) {
    contentIsShow = false;
    final int delta = (vContent.getWidth() + vContent.getScrollX() - ivLeft.getWidth());
    // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
    scroller.startScroll(vContent.getScrollX(), 0, -delta, 0,isFast?0: Math.abs(delta));
    postInvalidate(); // 刷新itemView
  }
  private void scrollRight() {
    scrollRight(false);
  }

  private void scrollLeft() {
    contentIsShow = true;
    final int delta = (vContent.getWidth() + vContent.getScrollX());
    // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
    scroller.startScroll(vContent.getScrollX(), 0, 0 - vContent.getScrollX(), 0, Math.abs(delta));
    postInvalidate(); // 刷新itemView
  }

}
