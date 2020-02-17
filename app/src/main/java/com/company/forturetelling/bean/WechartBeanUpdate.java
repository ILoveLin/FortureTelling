package com.company.forturetelling.bean;

/**
 * Created by Lovelin on 2020/2/17
 * <p>
 * Describe:
 */
public class WechartBeanUpdate {
    /**
     * status : 0
     * msg : ok
     * data : {}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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


    /**
     * status : 0
     * msg : ok
     * data : {"userid":74}
     */

}
