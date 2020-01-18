package com.company.forturetelling.bean.paybean;

/**
 * Created by Lovelin on 2020/1/17
 * <p>
 * Describe:
 */
public class AliPaySecondResultBean {

    /**
     * status : 0
     * msg : ok
     * data : {"status":1,"msg":"sad"}
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
        /**
         * status : 1
         * msg : sad
         */

        private int status;
        private String msg;

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
    }
}
