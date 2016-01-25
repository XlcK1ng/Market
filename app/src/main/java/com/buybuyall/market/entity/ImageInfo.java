
package com.buybuyall.market.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 描述:图片信息
 *
 * @author jakechen
 * @since 2016/1/25 10:45
 */
public class ImageInfo implements Serializable {
    private String thumb;

    private String large;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public static ImageInfo parse(JSONObject root) {
        if (root != null) {
            ImageInfo info = new ImageInfo();
            info.setThumb(root.optString("thumb"));
            info.setLarge(root.optString("large"));
            return info;
        }
        return null;
    }
}
