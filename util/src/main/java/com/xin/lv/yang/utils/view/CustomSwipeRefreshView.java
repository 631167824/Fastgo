package com.xin.lv.yang.utils.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.xin.lv.yang.utils.listviewside.SwipeMenuListView;
import com.xin.lv.yang.utils.pull.PullToZoomListViewEx;

/**
 * 自定义下拉刷新 用户子项为 ListView    PullToZoomListViewEx   SwipeMenuListView
 */
public class CustomSwipeRefreshView extends SwipeRefreshLayout {

    View footView;   // 底部加载更多的view
    Context context;

    ListView listView;
    PullToZoomListViewEx pullListView;
    SwipeMenuListView swipeMenuListView;

    private int mScaledTouchSlop;
    private boolean isLoading;
    private OnLoadListener mOnLoadListener;

    public static final int LISTVIEW_CODE = 1;
    public static final int PULL_LISTVIEW_CODE = 2;
    public static final int SWIPEMENULISTVIEW = 3;


    public CustomSwipeRefreshView(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    public CustomSwipeRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setColorSchemeColors(context.getResources().getColor(android.R.color.holo_red_light),
                context.getResources().getColor(android.R.color.holo_green_light),
                context.getResources().getColor(android.R.color.holo_blue_bright),
                context.getResources().getColor(android.R.color.holo_orange_light)
        );

    }

    /**
     * 设置底部加载
     *
     * @param id view视图的id
     */
    public void setFootView(int id) {
        footView = View.inflate(context, id, null);
    }


    /**
     * 设置加载状态，是否加载传入boolean值进行判断
     */
    public void setLoading(boolean loading, int code) {
        // 修改当前的状态
        isLoading = loading;
        switch (code) {
            case LISTVIEW_CODE:
                listView = (ListView) getChildAt(0);
                if (isLoading) {
                    // 显示布局
                    if (footView != null && listView != null) {
                        listView.addFooterView(footView);
                        listView.setFocusable(false);
                    }
                } else {
                    // 隐藏布局
                    if (footView != null && listView != null) {
                        listView.removeFooterView(footView);
                        listView.setFocusable(true);
                    }
                }
                break;
            case PULL_LISTVIEW_CODE:
                pullListView = (PullToZoomListViewEx) getChildAt(0);
                if (isLoading) {
                    // 显示布局
                    if (footView != null && pullListView != null) {
                        pullListView.setFootView(footView);
                    }
                } else {
                    // 隐藏布局
                    if (footView != null && pullListView != null) {
                        pullListView.removeFootView(footView);
                    }
                }
                break;
            case SWIPEMENULISTVIEW:
                swipeMenuListView = (SwipeMenuListView) getChildAt(0);

                if (isLoading) {
                    // 显示布局
                    if (footView != null && swipeMenuListView != null) {
                        swipeMenuListView.addFooterView(footView);
                    }
                } else {
                    // 隐藏布局
                    if (footView != null && pullListView != null) {
                        swipeMenuListView.removeFooterView(footView);
                    }
                }
                break;
        }


    }


    /**
     * 上拉加载的接口回调
     */

    public interface OnLoadListener {
        void onLoad();

    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }
}
