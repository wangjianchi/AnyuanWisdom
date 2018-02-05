package com.ayfp.anyuanwisdom.view.report.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.view.report.bean.ReportImageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  16:18.
 * @description:
 */

public class ReportImageAdapter extends BaseQuickAdapter<ReportImageBean,BaseViewHolder> {
    public ReportImageAdapter(@Nullable List<ReportImageBean> data) {
        super(R.layout.item_report_image, data);
        onClick();
    }

    @Override
    protected void convert(BaseViewHolder helper, final ReportImageBean item) {
        ImageView imageView = helper.getView(R.id.iv_report_image);
        if (item.getType() == 2){
            Bitmap bm = BitmapFactory.decodeFile(item.getImageFile());
            imageView.setImageBitmap(bm);
        }else if (item.getType() == 1){
            imageView.setImageResource(R.mipmap.icon_upload_pic);
        }else if (item.getType() == 3){
            GlideUtils.loadImageView(item.getImageUrl(),imageView);
        }
        helper.setVisible(R.id.iv_delete,item.isDelete());
        helper.addOnClickListener(R.id.iv_delete);
    }
    private void onClick(){
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mData.remove(position);
                notifyItemRemoved(position);
            }
        });
    }
}
