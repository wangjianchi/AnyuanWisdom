package com.ayfp.anyuanwisdom.view.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cd14 on 2017/8/29.
 */

public class GalleryAdapter extends PagerAdapter {
    private List<NoticeListBean> mData;
    private List<View> views = new ArrayList<>();
    private Context context;
    private OnClickListener listener;
    public GalleryAdapter(List<NoticeListBean> list, Context context){
        this.mData = list;
        this.context = context;
    }
    @Override
    public int getCount() {
       return   mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        NoticeListBean data = mData.get(position);
        View view = View.inflate(context, R.layout.item_home_notice,null);
        TextView textTitle = (TextView)view.findViewById(R.id.tv_title);
        TextView textUnread= (TextView)view.findViewById(R.id.tv_unread);
        TextView textContent= (TextView)view.findViewById(R.id.tv_content);
        textTitle.setText(data.getTitle());
        textContent.setText(data.getContent());
        if (data.getStatus() == 0){
            textUnread.setVisibility(View.VISIBLE);
            textTitle.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.icon_dot_red),null,null,null);
        }else {
            textUnread.setVisibility(View.GONE);
            textTitle.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
        container.addView(view);
        view.setTag(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener{
        void onClick(int position);
    }
}
