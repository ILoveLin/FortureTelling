package com.company.forturetelling.bean;

/**
 * Created by Lovelin on 2019/12/25
 * <p>
 * Describe:
 */
public class LifeBean {


    /**
     * status : 0
     * msg : ok
     * data : {"love":"你要接受这世上总有突如其来的失去。洒了的牛奶，遗失的钱包，走散的爱人，断掉的友情\u2026当你做什么都于事无补时，唯一能做的，就是努力让自己好过一点。丢都丢了，就别再哭了。","cause":"男子为了各自家庭而承担的工作，是努力支撑、发展和维护他们的家；至于女子呢？则是努力维护家庭的秩序，家庭的安适和家庭的可爱。","wealths":"蜜蜂嗡嗡叫，好运时缠绕。青蛙池中舞，忧愁全消无。百花红正艳，快乐常相伴。荷塘月色秀，财运永不休。天空风筝飘，心情无限妙。柳絮满天飞，浪漫又陶醉。铃音及时到，问声早上好。快快快起床，今天真美好！"}
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
         * love : 你要接受这世上总有突如其来的失去。洒了的牛奶，遗失的钱包，走散的爱人，断掉的友情…当你做什么都于事无补时，唯一能做的，就是努力让自己好过一点。丢都丢了，就别再哭了。
         * cause : 男子为了各自家庭而承担的工作，是努力支撑、发展和维护他们的家；至于女子呢？则是努力维护家庭的秩序，家庭的安适和家庭的可爱。
         * wealths : 蜜蜂嗡嗡叫，好运时缠绕。青蛙池中舞，忧愁全消无。百花红正艳，快乐常相伴。荷塘月色秀，财运永不休。天空风筝飘，心情无限妙。柳絮满天飞，浪漫又陶醉。铃音及时到，问声早上好。快快快起床，今天真美好！
         */

        private String love;
        private String cause;
        private String wealths;

        public String getLove() {
            return love;
        }

        public void setLove(String love) {
            this.love = love;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public String getWealths() {
            return wealths;
        }

        public void setWealths(String wealths) {
            this.wealths = wealths;
        }
    }
}
