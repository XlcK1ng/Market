package com.buybuyall.market.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.GoodsSearchAdapter;

/**
 * 描述：分类列表
 * 作者：jake on 2016/1/17 22:19
 */
public class ClassListActivity extends StateActivity {
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_ID = "key_id";
    private ListView listView;
    private GoodsSearchAdapter adapter;

    public static void start(Context context, long classId, String className) {
        Intent it = new Intent(context, ClassListActivity.class);
        it.putExtra(KEY_ID, classId);
        it.putExtra(KEY_TITLE, className);
        context.startActivity(it);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activtiy_class_list);
        if (getIntent().hasExtra(KEY_TITLE)) {
            setTitle(getIntent().getExtras().getString(KEY_TITLE, "分类列表"));
        }
        listView = (ListView) findViewById(R.id.lv_content);
        listView.addHeaderView(inflate(R.layout.header_class_list));
        adapter = new GoodsSearchAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void reLoadData() {

    }
}
