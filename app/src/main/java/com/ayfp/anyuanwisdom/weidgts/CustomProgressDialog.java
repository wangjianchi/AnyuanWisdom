package com.ayfp.anyuanwisdom.weidgts;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.ayfp.anyuanwisdom.R;

/**
 * @author:: wangjianchi
 * @time: 2017/12/1  17:56.
 * @description:
 */

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context, R.style.CustomDialog);
        init();
    }
    private void init(){
        setContentView(R.layout.view_progress_bar);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }
}
