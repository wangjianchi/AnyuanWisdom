package com.ayfp.anyuanwisdom.view.personal.bean;

/**
 * @author:: wangjianchi
 * @time: 2017/12/11  14:20.
 * @description:
 */

public class PersonEditBean {
    private String title;
    private String name;
    private String image_url;
    private boolean edit;
    private boolean image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
}
