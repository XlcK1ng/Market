
package com.buybuyall.market.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 描述:首页聚优
 *
 * @author jakechen
 * @since 2016/1/22 17:11
 */
public class PackageInfo implements Serializable {
    private long packageId;

    private long startTime;

    private long endTime;

    private String packageName;

    private String packageDesc;

    private String packageImage;

    private int state;

    private int sort;

    private int packageStorage;

    private int discount;

    private double totalPackagePrice;

    private double totalOriPrice;

    private ArrayList<GoodsInfo> goodsList;

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public String getPackageImage() {
        return packageImage;
    }

    public void setPackageImage(String packageImage) {
        this.packageImage = packageImage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getPackageStorage() {
        return packageStorage;
    }

    public void setPackageStorage(int packageStorage) {
        this.packageStorage = packageStorage;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTotalPackagePrice() {
        return totalPackagePrice;
    }

    public void setTotalPackagePrice(double totalPackagePrice) {
        this.totalPackagePrice = totalPackagePrice;
    }

    public double getTotalOriPrice() {
        return totalOriPrice;
    }

    public void setTotalOriPrice(double totalOriPrice) {
        this.totalOriPrice = totalOriPrice;
    }

    public ArrayList<GoodsInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(ArrayList<GoodsInfo> goodsList) {
        this.goodsList = goodsList;
    }

    public static PackageInfo parse(JSONObject root) {
        if (root != null) {
            PackageInfo info = new PackageInfo();
            info.setPackageId(root.optLong("package_id"));
            info.setStartTime(root.optLong("start_time"));
            info.setEndTime(root.optLong("end_time"));
            info.setState(root.optInt("state"));
            info.setSort(root.optInt("sort"));
            info.setPackageStorage(root.optInt("package_storage"));
            info.setDiscount(root.optInt("discount"));
            info.setPackageName(root.optString("package_name"));
            info.setPackageDesc(root.optString("package_desc"));
            info.setPackageImage(root.optString("package_image"));
            info.setTotalPackagePrice(root.optDouble("total_package_price"));
            info.setTotalOriPrice(root.optDouble("total_ori_price"));
            JSONArray array = root.optJSONArray("goods");
            if (array != null && array.length() > 0) {
                ArrayList<GoodsInfo> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    GoodsInfo goodsInfo = GoodsInfo.parse(array.optJSONObject(i));
                    if (goodsInfo != null) {
                        list.add(goodsInfo);
                    }
                }
                if (list.size() > 0) {
                    info.setGoodsList(list);
                }
            }
            return info;
        }
        return null;
    }

}
