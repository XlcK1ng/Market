package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 描述:物品详情页
 *
 * @author jakechen
 * @since 2016/1/21 18:07
 */
public class GoodsDetailActivity extends StateActivity {
  private static final String KEY_GOODS_ID = "key_goods_id";

  public static void start(Context context, long goodsId) {
    if (context != null) {
      Intent it = new Intent(context, GoodsDetailActivity.class);
      it.putExtra(KEY_GOODS_ID, goodsId);
      context.startActivity(it);
    }
  }

  private long goodsId = 0;

  @Override
  protected void dealIntent(Bundle data) {
    super.dealIntent(data);
    if (data != null) {
      goodsId = data.getInt(KEY_GOODS_ID, 0);
    }
  }

  @Override
  protected void initView() {
    showFailView();
  }

  @Override
  public void reLoadData() {
  }
}
