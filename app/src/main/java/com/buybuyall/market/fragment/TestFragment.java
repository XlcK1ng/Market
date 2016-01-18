
package com.buybuyall.market.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.buybuyall.market.R;

import java.util.ArrayList;

import cn.common.ui.adapter.BaseListAdapter;

/**
 * 描述:
 *
 * @author jakechen
 * @since 2016/1/18 18:20
 */
public class TestFragment extends BasePullListFragment<String> {

    @Override
    protected BaseListAdapter<String> createAdapter() {
        return new MyAdapter(getActivity());
    }

    @Override
    protected ArrayList<String> loadData(int pageIndex, int pageSize) {
        if (pageIndex == 3) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            list.add("haha");
        }
        return list;
    }

    class MyAdapter extends BaseListAdapter<String> {

        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return inflate(R.layout.item_home_goods);
        }
    }
}
