package com.buybuyall.market.logic.http.response;

import android.text.TextUtils;

import com.buybuyall.market.entity.AdvInfo;
import com.buybuyall.market.entity.PartyInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.common.http.base.BaseResponse;

/**
 * 描述：拼团页面的数据返回
 * 作者：jake on 2016/1/6 23:21
 */
public class PartyListResponse extends BaseResponse {
    private ArrayList<PartyInfo> list;

    public ArrayList<PartyInfo> getList() {
        return list;
    }

    public void setList(ArrayList<PartyInfo> list) {
        this.list = list;
    }

    @Override
    public Object parse(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject root = new JSONObject(json);
                JSONArray array = root.optJSONArray("datas");
                if (array != null && array.length() > 0) {
                    ArrayList<PartyInfo> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        if (object != null) {
                            PartyInfo info = PartyInfo.parse(object);
                            if (info != null) {
                                list.add(info);
                            }
                        }
                    }
                    setIsOk(true);
                    if (list.size() > 0) {
                        setList(list);
                        return this;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
