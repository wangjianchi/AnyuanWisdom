package com.ayfp.anyuanwisdom.retrofit;

/**
 * @author:: wangjianchi
 * @time: 2017/12/1  16:19.
 * @description:
 */

public class AppResultData<T> {

    /**
     * status : 200
     * statusMsg : login success
     * result : {"user_id":"13990181916"}
     */

    private int status;
    private String statusMsg;
    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
