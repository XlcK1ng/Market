package com.buybuyall.market.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 描述：商品信息
 * 作者：jake on 2015/12/29 23:45
 */
public class GoodsInfo implements Serializable {


    private long storeId;
    private long goodsId;
    private String goodsName;
    private double goodsPrice;
    private String goodsImage;
    private int groupBy;
    private int xianShi;
    private double goodsMarketPrice;
    private long goodsCommonId;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public int getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(int groupBy) {
        this.groupBy = groupBy;
    }

    public int getXianShi() {
        return xianShi;
    }

    public void setXianShi(int xianShi) {
        this.xianShi = xianShi;
    }

    public double getGoodsMarketPrice() {
        return goodsMarketPrice;
    }

    public void setGoodsMarketPrice(double goodsMarketPrice) {
        this.goodsMarketPrice = goodsMarketPrice;
    }

    public long getGoodsCommonId() {
        return goodsCommonId;
    }

    public void setGoodsCommonId(long goodsCommonId) {
        this.goodsCommonId = goodsCommonId;
    }


    public static GoodsInfo parse(JSONObject root) {
        if (root != null) {
            GoodsInfo info = new GoodsInfo();
            info.setStoreId(root.optLong("store_id"));
            info.setGoodsId(root.optLong("goods_id"));
            info.setGoodsName(root.optString("goods_name"));
            info.setGoodsPrice(root.optDouble("goods_price"));
            info.setGoodsImage(root.optString("goods_image"));
            info.setGroupBy(root.optInt("groupby"));
            info.setXianShi(root.optInt("xianshi"));
            info.setGoodsMarketPrice(root.optDouble("goods_marketprice"));
            info.setGoodsCommonId(root.optLong("goods_commonid"));
            info.setGoodsPrice(root.optDouble("goods_price"));
            return info;
        }
        return null;
    }
}
