package com.buybuyall.market.logic.http;

import com.buybuyall.market.logic.UrlManager;

import java.util.Hashtable;
import java.util.Set;

import cn.common.http.base.BaseHttpClientRequest;

/**
 * 描述：数据请求类
 * 作者：jake on 2016/1/5 21:11
 */
public class HttpRequest<T> extends BaseHttpClientRequest<T> {
    public HttpRequest(String svc, Class<?> clazz) {
        super(svc, clazz);
    }

    @Override
    protected void addCommonParam() {

    }

    @Override
    protected String getServerUrl() {
        return UrlManager.SERVER_URL;
    }

    @Override
    protected Hashtable<String, String> getRequestHeaders() {
        return null;
    }

    @Override
    protected String getGetRequestParams() {
        if (mParams != null && mParams.size() >= 0) {
            StringBuilder builder = new StringBuilder();
            final Set<String> keys = mParams.keySet();
            for (String key : keys) {
                builder.append(key.trim()).append("/").append(mParams.get(key).trim()).append("/");
            }
            builder.append(UrlManager.KEY_CODE);
            return builder.toString();
        }
        return null;
    }
}
