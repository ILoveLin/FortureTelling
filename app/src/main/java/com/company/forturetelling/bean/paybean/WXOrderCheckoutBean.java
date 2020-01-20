package com.company.forturetelling.bean.paybean;

/**
 * Created by Lovelin on 2020/1/15
 * <p>
 * Describe:微信二次校验
 */
public class WXOrderCheckoutBean {

    /**
     * status : -1
     * msg : error
     * data : {"id":142,"total":"0.01","out_trade_no":"202001201200301119249400","body":"八字精辟","return_code":"FAIL","return_msg":"ok"}
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
         * id : 142
         * total : 0.01
         * out_trade_no : 202001201200301119249400
         * body : 八字精辟
         * return_code : FAIL
         * return_msg : ok
         */

        private String id;
        private String total;
        private String out_trade_no;
        private String body;
        private String return_code;
        private String return_msg;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }
    }
}
