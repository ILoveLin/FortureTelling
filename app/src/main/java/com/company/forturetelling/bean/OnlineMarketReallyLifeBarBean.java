package com.company.forturetelling.bean;

/**
 * Created by Lovelin on 2020/6/4
 * <p>
 * Describe:
 */
public class OnlineMarketReallyLifeBarBean {


    /**
     * status : 0
     * msg : ok
     * data : {}
     */

    private String status;
    private String msg;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
