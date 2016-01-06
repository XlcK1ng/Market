package com.buybuyall.market.entity;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.common.http.JsonParse;

/**
 * 描述：广告信息
 * 作者：jake on 2015/12/29 23:45
 */
public class AdvInfo extends Object{

    private String apName;
    private String apIntro;
    private int adDisplay;
    private int linkType;
    private String advPic;
    private String advPicUrl;
    private String param;


    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public String getApIntro() {
        return apIntro;
    }

    public void setApIntro(String apIntro) {
        this.apIntro = apIntro;
    }

    public int getAdDisplay() {
        return adDisplay;
    }

    public void setAdDisplay(int adDisplay) {
        this.adDisplay = adDisplay;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public String getAdvPicUrl() {
        return advPicUrl;
    }

    public void setAdvPicUrl(String advPicUrl) {
        this.advPicUrl = advPicUrl;
    }

    public static AdvInfo parse(JSONObject root) {
        if (root != null) {
            AdvInfo info = new AdvInfo();
            info.setAdDisplay(root.optInt("ap_display"));
            info.setLinkType(root.optInt("link_type"));
            info.setParam(root.optString("param"));
            info.setAdvPic(root.optString("adv_pic"));
            info.setAdvPicUrl(root.optString("adv_pic_url"));
            info.setApName(root.optString("ap_name"));
            info.setApIntro(root.optString("ap_intro"));
            return info;
        }
        return null;
    }
}
