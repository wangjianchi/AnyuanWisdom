package com.ayfp.anyuanwisdom.view.notice.bean;

import java.util.List;

/**
 * Created by 建池 on 2017/12/3.
 */

public class NoticeDetail {

    /**
     * affiche : {"id":"4","title":"4","author":"cc","add_time":"2017-11-29 10:18:01","html_content":"4sfafsa"}
     * read_all_count : 3
     * read_users : [{"real_name":"2xx","portrait":""},{"real_name":"3xx","portrait":""},{"real_name":"4xx","portrait":""}]
     * unread_all_count : 1
     * unread_users : [{"real_name":"5xx","portrait":""}]
     */

    private AfficheBean affiche;
    private String read_all_count;
    private int unread_all_count;
    private List<ReadUsersBean> read_users;
    private List<ReadUsersBean> unread_users;

    public AfficheBean getAffiche() {
        return affiche;
    }

    public void setAffiche(AfficheBean affiche) {
        this.affiche = affiche;
    }

    public String getRead_all_count() {
        return read_all_count;
    }

    public void setRead_all_count(String read_all_count) {
        this.read_all_count = read_all_count;
    }

    public int getUnread_all_count() {
        return unread_all_count;
    }

    public void setUnread_all_count(int unread_all_count) {
        this.unread_all_count = unread_all_count;
    }

    public List<ReadUsersBean> getRead_users() {
        return read_users;
    }

    public void setRead_users(List<ReadUsersBean> read_users) {
        this.read_users = read_users;
    }

    public List<ReadUsersBean> getUnread_users() {
        return unread_users;
    }

    public void setUnread_users(List<ReadUsersBean> unread_users) {
        this.unread_users = unread_users;
    }

    public static class AfficheBean {
        /**
         * id : 4
         * title : 4
         * author : cc
         * add_time : 2017-11-29 10:18:01
         * html_content : 4sfafsa
         */

        private String id;
        private String title;
        private String author;
        private String add_time;
        private String html_content;

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

        public String getHtml_content() {
            return html_content;
        }

        public void setHtml_content(String html_content) {
            this.html_content = html_content;
        }
    }

    public static class ReadUsersBean {
        /**
         * real_name : 2xx
         * portrait :
         */

        private String real_name;
        private String portrait;
        private int type;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
