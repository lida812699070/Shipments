package com.example.ok.shipments.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.utils.WrapperUtils;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

/**
 * Created by ${lida} on 2016/12/19.
 */
public class MyLoadMoreWrapperAdapter<T> extends LoadMoreWrapper implements View.OnClickListener {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;

    public MyLoadMoreWrapperAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
        mInnerAdapter = adapter;
    }

    public View getLoadMoreView() {
        return mLoadMoreView;
    }


    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }


    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            ViewHolder holder;
            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    private int loadStat = 0;
    public static final int LoadMoreing = 0;
    public static final int LoadMoreError = -1;
    public static final int LoadMoreEnd = 1;

    public int getLoadStat() {
        return loadStat;
    }

    public void setLoadStat(int loadStat) {
        this.loadStat = loadStat;
    }

    private ProgressBar progressBar;
    private TextView tv;
    private LoadMoreErrorListener mLoadMoreErrorListener;

    public void setLoadMoreErrorListener(LoadMoreErrorListener loadMoreErrorListener) {
        mLoadMoreErrorListener = loadMoreErrorListener;
    }

    @Override
    public void onClick(View v) {
        if (loadStat == LoadMoreError && mLoadMoreErrorListener != null)
            mLoadMoreErrorListener.loadErrorClick();
    }

    public interface LoadMoreErrorListener {
        void loadErrorClick();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            if (progressBar == null || tv == null) {
                ViewGroup loadMoreView = (ViewGroup) mLoadMoreView;
                progressBar = (ProgressBar) loadMoreView.getChildAt(0);
                tv = (TextView) loadMoreView.getChildAt(1);
            }
            if (mOnLoadMoreListener != null) {
                if (loadStat == LoadMoreing) {
                    progressBar.setVisibility(View.VISIBLE);
                    tv.setText("正在加载...");
                    mOnLoadMoreListener.onLoadMoreRequested();
                } else if (loadStat == LoadMoreEnd) {
                    progressBar.setVisibility(View.INVISIBLE);
                    tv.setText("已经加载到底部");
                } else if (loadStat == LoadMoreError) {
                    progressBar.setVisibility(View.INVISIBLE);
                    tv.setText("加载错误，请重新点击!");
                    mLoadMoreView.setOnClickListener(this);
                }
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface MyOnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private MyOnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(MyOnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }


    public LoadMoreWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }
}
