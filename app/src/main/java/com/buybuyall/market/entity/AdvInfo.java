package com.buybuyall.market.entity;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.common.http.JsonParse;
/**
 * 描述：广告信息
 * 作者：jake on 2015/12/29 23:45
 */
public class AdvInfo implements JsonParse {

    private String apName;
    private String apIntro;
    private int adDisplay;
    private String advPic;
    private String advPicUrl;

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

    @Override
    public AdvInfo parse(String json) {
        //    "ap_name": "专题广告位",
//            "ap_intro": "围巾专题~~为你提供一个温暖的冬天~",
//            "ap_display": "1",
//            "adv_pic": "http://120.25.245.59/data/upload/shop/adv/04994431017079701.jpg",
//            "adv_pic_url": "www.buybuyall.com"
        try {
            if (!TextUtils.isEmpty(json)) {
                JSONObject root = new JSONObject(json);
                setAdDisplay(root.optInt("ap_display"));
                setAdvPic(root.optString("adv_pic"));
                setAdvPicUrl(root.optString("adv_pic_url"));
                setApName(root.optString("ap_name"));
                setApIntro(root.optString("ap_intro"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
