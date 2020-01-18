package com.company.forturetelling.bean.calculate;

import java.util.List;

/**
 * Created by Lovelin on 2019/12/18
 * <p>
 * Describe:
 */
public class NameDetailsBean {
    /**
     * status : 0
     * msg : ok
     * data : {"info":{"name":"余","gender":"男","birthday":"2018-16-6","birthtime":0,"birthmin":0,"phone":"15756174513032092","ver":"","state":0,"datatype":1,"birthday2":["2019","02","10"]},"nongli":["正月","初六","己亥年","子时"],"bazi":["己酉","丙子","辛巳","壬辰"],"fullname":[{"ming":"明刚","score":100},{"ming":"昌泽","score":100},{"ming":"尚刚","score":100},{"ming":"岩城","score":100},{"ming":"青凌","score":100},{"ming":"松良","score":100},{"ming":"岩轩","score":100},{"ming":"宜泽","score":100},{"ming":"尚轩","score":100},{"ming":"金城","score":100}],"pagecount":10,"page":1}
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
         * info : {"name":"余","gender":"男","birthday":"2018-16-6","birthtime":0,"birthmin":0,"phone":"15756174513032092","ver":"","state":0,"datatype":1,"birthday2":["2019","02","10"]}
         * nongli : ["正月","初六","己亥年","子时"]
         * bazi : ["己酉","丙子","辛巳","壬辰"]
         * fullname : [{"ming":"明刚","score":100},{"ming":"昌泽","score":100},{"ming":"尚刚","score":100},{"ming":"岩城","score":100},{"ming":"青凌","score":100},{"ming":"松良","score":100},{"ming":"岩轩","score":100},{"ming":"宜泽","score":100},{"ming":"尚轩","score":100},{"ming":"金城","score":100}]
         * pagecount : 10
         * page : 1
         */

        private InfoBean info;
        private int pagecount;
        private int page;
        private List<String> nongli;
        private List<String> bazi;
        private List<FullnameBean> fullname;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public int getPagecount() {
            return pagecount;
        }

        public void setPagecount(int pagecount) {
            this.pagecount = pagecount;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<String> getNongli() {
            return nongli;
        }

        public void setNongli(List<String> nongli) {
            this.nongli = nongli;
        }

        public List<String> getBazi() {
            return bazi;
        }

        public void setBazi(List<String> bazi) {
            this.bazi = bazi;
        }

        public List<FullnameBean> getFullname() {
            return fullname;
        }

        public void setFullname(List<FullnameBean> fullname) {
            this.fullname = fullname;
        }

        public static class InfoBean {
            /**
             * name : 余
             * gender : 男
             * birthday : 2018-16-6
             * birthtime : 0
             * birthmin : 0
             * phone : 15756174513032092
             * ver :
             * state : 0
             * datatype : 1
             * birthday2 : ["2019","02","10"]
             */

            private String name;
            private String gender;
            private String birthday;
            private int birthtime;
            private int birthmin;
            private String phone;
            private String ver;
            private int state;
            private int datatype;
            private List<String> birthday2;

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

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public int getBirthtime() {
                return birthtime;
            }

            public void setBirthtime(int birthtime) {
                this.birthtime = birthtime;
            }

            public int getBirthmin() {
                return birthmin;
            }

            public void setBirthmin(int birthmin) {
                this.birthmin = birthmin;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getVer() {
                return ver;
            }

            public void setVer(String ver) {
                this.ver = ver;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getDatatype() {
                return datatype;
            }

            public void setDatatype(int datatype) {
                this.datatype = datatype;
            }

            public List<String> getBirthday2() {
                return birthday2;
            }

            public void setBirthday2(List<String> birthday2) {
                this.birthday2 = birthday2;
            }
        }

        public static class FullnameBean {
            /**
             * ming : 明刚
             * score : 100
             */

            private String ming;
            private int score;

            public String getMing() {
                return ming;
            }

            public void setMing(String ming) {
                this.ming = ming;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }
        }
    }
}
