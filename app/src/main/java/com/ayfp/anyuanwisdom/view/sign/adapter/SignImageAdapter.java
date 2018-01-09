package com.ayfp.anyuanwisdom.view.sign.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/25  17:04.
 * @description:
 */

public class SignImageAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public static final String TAKEPHOTO = "take_photo";
    public SignImageAdapter( @Nullable List<String> data) {
        super(R.layout.item_sign_image, data);
        onClick();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item.equals(TAKEPHOTO)){
            GlideUtils.loadImageViewRes(R.mipmap.icon_upload_pic,(ImageView) helper.getView(R.id.iv_sign_image));
            helper.setVisible(R.id.iv_delete,false);
        }else if (item.startsWith("http")){
            GlideUtils.loadImageViewErr(item,(ImageView) helper.getView(R.id.iv_sign_image),R.mipmap.icon_upload_pic);
            helper.setVisible(R.id.iv_delete,false);
        }else {
            Bitmap bm = BitmapFactory.decodeFile(item);
            helper.setImageBitmap(R.id.iv_sign_image,bm);
            helper.setVisible(R.id.iv_delete,true);
            helper.addOnClickListener(R.id.iv_delete);
        }
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
