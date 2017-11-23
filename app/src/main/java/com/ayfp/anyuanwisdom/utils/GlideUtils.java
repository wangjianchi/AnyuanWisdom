package com.ayfp.anyuanwisdom.utils;

import android.widget.ImageView;

import com.ayfp.anyuanwisdom.base.MyApplication;
import com.bumptech.glide.Glide;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  14:48.
 * @description:
 */

public class GlideUtils {
    public static void loadImageView(String url, ImageView imageView){
        Glide.with(MyApplication.getContext())
                .load(url)
                .into(imageView);
    }
}
