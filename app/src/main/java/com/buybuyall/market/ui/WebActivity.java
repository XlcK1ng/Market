package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.ViewCreator;

/**
 * 描述:网页容器
 *
 * @author jakechen
 * @since 2016/1/28 16:33
 */
public class WebActivity extends CommonTitleActivity {
  public static final String KEY_TITLE = "key_title";
  public static final String KEY_URL = "key_url";

  public static void start(Context context, String title, String url) {
    if (context != null) {
      Intent it = new Intent(context, WebActivity.class);
      it.putExtra(KEY_TITLE, title);
      it.putExtra(KEY_URL, url);
      context.startActivity(it);
    }
  }

  private WebView webView;
  private String title = "网页";
  private String url;

  @Override
  protected void dealIntent(Bundle data) {
    super.dealIntent(data);
    if (data != null) {
      title = data.getString(KEY_TITLE, "网页");
      url = data.getString(KEY_URL, null);
    }
    if (TextUtils.isEmpty(url)) {
      ToastUtil.show("网页初始化失败，请重试");
      finish();
    }
  }

  @Override
  protected void initView() {
    webView = ViewCreator.createCommonWebView(this);
    setTitle(title);
    setContentView(webView);
    webView.loadUrl(url);
  }
}
