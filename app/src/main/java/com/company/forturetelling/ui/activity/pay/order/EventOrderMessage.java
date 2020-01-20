package com.company.forturetelling.ui.activity.pay.order;

/**
 * Created by Lovelin on 2020/1/20
 * <p>
 * Describe:
 */
public class EventOrderMessage {
    private String  body;
    private String  out_trade_no;
    private String  total_fee;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
