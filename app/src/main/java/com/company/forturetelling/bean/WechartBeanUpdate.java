package com.company.forturetelling.bean;

/**
 * Created by Lovelin on 2020/2/17
 * <p>
 * Describe:
 */
public class WechartBeanUpdate {

    /**
     * status : 1
     * msg : ok
     * data : {"openid":"1","name":"1212","gender":"1","headimg":"12121","province":"北京","city":"北京","birthday":"1997-6-8","userid":0,"username":"0","perfect":false}
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
         * openid : 1
         * name : 1212
         * gender : 1
         * headimg : 12121
         * province : 北京
         * city : 北京
         * birthday : 1997-6-8
         * userid : 0
         * username : 0
         * perfect : false
         */

        private String openid;
        private String name;
        private String gender;
        private String headimg;
        private String province;
        private String city;
        private String birthday;
        private int userid;
        private String username;
        private String perfect;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPerfect() {
            return perfect;
        }

        public void setPerfect(String perfect) {
            this.perfect = perfect;
        }
    }
}
