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
     * data : {"name":"乐观","gender":"1","headimg":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIpDnvrfGsKKxXBiccAVZMZK0qH79ybg48GU7IlMeMU8pNBSJwhv5vgib10q3NUVtm3EIgicHaSu8pnQ/132","province":"","birthday":"1997-6-8","city":"","userid":92,"username":""}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
         * name : 乐观
         * gender : 1
         * headimg : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIpDnvrfGsKKxXBiccAVZMZK0qH79ybg48GU7IlMeMU8pNBSJwhv5vgib10q3NUVtm3EIgicHaSu8pnQ/132
         * province :
         * birthday : 1997-6-8
         * city :
         * userid : 92
         * username :
         */

        private String name;
        private String gender;
        private String headimg;
        private String province;
        private String birthday;
        private String city;
        private int userid;
        private String username;

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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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
    }
}
