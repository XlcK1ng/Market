
package com.buybuyall.market.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 描述：广告信息 作者：jake on 2015/12/29 23:45
 */
public class AdvInfo implements Serializable {
    private String apName;

    private String apIntro;

    private int adDisplay;

    private int linkType;

    private String advPic;

    private String advPicUrl;

    private HashMap<String, String> paramMap;

    public HashMap<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(HashMap<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
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
            info.setAdvPic(root.optString("adv_pic"));
            info.setAdvPicUrl(root.optString("adv_pic_url"));
            info.setApName(root.optString("ap_name"));
            info.setApIntro(root.optString("ap_intro"));
            JSONArray array = root.optJSONArray("param");
            if (array != null && array.length() > 0) {
                HashMap<String, String> map = new HashMap<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    if (object != null) {
                        map.put(object.optString("name"), object.optString("value"));
                    }
                }
                if (map.size() > 0) {
                    info.setParamMap(map);
                }
            }

            return info;
        }
        return null;
    }
}
