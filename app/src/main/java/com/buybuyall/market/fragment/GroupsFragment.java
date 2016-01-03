package com.buybuyall.market.fragment;

import android.os.Message;
import android.widget.ImageView;
import android.widget.ListView;

import com.buybuyall.market.R;
import com.buybuyall.market.adapter.HomeGoodsAdapter;
import com.buybuyall.market.entity.GoodsInfo;
import com.buybuyall.market.utils.ToastUtil;
import com.buybuyall.market.widget.ViewCreator;

import java.util.ArrayList;

import cn.common.bitmap.core.ImageLoader;
import cn.common.ui.widgt.banner.BannerView;

/**
 * 描述：拼团页面
 * 作者：jake on 2016/1/3 13:36
 */
public class GroupsFragment extends StateFragment {
    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    private static final int MSG_UI_LOAD_DATA_SUCCESS = 0;
    private static final int MSG_UI_LOAD_DATA_FAIL = 1;
    private static final int MSG_BACK_LOAD_DATA = 0;
    private ListView lvContent;
    private HomeGoodsAdapter mGoodsAdapter;
    private BannerView bannerView;

    @Override
    protected void initView() {
        lvContent = ViewCreator.createCommonListView(getActivity());
        lvContent.addHeaderView(inflate(R.layout.view_header_group));
        setContentView(lvContent);
        bannerView = (BannerView) findViewById(R.id.banner_view);
        bannerView.setStyle(BannerView.STYLE_DOT_LEFT);
        bannerView.getDotView().setNormalColor(getColor(R.color.gray_dfdfdf));
        bannerView.getDotView().setSelectColor(getColor(R.color.orange_fb6e52));
        bannerView.setBannerListener(new BannerView.IListener() {
            @Override
            public void itemClick(Object banner) {
                ToastUtil.show((String) banner);
            }

            @Override
            public void loadImage(Object banner, ImageView ivBanner) {
                ImageLoader.getInstance().displayImage((String) banner, ivBanner);
            }
        });
    }

    private boolean isFirstIn = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstIn) {
            isFirstIn = false;
            sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
        }
    }

    @Override
    protected void initData() {
        mGoodsAdapter = new HomeGoodsAdapter(getActivity());
        lvContent.setAdapter(mGoodsAdapter);
        showLoadingView();
    }

    @Override
    public void reLoadData() {
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
    }

    @Override
    public void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
        switch (msg.what) {
            case MSG_BACK_LOAD_DATA:
                loadDataTask();
                break;
        }
    }

    private void loadDataTask() {
        ArrayList<GoodsInfo> goodsInfos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            goodsInfos.add(new GoodsInfo());
        }
        mGoodsAdapter.setData(goodsInfos);
        sendEmptyUiMessage(MSG_UI_LOAD_DATA_SUCCESS);
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_DATA_FAIL:
                showFailView();
                break;
            case MSG_UI_LOAD_DATA_SUCCESS:
                showContentView();
                ArrayList<Object> list = new ArrayList<>();
                list.add("http://pic26.nipic.com/20130121/9475856_141716386357_2.jpg");
                list.add("http://www.16sucai.com/uploadfile/2012/0706/20120706121453743.jpg");
                list.add("http://pic2.ooopic.com/10/82/24/63b1OOOPICe1.jpg");
                list.add("http://pic24.nipic.com/20121015/3684767_163317463111_2.jpg");
                list.add("http://pic2.ooopic.com/10/57/50/93b1OOOPIC4d.jpg");
                bannerView.setBannerList(list);
                bannerView.startScroll(3);
                mGoodsAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(bannerView!=null){
            bannerView.destroy();
        }
    }
}
