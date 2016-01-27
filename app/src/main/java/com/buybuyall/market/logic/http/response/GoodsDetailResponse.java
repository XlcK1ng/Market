
package com.buybuyall.market.logic.http.response;

import android.text.TextUtils;

import com.buybuyall.market.entity.ImageInfo;
import com.buybuyall.market.entity.SpecInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.common.http.base.BaseResponse;

/**
 * 描述:商品详情
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

        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject root = new JSONObject(json);
                JSONObject data = root.optJSONObject("datas");
                setGoodsId(data.optLong("goods_id"));
                setGoodsName(data.optString("goods_name"));
                setGoodsJingLe(data.optString("goods_jingle"));
                setGoodsSpec(data.optString("goods_spec"));
                setSpecName(data.optString("spec_name"));
                setSpecValue(data.optString("spec_value"));
                setGoodsBody(data.optString("goods_body"));
                setGoodPrize(data.optDouble("goods_price"));
                setGoodsStorage(data.optInt("goods_storage"));
                setGoodsState(data.optInt("goods_state"));
                setColorId(data.optInt("color_id"));
                setTaxRate(data.optInt("tax_rate"));
                setIfTaxFree(data.optInt("if_tax_free"));
                setGoodsLimit(data.optInt("goods_limit"));
                setInformIsSelf(data.optBoolean("inform_isself"));
                JSONObject groupBuy = data.optJSONObject("groupbuy_info");
                if (groupBuy != null) {
                    setGroupBuyId(groupBuy.optLong("groupbuy_id"));
                    setStartTime(groupBuy.optLong("start_time"));
                    setEndTime(groupBuy.optLong("end_time"));
                    setGroupBuyPrice(groupBuy.optDouble("groupbuy_price"));
                    setGroupBuyName(groupBuy.optString("groupbuy_name"));
                    setUpperLimit(groupBuy.optInt("upper_limit"));
                }
                JSONArray specImg = data.optJSONArray("spec_image");
                if (specImg != null && specImg.length() > 0) {
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < specImg.length(); i++) {
                        String img = specImg.optString(i);
                        if (img != null) {
                            list.add(img);
                        }
                    }
                    if (list.size() > 0) {
                        setSpecImageList(list);
                    }
                }
                JSONArray array = data.optJSONArray("goods_image");
                if (array != null && array.length() > 0) {
                    ArrayList<ImageInfo> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        if (object != null) {
                            ImageInfo info = ImageInfo.parse(object);
                            if (info != null) {
                                list.add(info);
                            }
                        }
                    }
                    if (list.size() > 0) {
                        setImageList(list);
                    }
                }
                JSONArray specArray = data.optJSONArray("spec_list");
                if (specArray != null && specArray.length() > 0) {
                    ArrayList<SpecInfo> list = new ArrayList<>();
                    for (int i = 0; i < specArray.length(); i++) {
                        SpecInfo info = SpecInfo.parse(specArray.optJSONObject(i));
                        if (info != null) {
                            list.add(info);
                        }
                    }
                    if (list.size() > 0) {
                        setSpecList(list);
                    }
                }

                setIsOk(true);
                return this;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

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
