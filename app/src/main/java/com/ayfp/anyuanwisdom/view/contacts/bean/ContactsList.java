package com.ayfp.anyuanwisdom.view.contacts.bean;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/7  16:59.
 * @description:
 */

public class ContactsList {

    /**
     * name : 版石镇
     * list : [{"name":"安信村","list":[{"user_id":"2","real_name":"2xx","job_position":"书记","portrait":"http://www.etiyi.com.cn/fp/data/files/portrait/2.jpg?v=1512552913"}]},{"name":"海螺村","list":[]}]
     */

    private String name;
    private List<ListBeanX> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBeanX> getList() {
        return list;
    }

    public void setList(List<ListBeanX> list) {
        this.list = list;
    }

    public static class ListBeanX {
        /**
         * name : 安信村
         * list : [{"user_id":"2","real_name":"2xx","job_position":"书记","portrait":"http://www.etiyi.com.cn/fp/data/files/portrait/2.jpg?v=1512552913"}]
         */

        private String name;
        private List<Person> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Person> getList() {
            return list;
        }

        public void setList(List<Person> list) {
            this.list = list;
        }

    }
}
