package com.ayfp.anyuanwisdom.view.personal.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.view.personal.bean.PersonEditBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/11  14:25.
 * @description:
 */

public class PersonEditAdapter extends BaseQuickAdapter<PersonEditBean,BaseViewHolder> {
    public PersonEditAdapter( @Nullable List<PersonEditBean> data) {
        super(R.layout.item_edit_person, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonEditBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        TextView textContent = helper.getView(R.id.tv_content);
        if (item.isEdit()){
            textContent.setCompoundDrawablesWithIntrinsicBounds(null,null,mContext.getResources().getDrawable(R.mipmap.icon_right_triangle),null);
        }else {
            textContent.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
        if (!item.isImage()){
            helper.setVisible(R.id.iv_head,false);
            textContent.setText(item.getName());
        }else {
            helper.setVisible(R.id.iv_head,true);
            textContent.setText("");
            GlideUtils.loadImageViewErr(item.getImage_url(),(ImageView) helper.getView(R.id.iv_head),R.mipmap.image_head);
        }
    }

}
