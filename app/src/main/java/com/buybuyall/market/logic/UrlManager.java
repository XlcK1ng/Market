package com.buybuyall.market.logic;

/**
 * 描述：用于存放网络请求的链接
 * 作者：jake on 2016/1/5 21:17
 */
public interface UrlManager {
    String KEY_CODE = "c7d32cfe9654074a21d8ad0ab25e5950";
    //服务器地址
    String SERVER_URL = "http://www.bbaline.com/webservice/v1.0/?";
    //获取广告位
    String GET_ADV = "adv/get_adv/";
    //获取拼团页面的数据
    String GET_PARTY_LIST = "party_activity/online_party_list/";


}
