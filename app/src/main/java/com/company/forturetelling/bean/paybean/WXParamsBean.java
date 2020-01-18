package com.company.forturetelling.bean.paybean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lovelin on 2020/1/15
 * <p>
 * Describe:参数Bean
 */
public class WXParamsBean {

    /**
     * status : 0
     * msg : ok
     * data : {"appid":"wx4b9e09ae470dc4ce","partnerid":"1574217601","prepayid":"wx1511151376797319615893aa1671254800","noncestr":"4206aa74599401b3057dfd0bd8971173","timestamp":1579058113,"package":"Sign=WXPay","sign":"CB9540ADF4A70CD0EED057A4DBE3D604"}
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
         * appid : wx4b9e09ae470dc4ce
         * partnerid : 1574217601
         * prepayid : wx1511151376797319615893aa1671254800
         * noncestr : 4206aa74599401b3057dfd0bd8971173
         * timestamp : 1579058113
         * package : Sign=WXPay
         * sign : CB9540ADF4A70CD0EED057A4DBE3D604
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;
        @SerializedName("package")
        private String packageX;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
