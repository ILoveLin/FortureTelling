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
        private String wechat_price;
        private String ali_price;

        public String getWechat_price() {
            return wechat_price;
        }

        public void setWechat_price(String wechat_price) {
            this.wechat_price = wechat_price;
        }

        public String getAli_price() {
            return ali_price;
        }

        public void setAli_price(String ali_price) {
            this.ali_price = ali_price;
        }


        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }
    }
}
