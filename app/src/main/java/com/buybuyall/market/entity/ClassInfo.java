package com.buybuyall.market.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 描述：分类信息
 * 作者：jake on 2016/1/14 21:22
 */
public class ClassInfo implements Serializable {

    private long gcId;
    private String gcName;
    private int gcSort;
    private int isTaxFree;
    private int isFreightFree;
    private int seperateOrder;
    private String gcThumb;

    public long getGcId() {
        return gcId;
    }

    public void setGcId(long gcId) {
        this.gcId = gcId;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public int getGcSort() {
        return gcSort;
    }

    public void setGcSort(int gcSort) {
        this.gcSort = gcSort;
    }

    public int getIsTaxFree() {
        return isTaxFree;
    }

    public void setIsTaxFree(int isTaxFree) {
        this.isTaxFree = isTaxFree;
    }

    public int getIsFreightFree() {
        return isFreightFree;
    }

    public void setIsFreightFree(int isFreightFree) {
        this.isFreightFree = isFreightFree;
    }

    public int getSeperateOrder() {
        return seperateOrder;
    }

    public void setSeperateOrder(int seperateOrder) {
        this.seperateOrder = seperateOrder;
    }

    public String getGcThumb() {
        return gcThumb;
    }

    public void setGcThumb(String gcThumb) {
        this.gcThumb = gcThumb;
    }

    public static ClassInfo parse(JSONObject root) {
        if (root != null) {
            ClassInfo info = new ClassInfo();
            info.setGcId(root.optLong("gc_id"));
            info.setGcName(root.optString("gc_name"));
            info.setGcSort(root.optInt("gc_sort"));
            info.setIsTaxFree(root.optInt("gc_sort"));
            info.setIsFreightFree(root.optInt("is_freight_free"));
            info.setSeperateOrder(root.optInt("seperate_order"));
            info.setGcThumb(root.optString("gc_thumb"));
            return info;
        }
        return null;
    }
}
