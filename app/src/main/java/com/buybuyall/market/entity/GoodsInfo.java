
package com.buybuyall.market.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 描述：商品信息 作者：jake on 2015/12/29 23:45
 */
public class GoodsInfo implements Serializable {

    private int groupBy;

    private double goodsMarketPrice;

    private long goodsId;

    private long storeId;

    private long goodsCommonId;

    private int ifTaxFree;

    private int ifFreightFree;

    private int groupBuy;

    private int xianShi;

    private double goodsPrice;

    private double packagePrice;

    private String goodsName;

    private String goodsImage;

    private String goodsJingLe;

    public double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(double packagePrice) {
        this.packagePrice = packagePrice;
    }

    public int getIfTaxFree() {
        return ifTaxFree;
    }

    public void setIfTaxFree(int ifTaxFree) {
        this.ifTaxFree = ifTaxFree;
    }

    public int getIfFreightFree() {
        return ifFreightFree;
    }

    public void setIfFreightFree(int ifFreightFree) {
        this.ifFreightFree = ifFreightFree;
    }

    public int getGroupBuy() {
        return groupBuy;
    }

    public void setGroupBuy(int groupBuy) {
        this.groupBuy = groupBuy;
    }

    public String getGoodsJingLe() {
        return goodsJingLe;
    }

    public void setGoodsJingLe(String goodsJingLe) {
        this.goodsJingLe = goodsJingLe;
    }

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

    public boolean isBS() {
        return ifTaxFree == 1;
    }

    public boolean isBY() {
        return ifFreightFree == 1;
    }

    public static GoodsInfo parse(JSONObject root) {
        if (root != null) {
            GoodsInfo info = new GoodsInfo();
            info.setStoreId(root.optLong("store_id"));
            info.setGoodsId(root.optLong("goods_id"));
            info.setGoodsName(root.optString("goods_name"));
            info.setGoodsPrice(root.optDouble("goods_price"));
            info.setGoodsMarketPrice(root.optDouble("goods_marketprice"));
            info.setPackagePrice(root.optDouble("package_price"));
            info.setGoodsImage(root.optString("goods_image"));
            info.setGroupBy(root.optInt("groupby"));
            info.setXianShi(root.optInt("xianshi"));
            info.setGoodsMarketPrice(root.optDouble("goods_marketprice"));
            info.setGoodsCommonId(root.optLong("goods_commonid"));
            info.setGoodsPrice(root.optDouble("goods_price"));
            info.setIfFreightFree(root.optInt("if_freight_free"));
            info.setIfTaxFree(root.optInt("if_tax_free"));
            info.setGroupBuy(root.optInt("groupbuy"));
            info.setGoodsJingLe(root.optString("goods_jingle"));
            return info;
        }
        return null;
    }
}
