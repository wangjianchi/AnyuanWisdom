package com.ayfp.anyuanwisdom.view.sign.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.view.sign.adapter.SignAddressAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/27  14:44.
 * @description:
 */

public class SignAddressPopupWindow extends PopupWindow{
    private Context mContext;
    private View mView;
    private RecyclerView mRecyclerView;
    private SignAddressAdapter mAdapter;
    private OnSignAddressSelect mListener;
    private List<String> addressStrs;
    public SignAddressPopupWindow(Context context,List<String> list,OnSignAddressSelect listener){
        this.mContext = context;
        this.addressStrs = list;
        this.mListener = listener;
        init();
        setPopupWindown();
    }
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(R.layout.popup_sign_address,null);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
    }

    private void setPopupWindown(){
        this.setContentView(mView);
        this.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
        this.setHeight(RecyclerView.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mAdapter = new SignAddressAdapter(addressStrs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mListener != null){
                    mListener.signAddressSelect(addressStrs.get(position));
                }
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface OnSignAddressSelect{
        void signAddressSelect(String address);
    }
}
