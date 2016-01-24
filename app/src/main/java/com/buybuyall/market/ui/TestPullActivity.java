
package com.buybuyall.market.ui;

import android.os.Bundle;

import com.buybuyall.market.R;
import com.buybuyall.market.widget.HomeDesItemView;

import cn.common.ui.activity.BaseWorkerFragmentActivity;

/**
 * 描述:
 *
 * @author jakechen
 * @since 2016/1/18 17:17
 */
public class TestPullActivity extends BaseWorkerFragmentActivity {
    private HomeDesItemView homeDesItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_test);
        homeDesItemView = (HomeDesItemView) findViewById(R.id.hdiv);
        homeDesItemView
                .setContent("马看来干嘛的快速过 卡到干嘛sad矮冬瓜聊点什么了十多个，了，；了马桑， 是倒过来看怕您sad难看了那时候奥斯卡了多功能南马路的功能上来看你你你说的更好的萨拉 大家卡萨好干啥的；sad改好到南沙卡死给大家看能看上你大概个偶尔玩乐fds;lhfdhsm;hrem asd ;mlgeow vmdamdkgmsa;；爱的slld朗朗上口送老公看电视购");
    }

}
