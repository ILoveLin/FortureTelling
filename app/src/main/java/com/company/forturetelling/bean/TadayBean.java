package com.company.forturetelling.bean;

import java.util.List;

/**
 * Created by Lovelin on 2019/12/16
 * <p>
 * Describe:
 */
public class TadayBean {

    /**
     * status : 0
     * msg : ok
     * data : {"su1":89,"su2":5,"cw":"东方","color":"紫","cy":["否终斯泰","千载难逢","日进斗金","集思广益"],"sw":"核桃"}
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
         * su1 : 89
         * su2 : 5
         * cw : 东方
         * color : 紫
         * cy : ["否终斯泰","千载难逢","日进斗金","集思广益"]　
         * sw : 核桃
         */

        private int su1;
        private int su2;
        private String cw;
        private String color;
        private String sw;
        private List<String> cy;

        public int getSu1() {
            return su1;
        }

        public void setSu1(int su1) {
            this.su1 = su1;
        }

        public int getSu2() {
            return su2;
        }

        public void setSu2(int su2) {
            this.su2 = su2;
        }

        public String getCw() {
            return cw;
        }

        public void setCw(String cw) {
            this.cw = cw;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSw() {
            return sw;
        }

        public void setSw(String sw) {
            this.sw = sw;
        }

        public List<String> getCy() {
            return cy;
        }

        public void setCy(List<String> cy) {
            this.cy = cy;
        }
    }
}
