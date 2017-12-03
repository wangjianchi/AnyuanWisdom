package com.ayfp.anyuanwisdom.view.notice.bean;

/**
 * @author:: wangjianchi
 * @time: 2017/11/29  14:39.
 * @description:
 */

public class NoticeListBean {

    /**
     * id : 6
     * title : 6
     * content : 6dad...
     * author : s
     * add_time : 2017-11-29 10:36:01
     * status : 0
     */

    private String id;
    private String title;
    private String content;
    private String author;
    private String add_time;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
