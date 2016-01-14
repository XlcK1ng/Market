package com.buybuyall.market.logic.http.response;

import android.text.TextUtils;

import com.buybuyall.market.entity.BrandInfo;
import com.buybuyall.market.entity.ClassInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.common.http.base.BaseResponse;

/**
 * 描述：品牌列表数据
 * 作者：jake on 2016/1/13 22:23
 */
public class ClassListResponse extends BaseResponse {
    private ArrayList<ClassInfo> list;

    public ArrayList<ClassInfo> getList() {
        return list;
    }

    public void setList(ArrayList<ClassInfo> list) {
        this.list = list;
    }

    @Override
    public Object parse(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject root = new JSONObject(json);
                JSONObject oa = root.optJSONObject("datas");
                JSONArray array = oa.optJSONArray("class_list");
                if (array != null && array.length() > 0) {
                    ArrayList<ClassInfo> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        if (object != null) {
                            ClassInfo info = ClassInfo.parse(object);
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