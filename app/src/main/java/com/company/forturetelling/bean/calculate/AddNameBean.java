package com.company.forturetelling.bean.calculate;

/**
 * Created by Lovelin on 2019/12/18
 * <p>
 * Describe:
 */
public class AddNameBean {

    /**
     * status : 0
     * msg : ok
     * data : {"orderNo":"CS157562005928043"}
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
         * orderNo : CS157562005928043
         */

        private String orderNo;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }
    }
}
