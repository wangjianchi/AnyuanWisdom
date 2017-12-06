package com.ayfp.anyuanwisdom.view.report.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.view.report.adapter.EventCategoryAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @author:: wangjianchi
 * @time: 2017/12/6  10:49.
 * @description:
 */

public class EventCategoryPopupWindow extends PopupWindow{
    private Context mContext;
    private View mView;
    private RecyclerView mRecyclerView;
    private EventCategoryAdapter mAdapter;
    private OnEventCategorySelect mListener;
    public EventCategoryPopupWindow(Context context,OnEventCategorySelect listener){
        this.mContext = context;
        this.mListener = listener;
        init();
        setPopupWindown();
    }
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(R.layout.popup_event_category,null);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
    }
    private void setPopupWindown(){
        this.setContentView(mView);
        this.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
        this.setHeight(RecyclerView.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mAdapter = new EventCategoryAdapter(AppCache.getInstance().getCategoryList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mListener != null){
                    mListener.categorySelect(AppCache.getInstance().getCategoryList().get(position));
                }
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface OnEventCategorySelect{
        void categorySelect(EventCategory eventCategory);
    }
}
