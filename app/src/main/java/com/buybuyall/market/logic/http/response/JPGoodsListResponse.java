
package com.buybuyall.market.logic.http.response;

import android.text.TextUtils;

import com.buybuyall.market.entity.GoodsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.common.http.base.BaseResponse;

/**
 * 描述：物品列表 作者：jake on 2016/1/17 14:01
 */
public class JPGoodsListResponse extends BaseResponse {
    private ArrayList<GoodsInfo> list;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<GoodsInfo> getList() {
        return list;
    }

    public void setList(ArrayList<GoodsInfo> list) {
        this.list = list;
    }

    @Override
    public Object parse(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject root = new JSONObject(json);
                JSONArray array = root.optJSONArray("datas");
                if (array != null && array.length() > 0) {
                    ArrayList<GoodsInfo> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        if (object != null) {
                            GoodsInfo info = GoodsInfo.parse(object);
                            if (info != null) {
                                list.add(info);
                            }
                        }
                    }
                    if (list.size() > 0) {
                        setList(list);
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
}
