
package com.buybuyall.market.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 描述:
 *
 * @author jakechen
 * @since 2016/1/25 10:43
 */
public class SpecInfo implements Serializable {
    private ArrayList<ImageInfo> goodsImageList;

    private String sign;

    private String goodsImg;

    private long goodsId;

    private double goodsPrize;

    private int goodsStorage;

    private int goodsLimit;

    public static SpecInfo parse(JSONObject root) {
        if (root != null) {
            SpecInfo info = new SpecInfo();
            info.setSign(root.optString("sign"));
            info.setGoodsImg(root.optString("goods_img"));
            info.setGoodsId(root.optLong("goods_id"));
            info.setGoodsPrize(root.optDouble("goods_price"));
            info.setGoodsStorage(root.optInt("goods_storage"));
            info.setGoodsLimit(root.optInt("goods_limit"));
            JSONArray array = root.optJSONArray("goods_image");
            if (array != null & array.length() > 0) {
                ArrayList<ImageInfo> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    ImageInfo imageInfo = ImageInfo.parse(array.optJSONObject(i));
                    if (imageInfo != null) {
                        list.add(imageInfo);
                    }

                }
                if (list.size() > 0) {
                    info.setGoodsImageList(list);
                }
            }
            return info;
        }
        return null;
    }

    public ArrayList<ImageInfo> getGoodsImageList() {
        return goodsImageList;
    }

    public void setGoodsImageList(ArrayList<ImageInfo> goodsImageList) {
        this.goodsImageList = goodsImageList;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public int getGoodsStorage() {
        return goodsStorage;
    }

    public void setGoodsStorage(int goodsStorage) {
        this.goodsStorage = goodsStorage;
    }

    public int getGoodsLimit() {
        return goodsLimit;
    }

    public void setGoodsLimit(int goodsLimit) {
        this.goodsLimit = goodsLimit;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public double getGoodsPrize() {
        return goodsPrize;
    }

    public void setGoodsPrize(double goodsPrize) {
        this.goodsPrize = goodsPrize;
    }
}
