package com.buybuyall.market.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 描述：拼团列表item信息
 * 作者：jake on 2016/1/7 20:54
 */
public class PartyInfo implements Serializable {
    private long partyId;
    private String title;
    private String partyDesc;
    private int partyNum;
    private double partyGoodsPrice;
    private long goodsId;
    private String partyImage;
    private double goodsPrice;
    private double goodsMarketprice;

    public long getPartyId() {
        return partyId;
    }

    public void setPartyId(long partyId) {
        this.partyId = partyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPartyDesc() {
        return partyDesc;
    }

    public void setPartyDesc(String partyDesc) {
        this.partyDesc = partyDesc;
    }

    public int getPartyNum() {
        return partyNum;
    }

    public void setPartyNum(int partyNum) {
        this.partyNum = partyNum;
    }

    public double getPartyGoodsPrice() {
        return partyGoodsPrice;
    }

    public void setPartyGoodsPrice(double partyGoodsPrice) {
        this.partyGoodsPrice = partyGoodsPrice;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getPartyImage() {
        return partyImage;
    }

    public void setPartyImage(String partyImage) {
        this.partyImage = partyImage;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public double getGoodsMarketprice() {
        return goodsMarketprice;
    }

    public void setGoodsMarketprice(double goodsMarketprice) {
        this.goodsMarketprice = goodsMarketprice;
    }

    public static PartyInfo parse(JSONObject root) {
        if (root != null) {
            PartyInfo info = new PartyInfo();
            info.setPartyId(root.optLong("party_id"));
            info.setTitle(root.optString("title"));
            info.setPartyDesc(root.optString("party_desc"));
            info.setPartyNum(root.optInt("party_num"));
            info.setPartyGoodsPrice(root.optDouble("party_goods_price"));
            info.setGoodsId(root.optLong("goods_id"));
            info.setPartyImage(root.optString("party_image"));
            info.setGoodsPrice(root.optDouble("goods_price"));
            info.setGoodsMarketprice(root.optDouble("goods_marketprice"));
            return info;
        }
        return null;
    }

}
