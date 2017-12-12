package com.ayfp.anyuanwisdom.view.report.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.bean.Town;
import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.view.report.adapter.EventTownAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/12  14:57.
 * @description:
 */

public class EventTownPopupWindow extends PopupWindow{
    private Context mContext;
    private View mView;
    private RecyclerView mRecyclerView;
    private EventTownAdapter mAdapter;
    private List<Town> mTowns = new ArrayList<>();
    private int mTownId;
    private int mVillageId;
    private TextView mTextTown,mTextVillage;
    private OnTownAndVillageSelectListener mListener;
    public EventTownPopupWindow(Context context,OnTownAndVillageSelectListener listener){
        this.mContext = context;
        this.mListener = listener;
        init();
        setPopupWindown();
    }
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(R.layout.popup_event_town,null);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        mTextTown = mView.findViewById(R.id.tv_select_town);
        mTextVillage = mView.findViewById(R.id.tv_select_village);
    }
    private void setPopupWindown(){
        this.setContentView(mView);
        this.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
        this.setHeight(RecyclerView.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mTowns.addAll(AppCache.getInstance().getTowns());
        mAdapter = new EventTownAdapter(mTowns);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mAdapter.isVillage()){
                    mTownId = mTowns.get(position).getId();
                    getVillageData(mTownId);
                }else {
                    mVillageId = mTowns.get(position).getId();
                    mListener.selectTownAndVillage(mTownId,mVillageId);
                    dismiss();
                }
            }
        });
    }

    private void getVillageData(int townId){
        RetrofitService.getApi().getVillageOptions(RetrofitService.TOKEN,townId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AppResultData<List<Town>>>() {
                    @Override
                    public void loadSuccess(AppResultData<List<Town>> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            mTextTown.setTextColor(mContext.getResources().getColor(R.color.text_color_666666));
                            mTextVillage.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                            mTowns.clear();
                            mTowns.addAll(data.getResult());
                            mAdapter.setVillage(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface OnTownAndVillageSelectListener{
        void selectTownAndVillage(int townId,int villageId);
    }
}
