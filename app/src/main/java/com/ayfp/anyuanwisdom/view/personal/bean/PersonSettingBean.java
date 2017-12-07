package com.ayfp.anyuanwisdom.view.personal.bean;

import android.graphics.drawable.Drawable;

/**
 * @author:: wangjianchi
 * @time: 2017/12/7  11:48.
 * @description:
 */

public class PersonSettingBean {
    private int id;
    private String name;
    private Drawable mDrawable;
    private boolean diliver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public boolean isDiliver() {
        return diliver;
    }

    public void setDiliver(boolean diliver) {
        this.diliver = diliver;
    }
}
