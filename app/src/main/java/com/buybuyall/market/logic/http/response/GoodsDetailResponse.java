
package com.buybuyall.market.logic.http.response;

import com.buybuyall.market.entity.ImageInfo;
import com.buybuyall.market.entity.SpecInfo;

import java.util.ArrayList;

import cn.common.http.base.BaseResponse;

/**
 * 描述:
 *
 * @author jakechen
 * @since 2016/1/25 10:27
 */
public class GoodsDetailResponse extends BaseResponse {
    private long goodsId;

    private long xianShiId;

    private long groupBuyId;

    private long startTime;

    private long endTime;

    private String goodsName;

    private String goodsJingLe;

    private String goodsSpec;

    private String specName;

    private String specValue;

    private String goodsBody;

    private String groupBuyName;

    private String xianShiName;

    private String xianShiTitle;

    private double goodPrize;

    private double groupBuyPrice;

    private double xianShiPrice;

    private int goodsState;

    private int goodsStorage;

    private int colorId;

    private int taxRate;

    private int ifTaxFree;

    private int goodsLimit;

    private int upperLimit;

    private boolean informIsSelf;

    private ArrayList<String> specImageList;

    private ArrayList<ImageInfo> imageList;

    private ArrayList<SpecInfo> specList;

    @Override
    public Object parse(String json) {
        return null;
    }
    // {
    // "status": "HTTP/1.1 200 OK",
    // "datas": {
    // "goods_id": "1959",
    // "goods_name": "joey-抢购上限=3",
    // "goods_jingle": "''",
    // "goods_price": "52.80",
    // "goods_storage": "79998",
    // "goods_state": "1",
    // "goods_spec": null,
    // "color_id": "0",
    // "goods_image": [
    // {
    // "thumb":
    // "http://120.76.78.1/data/upload/shop/store/goods/4/4_05055765865116323_360.png",
    // "large":
    // "http://120.76.78.1/data/upload/shop/store/goods/4/4_05055765865116323_1280.png"
    // }
    // ],
    // "tax_rate": "10",
    // "if_tax_free": "0",
    // "goods_limit": "3",
    // "spec_name": null,
    // "spec_value": null,
    // "goods_body": "",
    // "groupbuy_info": {
    // "groupbuy_id": "658",
    // "groupbuy_price": "33.33",
    // "groupbuy_name": "joey-抢购上限=3",
    // "start_time": "1452082560",
    // "end_time": "1482940800",
    // "upper_limit": "3"
    // },
    // "inform_isself": true,
    // "spec_image": [
    // "http://120.76.78.1/data/upload/shop/store/goods/4/4_05055765865116323_60.png"
    // ],
    // "spec_list": [
    // {
    // "goods_image": [
    // {
    // "thumb":
    // "http://120.76.78.1/data/upload/shop/store/goods/4/4_05055765865116323_360.png",
    // "large":
    // "http://120.76.78.1/data/upload/shop/store/goods/4/4_05055765865116323_1280.png"
    // }
    // ],
    // "sign": "",
    // "goods_id": "1959",
    // "goods_price": "33.33",
    // "goods_storage": "79998",
    // "goods_limit": "3",
    // "goods_img":
    // "http://120.76.78.1/data/upload/shop/store/goods/4/4_05055765865116323_60.png"
    // }
    // ]
    // }
    // }

  public long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(long goodsId) {
    this.goodsId = goodsId;
  }

  public long getXianShiId() {
    return xianShiId;
  }

  public void setXianShiId(long xianShiId) {
    this.xianShiId = xianShiId;
  }

  public long getGroupBuyId() {
    return groupBuyId;
  }

  public void setGroupBuyId(long groupBuyId) {
    this.groupBuyId = groupBuyId;
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

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public String getGoodsJingLe() {
    return goodsJingLe;
  }

  public void setGoodsJingLe(String goodsJingLe) {
    this.goodsJingLe = goodsJingLe;
  }

  public String getGoodsSpec() {
    return goodsSpec;
  }

  public void setGoodsSpec(String goodsSpec) {
    this.goodsSpec = goodsSpec;
  }

  public String getSpecName() {
    return specName;
  }

  public void setSpecName(String specName) {
    this.specName = specName;
  }

  public String getSpecValue() {
    return specValue;
  }

  public void setSpecValue(String specValue) {
    this.specValue = specValue;
  }

  public String getGoodsBody() {
    return goodsBody;
  }

  public void setGoodsBody(String goodsBody) {
    this.goodsBody = goodsBody;
  }

  public String getGroupBuyName() {
    return groupBuyName;
  }

  public void setGroupBuyName(String groupBuyName) {
    this.groupBuyName = groupBuyName;
  }

  public String getXianShiName() {
    return xianShiName;
  }

  public void setXianShiName(String xianShiName) {
    this.xianShiName = xianShiName;
  }

  public String getXianShiTitle() {
    return xianShiTitle;
  }

  public void setXianShiTitle(String xianShiTitle) {
    this.xianShiTitle = xianShiTitle;
  }

  public double getGoodPrize() {
    return goodPrize;
  }

  public void setGoodPrize(double goodPrize) {
    this.goodPrize = goodPrize;
  }

  public double getGroupBuyPrice() {
    return groupBuyPrice;
  }

  public void setGroupBuyPrice(double groupBuyPrice) {
    this.groupBuyPrice = groupBuyPrice;
  }

  public double getXianShiPrice() {
    return xianShiPrice;
  }

  public void setXianShiPrice(double xianShiPrice) {
    this.xianShiPrice = xianShiPrice;
  }

  public int getGoodsState() {
    return goodsState;
  }

  public void setGoodsState(int goodsState) {
    this.goodsState = goodsState;
  }

  public int getGoodsStorage() {
    return goodsStorage;
  }

  public void setGoodsStorage(int goodsStorage) {
    this.goodsStorage = goodsStorage;
  }

  public int getColorId() {
    return colorId;
  }

  public void setColorId(int colorId) {
    this.colorId = colorId;
  }

  public int getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(int taxRate) {
    this.taxRate = taxRate;
  }

  public int getIfTaxFree() {
    return ifTaxFree;
  }

  public void setIfTaxFree(int ifTaxFree) {
    this.ifTaxFree = ifTaxFree;
  }

  public int getGoodsLimit() {
    return goodsLimit;
  }

  public void setGoodsLimit(int goodsLimit) {
    this.goodsLimit = goodsLimit;
  }

  public int getUpperLimit() {
    return upperLimit;
  }

  public void setUpperLimit(int upperLimit) {
    this.upperLimit = upperLimit;
  }

  public boolean isInformIsSelf() {
    return informIsSelf;
  }

  public void setInformIsSelf(boolean informIsSelf) {
    this.informIsSelf = informIsSelf;
  }

  public ArrayList<String> getSpecImageList() {
    return specImageList;
  }

  public void setSpecImageList(ArrayList<String> specImageList) {
    this.specImageList = specImageList;
  }

  public ArrayList<ImageInfo> getImageList() {
    return imageList;
  }

  public void setImageList(ArrayList<ImageInfo> imageList) {
    this.imageList = imageList;
  }

  public ArrayList<SpecInfo> getSpecList() {
    return specList;
  }

  public void setSpecList(ArrayList<SpecInfo> specList) {
    this.specList = specList;
  }
}
// goods_id 商品id
// goods_name 商品名称
// goods_price 商品原价
// goods_storage 商品库存
// goods_state 商品状态（0下架，1正常，10禁售）
// color_id 颜色id
// mobile_body 移动端商品描述
// goods_image
// child
// thumb 缩略图链接（宽360像素）
// large 大图链接（宽1280像素）(如果if_pc传1，则不读取大图链接)
// inform_isself 是否允许购买（true为允许，false表示该商品是当前登陆用户自己店铺的商品，不允许购买）
// xianshi_info 限时折扣促销信息（正在进行此活动时才会有此数据，否则没有）
// xianshi_id 限时折扣id
// xianshi_name 限时折扣名
// xianshi_title 限时折扣活动简称
// xianshi_price 限时折扣价（当商品购买数量大于等于最低购买限额时，显示该价格）
// start_time 开始时间
// end_time 结束时间
// lower_limit 最低购买限额（最低购买X件，才能享受限时折扣价）
// groupbuy_info 限时折扣促销信息（正在进行此活动时才会有此数据，否则没有）
// groupbuy_id 抢购活动id
// groupbuy_price 抢购价（当进行抢购活动时，显示此价格）
// groupbuy_name 抢购活动名
// start_time 抢购开始时间
// end_time 抢购结束时间
// upper_limit 限购数量（0表示不限购，n表示限购n件）
