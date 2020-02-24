package com.company.forturetelling;

/**
 * Created by Lovelin on 2020/2/24
 * <p>
 * Describe:
 */
public class GetUseridBean {

    /**
     * status : 0
     * msg : ok
     * data : {"province":"北京","city":"北京","gender":0,"headimg":"/uploads/lunbo/mr.png","birthday":"1997-6-8","userid":"149","name":"巨思八字_149","username":"0"}
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
         * province : 北京
         * city : 北京
         * gender : 0
         * headimg : /uploads/lunbo/mr.png
         * birthday : 1997-6-8
         * userid : 149
         * name : 巨思八字_149
         * username : 0
         */

        private String province;
        private String city;
        private int gender;
        private String headimg;
        private String birthday;
        private String userid;
        private String name;
        private String username;

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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
