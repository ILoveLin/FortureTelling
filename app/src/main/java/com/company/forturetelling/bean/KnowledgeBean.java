package com.company.forturetelling.bean;

import java.util.List;

/**
 * Created by Lovelin on 2019/12/16
 * <p>
 * Describe:
 */
public class KnowledgeBean {


    /**
     * status : 0
     * msg : ok
     * data : {"wencontent":[{"id":45,"title":"八字为偏官格局的人和什么样的人是最为般配的？什么是偏官？","msg":"不论是谁也都是希望自己可以...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ0393aE.jpg","home":"/api/login/mia/id/45"},{"id":44,"title":"八字纯阳的人的婚姻怎么样？纯阳的人婚姻好不好？","msg":"不论是谁也都是希望自己的婚...","img":"static.k366.com/uploads/allimg/190807/10-1ZPG4434L32.jpg","home":"/api/login/mia/id/44"},{"id":43,"title":"八字为正财星纯正，缺乏食伤星的男性对待感情态度如何？","msg":"不同的人对待感情的态度也是...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ1005B10.jpg","home":"/api/login/mia/id/43"},{"id":42,"title":"女性的八字为伤官佩印是属于风情万种的？什么是伤官？","msg":"在现实的生活中，不少的女性...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ11IO41.jpg","home":"/api/login/mia/id/42"},{"id":41,"title":"男性的八字是财星不透天干，财星在日支会不会痴情？","msg":"每一个人的八字怎么样，对于...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ10JA13.jpg","home":"/api/login/mia/id/41"},{"id":40,"title":"八字的命局金旺或水旺的人会不会被人当枪用？八字金旺代表什么含义？","msg":"说到我们的命运，相信大家都...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ03420453.jpg","home":"/api/login/mia/id/40"},{"id":39,"title":"八字为正官格局的人和带有功利性的人在一起好不好？","msg":"不同的人也是和不同的人在一...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ05025154.jpg","home":"/api/login/mia/id/39"},{"id":38,"title":"八字中多食神、伤官的男子花心指数高不高？什么是食神？","msg":"有的男生是非常的花心的，而...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ14419505.jpg","home":"/api/login/mia/id/38"},{"id":37,"title":"壬辰年出生的朋友是什么命？长流水的爱情怎么样？","msg":"在不同的年份出生的朋友的命...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ20511146.jpg","home":"/api/login/mia/id/37"},{"id":35,"title":"用紫薇斗数怎么判断配偶的个性和身体健康状况？","msg":"不论是谁也都是希望自己的另...","img":"static.k366.com/uploads/allimg/190806/10-1ZP6110319542.jpg","home":"/api/login/mia/id/35"}],"page":1,"pagecount":3}
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
         * wencontent : [{"id":45,"title":"八字为偏官格局的人和什么样的人是最为般配的？什么是偏官？","msg":"不论是谁也都是希望自己可以...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ0393aE.jpg","home":"/api/login/mia/id/45"},{"id":44,"title":"八字纯阳的人的婚姻怎么样？纯阳的人婚姻好不好？","msg":"不论是谁也都是希望自己的婚...","img":"static.k366.com/uploads/allimg/190807/10-1ZPG4434L32.jpg","home":"/api/login/mia/id/44"},{"id":43,"title":"八字为正财星纯正，缺乏食伤星的男性对待感情态度如何？","msg":"不同的人对待感情的态度也是...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ1005B10.jpg","home":"/api/login/mia/id/43"},{"id":42,"title":"女性的八字为伤官佩印是属于风情万种的？什么是伤官？","msg":"在现实的生活中，不少的女性...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ11IO41.jpg","home":"/api/login/mia/id/42"},{"id":41,"title":"男性的八字是财星不透天干，财星在日支会不会痴情？","msg":"每一个人的八字怎么样，对于...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ10JA13.jpg","home":"/api/login/mia/id/41"},{"id":40,"title":"八字的命局金旺或水旺的人会不会被人当枪用？八字金旺代表什么含义？","msg":"说到我们的命运，相信大家都...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ03420453.jpg","home":"/api/login/mia/id/40"},{"id":39,"title":"八字为正官格局的人和带有功利性的人在一起好不好？","msg":"不同的人也是和不同的人在一...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ05025154.jpg","home":"/api/login/mia/id/39"},{"id":38,"title":"八字中多食神、伤官的男子花心指数高不高？什么是食神？","msg":"有的男生是非常的花心的，而...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ14419505.jpg","home":"/api/login/mia/id/38"},{"id":37,"title":"壬辰年出生的朋友是什么命？长流水的爱情怎么样？","msg":"在不同的年份出生的朋友的命...","img":"static.k366.com/uploads/allimg/190808/10-1ZPQ20511146.jpg","home":"/api/login/mia/id/37"},{"id":35,"title":"用紫薇斗数怎么判断配偶的个性和身体健康状况？","msg":"不论是谁也都是希望自己的另...","img":"static.k366.com/uploads/allimg/190806/10-1ZP6110319542.jpg","home":"/api/login/mia/id/35"}]
         * page : 1
         * pagecount : 3
         */

        private int page;
        private int pagecount;
        private List<WencontentBean> wencontent;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPagecount() {
            return pagecount;
        }

        public void setPagecount(int pagecount) {
            this.pagecount = pagecount;
        }

        public List<WencontentBean> getWencontent() {
            return wencontent;
        }

        public void setWencontent(List<WencontentBean> wencontent) {
            this.wencontent = wencontent;
        }

        public static class WencontentBean {
            /**
             * id : 45
             * title : 八字为偏官格局的人和什么样的人是最为般配的？什么是偏官？
             * msg : 不论是谁也都是希望自己可以...
             * img : static.k366.com/uploads/allimg/190808/10-1ZPQ0393aE.jpg
             * home : /api/login/mia/id/45
             */

            private int id;
            private String title;
            private String msg;
            private String img;
            private String home;
            private String bq;
            private String date;

            public String getBq() {
                return bq;
            }

            public void setBq(String bq) {
                this.bq = bq;
            }

            public String getData() {
                return date;
            }

            public void setData(String data) {
                this.date = data;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getHome() {
                return home;
            }

            public void setHome(String home) {
                this.home = home;
            }
        }
    }
}
