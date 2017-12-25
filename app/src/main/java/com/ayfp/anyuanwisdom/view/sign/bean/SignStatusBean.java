package com.ayfp.anyuanwisdom.view.sign.bean;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/25  15:36.
 * @description:
 */

public class SignStatusBean {

    /**
     * sign_status : 2
     * sign_id : 21
     * content : 走访
     * sign_in_time : 2017-12-25 11:14:00
     * sign_in_locate : 115.448062,25.251878
     * sign_in_address : 赣州市安远县版石镇安信村小学
     * sign_in_imgs : ["http://www.etiyi.com.cn/fp/data/files/sign/171225/21/1_0.jpg?v=1514171640","http://www.etiyi.com.cn/fp/data/files/sign/171225/21/1_1.jpg?v=1514171640"]
     * sign_out_time : 2017-12-25 11:15:24
     * sign_out_locate : 115.448206,25.247106
     * sign_out_address : 赣州市安远县版石镇安信村小学
     * sign_out_imgs : ["http://www.etiyi.com.cn/fp/data/files/sign/171225/21/2_0.jpg?v=1514171724","http://www.etiyi.com.cn/fp/data/files/sign/171225/21/2_1.jpg?v=1514171724"]
     * locate_path_url : http://www.etiyi.com.cn/fp/data/files/sign_path/21.jpeg?v=1514171724
     */

    private int sign_status;
    private int sign_id;
    private String content;
    private String sign_in_time;
    private String sign_in_locate;
    private String sign_in_address;
    private String sign_out_time;
    private String sign_out_locate;
    private String sign_out_address;
    private String locate_path_url;
    private List<String> sign_in_imgs;
    private List<String> sign_out_imgs;

    public int getSign_status() {
        return sign_status;
    }

    public void setSign_status(int sign_status) {
        this.sign_status = sign_status;
    }

    public int getSign_id() {
        return sign_id;
    }

    public void setSign_id(int sign_id) {
        this.sign_id = sign_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign_in_time() {
        return sign_in_time;
    }

    public void setSign_in_time(String sign_in_time) {
        this.sign_in_time = sign_in_time;
    }

    public String getSign_in_locate() {
        return sign_in_locate;
    }

    public void setSign_in_locate(String sign_in_locate) {
        this.sign_in_locate = sign_in_locate;
    }

    public String getSign_in_address() {
        return sign_in_address;
    }

    public void setSign_in_address(String sign_in_address) {
        this.sign_in_address = sign_in_address;
    }

    public String getSign_out_time() {
        return sign_out_time;
    }

    public void setSign_out_time(String sign_out_time) {
        this.sign_out_time = sign_out_time;
    }

    public String getSign_out_locate() {
        return sign_out_locate;
    }

    public void setSign_out_locate(String sign_out_locate) {
        this.sign_out_locate = sign_out_locate;
    }

    public String getSign_out_address() {
        return sign_out_address;
    }

    public void setSign_out_address(String sign_out_address) {
        this.sign_out_address = sign_out_address;
    }

    public String getLocate_path_url() {
        return locate_path_url;
    }

    public void setLocate_path_url(String locate_path_url) {
        this.locate_path_url = locate_path_url;
    }

    public List<String> getSign_in_imgs() {
        return sign_in_imgs;
    }

    public void setSign_in_imgs(List<String> sign_in_imgs) {
        this.sign_in_imgs = sign_in_imgs;
    }

    public List<String> getSign_out_imgs() {
        return sign_out_imgs;
    }

    public void setSign_out_imgs(List<String> sign_out_imgs) {
        this.sign_out_imgs = sign_out_imgs;
    }
}
