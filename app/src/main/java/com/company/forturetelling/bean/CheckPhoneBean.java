package com.company.forturetelling.bean;

/**
 * Created by Lovelin on 2020/2/20
 * <p>
 * Describe:
 */
public class CheckPhoneBean {

    /**
     * status : 0
     * msg : ok
     * data : {"BizId":"567020782008144923^0"}
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
         * BizId : 567020782008144923^0
         */

        private String BizId;

        public String getBizId() {
            return BizId;
        }

        public void setBizId(String BizId) {
            this.BizId = BizId;
        }
    }
}
