package com.buybuyall.market.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 描述：品牌信息
 * 作者：jake on 2016/1/13 22:19
 */
public class BrandInfo implements Serializable {
    private long brandId;
    private String brandName;
    private String brandPic;
    private String brandDescription;

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandPic() {
        return brandPic;
    }

    public void setBrandPic(String brandPic) {
        this.brandPic = brandPic;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public static BrandInfo parse(JSONObject root) {
        if (root != null) {
            BrandInfo info = new BrandInfo();
            info.setBrandId(root.optLong("brand_id"));
            info.setBrandName(root.optString("brand_name"));
            info.setBrandPic(root.optString("brand_pic"));
            info.setBrandDescription(root.optString("brand_description"));
            return info;
        }
        return null;
    }


}
