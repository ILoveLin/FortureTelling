package com.company.forturetelling.bean;

import java.util.List;

/**
 * Created by Lovelin on 2020/1/19
 * <p>
 * Describe:
 */
public class OrderBean {


    /**
     * status : 0
     * msg : ok
     * data : {"list":[{"id":87,"userid":1,"order_no":"202001181415159169213280","status":0,"title":"八字精辟","gmt_create":"","gmt_payment":"2020-01-18 15:39:13","create_date":"2020-01-18 15:39:13","type":0}],"count":21,"pagecount":3}
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
         * list : [{"id":87,"userid":1,"order_no":"202001181415159169213280","status":0,"title":"八字精辟","gmt_create":"","gmt_payment":"2020-01-18 15:39:13","create_date":"2020-01-18 15:39:13","type":0}]
         * count : 21
         * pagecount : 3
         */

        private String count;
        private String pagecount;
        private List<ListBean> list;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPagecount() {
            return pagecount;
        }

        public void setPagecount(String pagecount) {
            this.pagecount = pagecount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 87
             * userid : 1
             * order_no : 202001181415159169213280
             * status : 0
             * title : 八字精辟
             * gmt_create :
             * gmt_payment : 2020-01-18 15:39:13
             * create_date : 2020-01-18 15:39:13
             * type : 0
             */

            private String id;
            private String userid;
            private String order_no;
            private String status;
            private String title;
            private String gmt_create;
            private String gmt_payment;
            private String create_date;
            private String type;
            private String name;
            private String text_surname;
            private String text_name;
            private String text_all_name;

            public String getText_surname() {
                return text_surname;
            }

            public void setText_surname(String text_surname) {
                this.text_surname = text_surname;
            }

            public String getText_name() {
                return text_name;
            }

            public void setText_name(String text_name) {
                this.text_name = text_name;
            }

            public String getText_all_name() {
                return text_all_name;
            }

            public void setText_all_name(String text_all_name) {
                this.text_all_name = text_all_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getGmt_create() {
                return gmt_create;
            }

            public void setGmt_create(String gmt_create) {
                this.gmt_create = gmt_create;
            }

            public String getGmt_payment() {
                return gmt_payment;
            }

            public void setGmt_payment(String gmt_payment) {
                this.gmt_payment = gmt_payment;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
