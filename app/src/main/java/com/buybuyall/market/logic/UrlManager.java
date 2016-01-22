
package com.buybuyall.market.logic;

/**
 * 描述：用于存放网络请求的链接 作者：jake on 2016/1/5 21:17
 */
public interface UrlManager {
    String KEY_CODE = "c7d32cfe9654074a21d8ad0ab25e5950";

    // 服务器地址
    String SERVER_URL = "http://120.76.78.1/webservice/v1.0/?";

    // 获取广告位
    String GET_ADV = "adv/get_adv/";

    // 拼团页面的数据
    String GET_PARTY_LIST = "party_activity/online_party_list/";

    // 品牌页面的数据
    String GET_BRAND_LIST = "brand/brand_list/";

    // 分类页面的数据
    String GET_CLASS_LIST = "gc_class/get_root_class/";

    // 子分类页面的数据
    String GET_CHILD_CLASS_LIST = "gc_class/get_class_list/";

    // 搜索
    String SEARCH = "goods/search/";
    // 首页精品阁
    String HOME_JP = "goods/index_recommend/";
    // 首页聚优汇
    String HOME_JY = "package/package_list/";
    // 首页大牌献
    String HOME_DP = "goods/goods_tax/order/2/";

    public static interface Keys {
        // 首页国家馆广告位
        String HOME_VENUES = "b24bcb6a1a1c709e12a1f19ddc3d2452";

        // 首页Banner广告位
        String HOME_BANNER = "8f90dc66635d92be4540d001e5d0e875";

        // 首页专题广告位
        String HOME_ADV = "ed2573134a131a70b1c1bf42c7ef1c96";

        // 个人中心（未登陆）
        String USER_CENTER = "64d77bad07e2096198ebd5911fcc8d71";

        // 澳洲馆内页广告位
        String AOZHOUE = " 54ab95ec073ee223666f28fd5d254708";

        // 美洲馆内页广告位
        String MEIZHOU = " 295412d8d480430fb2942e9ebf1073b6";

        // 亚洲馆内页广告位
        String YAZHOU = "19396ade0c7a98885f62b86047117ff5";

        // 拼团广告位
        String PARTY = "6f16dea73b014286b4b71065d3897d84";

        // 套装内页广告位
        String TAOZHUANG = " a37fc24966bf9a25014a019c4d79632a";
    }

}
