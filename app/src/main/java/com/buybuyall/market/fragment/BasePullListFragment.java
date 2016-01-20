
package com.buybuyall.market.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.buybuyall.market.R;
import com.buybuyall.market.utils.ToastUtil;

import java.util.ArrayList;

import cn.common.ui.adapter.BaseListAdapter;
import cn.common.ui.widgt.pull.PullDragHelper;
import cn.common.ui.widgt.pull.PullEnableListView;
import cn.common.ui.widgt.pull.PullListener;
import cn.common.ui.widgt.pull.PullToRefreshLayout;
import cn.common.utils.ViewUtil;

/**
 * 描述:拥有下拉刷新的fragment
 *
 * @author jakechen
 * @since 2016/1/18 17:32
 */
public abstract class BasePullListFragment<T> extends StateFragment implements PullListener,
        AdapterView.OnItemClickListener {

    public static final int MSG_UI_LOAD_SUCCESS = 0x1001;

    public static final int MSG_UI_LOAD_FAIL = 0x1002;

    public static final int MSG_UI_ALL_DATA_HAVE_LOADED = 0x1003;

    public static final int MSG_UI_NO_DATA = 0x1004;

    protected static final int MSG_BACK_LOAD_DATA = 0x2000;

    // 默认第一页
    protected static final int PAGE_START = 1;

    private PullToRefreshLayout mPullToRefreshLayout;

    private PullEnableListView mListView;

    protected BaseListAdapter<T> mAdapter;

    protected int pageSize = 10;

    protected int pageIndex = PAGE_START;

    // 数据是否已经全部加载完
    public boolean hasLoadAllData;

    private boolean showFinishLoad = false;

    private long delayLoadTime = 0;

    private View footerView;
    private boolean hasMore = true;
    private ImageView ivGoTop;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_base_list);
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pull_view);
        mListView = new PullEnableListView(getActivity());
        mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mPullToRefreshLayout.setContentView(mListView);
        mPullToRefreshLayout.setPullListener(this);
        ivGoTop = (ImageView) findViewById(R.id.iv_go_top);
        ivGoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListView.setSelection(0);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > 10) {
                    ViewUtil.setViewVisibility(ivGoTop, View.VISIBLE);
                } else {
                    ViewUtil.setViewVisibility(ivGoTop, View.GONE);

                }
            }
        });
        setResultShowTime(300);
        showFinishLoad = true;
        addHeaderView(mListView);
        mAdapter = createAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        if (showFinishLoad) {
            footerView = inflate(R.layout.view_footer_finish_load_data);
        }
        pageIndex = PAGE_START;
        showLoadingView();
        sendEmptyBackgroundMessageDelayed(MSG_BACK_LOAD_DATA, delayLoadTime);
    }

    @Override
    public void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
        switch (msg.what) {
            case MSG_BACK_LOAD_DATA:
                // 开始请求数据
                ArrayList<T> list = dealData(loadData(pageIndex, pageSize));
                if (list != null) {
                    if (list.size() > 0) {
                        if (pageIndex == PAGE_START) {
                            mAdapter.setData(list);
                        } else {
                            mAdapter.addAll(list);
                        }
                        if (!hasMore) {
                            sendEmptyUiMessage(MSG_UI_ALL_DATA_HAVE_LOADED);
                        } else {
                            sendEmptyUiMessage(MSG_UI_LOAD_SUCCESS);
                        }
                    } else {
                        if (pageIndex == PAGE_START) {
                            sendEmptyUiMessage(MSG_UI_NO_DATA);
                        } else {
                            sendEmptyUiMessage(MSG_UI_ALL_DATA_HAVE_LOADED);
                        }
                    }
                } else {
                    // list为null表示加载数据失败
                    sendEmptyUiMessage(MSG_UI_LOAD_FAIL);
                }
                break;
        }
    }

    @Override
    public void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_LOAD_SUCCESS:
                if (mPullToRefreshLayout != null) {
                    mPullToRefreshLayout.finishTask(true);
                }
                // 数据加载成功,且数据条数不为0
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                if (pageIndex == PAGE_START) {
                    mListView.setSelection(0);
                    showContentView();
                }
                break;
            case MSG_UI_LOAD_FAIL:
                // 数据加载失败
                if (mPullToRefreshLayout != null) {
                    mPullToRefreshLayout.finishTask(false);
                }
                if (pageIndex == PAGE_START) {
                    showFailView();
                } else {
                    if (pageIndex > PAGE_START) {
                        pageIndex--;
                        ToastUtil.show("数据加载失败");
                    }
                }
                break;
            case MSG_UI_ALL_DATA_HAVE_LOADED:
                if (pageIndex == PAGE_START) {
                    showContentView();
                }
                if (mPullToRefreshLayout != null) {
                    mPullToRefreshLayout.finishTask(true);
                }
                hasLoadAllData = true;
                if (showFinishLoad) {
                    mListView.addFooterView(footerView);
                }
                setCanScrollUp(false);
                break;
            case MSG_UI_NO_DATA:
                // 数据加载成功,但数据条数为0
                if (mAdapter.getCount() > 0) {
                    if (mPullToRefreshLayout != null) {
                        mPullToRefreshLayout.finishTask(true);
                    }
                } else {
                    showNoDataView();
                }
                break;
        }
    }

    @Override
    public void reLoadData() {
        showLoadingView();
        hasLoadAllData = false;
        pageIndex = PAGE_START;
        if (showFinishLoad && mListView.getFooterViewsCount() > 0) {
            mListView.removeFooterView(footerView);
        }
        if (mAdapter.getCount()>0){
            mAdapter.clearAllData();
        }
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
    }

    @Override
    public void onLoadMore(PullDragHelper pullDragHelper) {
        pageIndex++;
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
    }

    @Override
    public void onRefresh(PullDragHelper pullDragHelper) {
        hasLoadAllData = false;
        pageIndex = PAGE_START;
        setCanScrollUp(true);
        if (showFinishLoad && mListView.getFooterViewsCount() > 0) {
            mListView.removeFooterView(footerView);
        }
        sendEmptyBackgroundMessage(MSG_BACK_LOAD_DATA);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void setCanScrollUp(boolean canScrollUp) {
        mListView.setCanScrollUp(canScrollUp);
    }

    public void setCanScrollDown(boolean canScrollDown) {
        mListView.setCanScrollDown(canScrollDown);
    }

    /**
     * 添加头部
     *
     * @param listView
     */
    protected void addHeaderView(ListView listView) {
    }

    /**
     * 创建适配器
     *
     * @return
     */
    protected abstract BaseListAdapter<T> createAdapter();

    /**
     * 加载数据，子类重写
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    protected abstract ArrayList<T> loadData(int pageIndex, int pageSize);

    /**
     * 处理返回数据
     *
     * @param list
     */
    protected ArrayList<T> dealData(ArrayList<T> list) {
        return list;
    }

    /**
     * 设置延时加载
     *
     * @param time
     */
    protected void setDelayLoad(long time) {
        delayLoadTime = time;
    }

    /**
     * 设置当所有数据加载完毕时是否移除footerView
     *
     * @param show
     */
    protected void setShowFinishLoad(boolean show) {
        showFinishLoad = show;
    }

    protected void setResultShowTime(long time) {
        mPullToRefreshLayout.setResultShowTime(time);
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
