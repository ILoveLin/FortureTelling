package com.company.forturetelling.bean.sixtab;

/**
 * Created by Lovelin on 2019/12/23
 * <p>
 * Describe:
 */
public class EightNumBean01 {

    /**
     * status : 0
     * msg : ok
     * data : {"y":"2012","m":"12","d":"4","cY":148,"cM":1367,"cD":41255,"cH":494941,"term1":"22/(12月07日01:17)","term2":"23/(12月21日19:11)","start_term":2309120,"end_term":256660,"start_term1":"20/(11月07日08:34)","end_term1":"22/(12月07日01:17)","lDate":"二零一二年十月廿一日 02:00-02:59丑时","username":"余小","datadate":"2012-12-4","gender":"0","h":"2","i":0}
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
         * y : 2012
         * m : 12
         * d : 4
         * cY : 148
         * cM : 1367
         * cD : 41255
         * cH : 494941
         * term1 : 22/(12月07日01:17)
         * term2 : 23/(12月21日19:11)
         * start_term : 2309120
         * end_term : 256660
         * start_term1 : 20/(11月07日08:34)
         * end_term1 : 22/(12月07日01:17)
         * lDate : 二零一二年十月廿一日 02:00-02:59丑时
         * username : 余小
         * datadate : 2012-12-4
         * gender : 0
         * h : 2
         * i : 0
         */

        private String y;
        private String m;
        private String d;
        private int cY;
        private int cM;
        private int cD;
        private int cH;
        private String term1;
        private String term2;
        private int start_term;
        private int end_term;
        private String start_term1;
        private String end_term1;
        private String lDate;
        private String username;
        private String datadate;
        private String gender;
        private String h;
        private int i;
        private String xing;

        public String getXing() {
            return xing;
        }

        public void setXing(String xing) {
            this.xing = xing;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public int getCY() {
            return cY;
        }

        public void setCY(int cY) {
            this.cY = cY;
        }

        public int getCM() {
            return cM;
        }

        public void setCM(int cM) {
            this.cM = cM;
        }

        public int getCD() {
            return cD;
        }

        public void setCD(int cD) {
            this.cD = cD;
        }

        public int getCH() {
            return cH;
        }

        public void setCH(int cH) {
            this.cH = cH;
        }

        public String getTerm1() {
            return term1;
        }

        public void setTerm1(String term1) {
            this.term1 = term1;
        }

        public String getTerm2() {
            return term2;
        }

        public void setTerm2(String term2) {
            this.term2 = term2;
        }

        public int getStart_term() {
            return start_term;
        }

        public void setStart_term(int start_term) {
            this.start_term = start_term;
        }

        public int getEnd_term() {
            return end_term;
        }

        public void setEnd_term(int end_term) {
            this.end_term = end_term;
        }

        public String getStart_term1() {
            return start_term1;
        }

        public void setStart_term1(String start_term1) {
            this.start_term1 = start_term1;
        }

        public String getEnd_term1() {
            return end_term1;
        }

        public void setEnd_term1(String end_term1) {
            this.end_term1 = end_term1;
        }

        public String getLDate() {
            return lDate;
        }

        public void setLDate(String lDate) {
            this.lDate = lDate;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDatadate() {
            return datadate;
        }

        public void setDatadate(String datadate) {
            this.datadate = datadate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getH() {
            return h;
        }

        public void setH(String h) {
            this.h = h;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }
}
