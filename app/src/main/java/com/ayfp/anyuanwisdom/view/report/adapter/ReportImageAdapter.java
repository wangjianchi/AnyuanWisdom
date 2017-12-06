package com.ayfp.anyuanwisdom.view.report.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.ayfp.anyuanwisdom.R;
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
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportImageBean item) {
        if (item.getType() != 1){
            ImageView imageView = helper.getView(R.id.iv_report_image);
            Bitmap bm = BitmapFactory.decodeFile(item.getImageFile());
            imageView.setImageBitmap(bm);

        }
    }
}
