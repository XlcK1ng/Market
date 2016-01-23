
package com.buybuyall.market.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 描述:
 *
 * @author jakechen
 * @since 2016/1/23 15:19
 */
public class ParamInfo implements Serializable {
    private String name;

    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ParamInfo parse(JSONObject root) {
        if (root != null) {
            ParamInfo info = new ParamInfo();
            info.setName(root.optString("name"));
            info.setValue(root.optInt("value"));
            return info;
        }
        return null;
    }
}
