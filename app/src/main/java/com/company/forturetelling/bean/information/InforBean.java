package com.company.forturetelling.bean.information;

import java.util.List;

/**
 * Created by Lovelin on 2019/12/13
 * <p>
 * Describe:
 */
public class InforBean {

    /**
     * status : 0
     * msg : ok
     * data : {"info":{"userid":1,"username":"余钟鑫","gender":1,"birthday":"1997-9-1","headimg":"1212121","province":"","city":"","datess":"2019-12-11 09:36:58","zhongheids":["春风得意","金榜题名","扶摇直上","淡泊名利","热心助人","善良温柔"],"astroid":1,"jmsht":"木"},"jinriyunsi":{"su1":81,"su2":0,"cw":"东方","color":"黑","cy":["近水楼台","乐天达观","平心而论","精明强干"],"sw":"黑豆"},"xin_zhuo":{"ys":["3","2","5","3"],"xin_name":"处女座"}}
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
         * info : {"userid":1,"username":"余钟鑫","gender":1,"birthday":"1997-9-1","headimg":"1212121","province":"","city":"","datess":"2019-12-11 09:36:58","zhongheids":["春风得意","金榜题名","扶摇直上","淡泊名利","热心助人","善良温柔"],"astroid":1,"jmsht":"木"}
         * jinriyunsi : {"su1":81,"su2":0,"cw":"东方","color":"黑","cy":["近水楼台","乐天达观","平心而论","精明强干"],"sw":"黑豆"}
         * xin_zhuo : {"ys":["3","2","5","3"],"xin_name":"处女座"}
         */

        private InfoBean info;
        private JinriyunsiBean jinriyunsi;
        private XinZhuoBean xin_zhuo;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public JinriyunsiBean getJinriyunsi() {
            return jinriyunsi;
        }

        public void setJinriyunsi(JinriyunsiBean jinriyunsi) {
            this.jinriyunsi = jinriyunsi;
        }

        public XinZhuoBean getXin_zhuo() {
            return xin_zhuo;
        }

        public void setXin_zhuo(XinZhuoBean xin_zhuo) {
            this.xin_zhuo = xin_zhuo;
        }

        public static class InfoBean {
            /**
             * userid : 1
             * username : 余钟鑫
             * gender : 1
             * birthday : 1997-9-1
             * headimg : 1212121
             * province :
             * city :
             * datess : 2019-12-11 09:36:58
             * zhongheids : ["春风得意","金榜题名","扶摇直上","淡泊名利","热心助人","善良温柔"]
             * astroid : 1
             * jmsht : 木
             */

            private int userid;
            private String username;
            private int gender;
            private String birthday;
            private String headimg;
            private String province;
            private String city;
            private String datess;
            private String name;
            private int astroid;
            private String jmsht;
            private List<String> zhongheids;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
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

            public String getDatess() {
                return datess;
            }

            public void setDatess(String datess) {
                this.datess = datess;
            }

            public int getAstroid() {
                return astroid;
            }

            public void setAstroid(int astroid) {
                this.astroid = astroid;
            }

            public String getJmsht() {
                return jmsht;
            }

            public void setJmsht(String jmsht) {
                this.jmsht = jmsht;
            }

            public List<String> getZhongheids() {
                return zhongheids;
            }

            public void setZhongheids(List<String> zhongheids) {
                this.zhongheids = zhongheids;
            }
        }

        public static class JinriyunsiBean {
            /**
             * su1 : 81
             * su2 : 0
             * cw : 东方
             * color : 黑
             * cy : ["近水楼台","乐天达观","平心而论","精明强干"]
             * sw : 黑豆
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

        public static class XinZhuoBean {
            /**
             * ys : ["3","2","5","3"]
             * xin_name : 处女座
             */

            private String xin_name;
            private String star_url;
            private List<String> ys;

            public String getStar_url() {
                return star_url;
            }

            public void setStar_url(String star_url) {
                this.star_url = star_url;
            }

            public String getXin_name() {
                return xin_name;
            }

            public void setXin_name(String xin_name) {
                this.xin_name = xin_name;
            }

            public List<String> getYs() {
                return ys;
            }

            public void setYs(List<String> ys) {
                this.ys = ys;
            }
        }
    }
}
