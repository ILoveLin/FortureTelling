package com.company.forturetelling.bean;

/**
 * Created by Lovelin on 2019/12/12
 * <p>
 * Describe:
 */
public class RegisterBean {

    /**
     * status : 0
     * msg : ok
     * data : {"userid":59,"name":"巨思八字_59"}
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
         * userid : 59
         * name : 巨思八字_59
         */

        private String userid;
        private String name;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
