package com.company.forturetelling.bean.paybean;

/**
 * Created by Lovelin on 2020/1/17
 * <p>
 * Describe:
 */
public class AliPayOrderBean {

    /**
     * status : 0
     * msg : ok
     * data : {"sing":"app_id=2021001106601835&biz_content=%7B%22subject%22%3A%22%5Cu5de8%5Cu601d%5Cu516b%5Cu5b57%22%2C%22body%22%3A%22%5Cu7b97%5Cu547d%5Cu53d6%5Cu540d%22%2C%22out_trade_no%22%3A%22CS157570975756017%22%2C%22timeout_express%22%3A%2215m%22%2C%22total_amount%22%3A%221.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftestbazi.zgszfy.com%2Fapi%2Faop%2Finfo&sign=Djk9AsGQDnr0woazld1G2TmnEZAhX0eJn%2FypscgsCrjtj%2BrnUUWNlIlTDJjFYfVxkJrR7y2j6x2yPuoFwceU%2FuyvQKXb1mqUyQyagRqv2jTZ4DMwXoBTQ0I0qkIrQydWU3NgW4tj9soEvNmV1O8EF0A9bVJ%2FSkrHA3Zvfhj%2FvwiTsI7r0px%2B0bpxEln9LqQoHZO23qBqA%2BmrtYGgpwYsXMvClrxJeT5dRQaazZuMX0rFJ1HYlzAtrfnP96nT7HEidULTCV9pWwfCQ4Un8x17eeXWeeJ6jMG1UGNhyA7F%2B3%2BesfwM%2Fal2ED%2FgCWYvTACIX2DU6dxe7tlKUV8%2FhtVwsQ%3D%3D&sign_type=RSA2&timestamp=2020-01-17+09%3A22%3A45&version=1.0"}
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
         * sing : app_id=2021001106601835&biz_content=%7B%22subject%22%3A%22%5Cu5de8%5Cu601d%5Cu516b%5Cu5b57%22%2C%22body%22%3A%22%5Cu7b97%5Cu547d%5Cu53d6%5Cu540d%22%2C%22out_trade_no%22%3A%22CS157570975756017%22%2C%22timeout_express%22%3A%2215m%22%2C%22total_amount%22%3A%221.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftestbazi.zgszfy.com%2Fapi%2Faop%2Finfo&sign=Djk9AsGQDnr0woazld1G2TmnEZAhX0eJn%2FypscgsCrjtj%2BrnUUWNlIlTDJjFYfVxkJrR7y2j6x2yPuoFwceU%2FuyvQKXb1mqUyQyagRqv2jTZ4DMwXoBTQ0I0qkIrQydWU3NgW4tj9soEvNmV1O8EF0A9bVJ%2FSkrHA3Zvfhj%2FvwiTsI7r0px%2B0bpxEln9LqQoHZO23qBqA%2BmrtYGgpwYsXMvClrxJeT5dRQaazZuMX0rFJ1HYlzAtrfnP96nT7HEidULTCV9pWwfCQ4Un8x17eeXWeeJ6jMG1UGNhyA7F%2B3%2BesfwM%2Fal2ED%2FgCWYvTACIX2DU6dxe7tlKUV8%2FhtVwsQ%3D%3D&sign_type=RSA2&timestamp=2020-01-17+09%3A22%3A45&version=1.0
         */

        private String sing;
        private String notify_url;

        public String getSing() {
            return sing;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public void setSing(String sing) {
            this.sing = sing;
        }
    }
}
