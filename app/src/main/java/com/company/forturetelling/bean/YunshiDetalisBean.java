package com.company.forturetelling.bean;

/**
 * Created by Lovelin on 2019/12/17
 * <p>
 * Describe:运势详情
 */
public class YunshiDetalisBean {

    /**
     * status : 0
     * msg : ok
     * data : {"id":50,"year":"上半年收入还算稳定，但也不太会有意料之外的收入，需要花钱的地方倒是不少，一不小心就会有入不敷出的感觉。1月到3月初，因为火星在危机宫位穿行，你会因为为未来投资而花上不少，工作上的收入还算不错，勉强略略弥补亏空。3月到4月相对轻松一些，但也因此无心工作，反而花费不少在旅游和聚会上。整个5月将是上半年财运最旺的时节，这个月内相应的意外开始也会减少，让你们过上一段丰衣足食的生活。6月则不得不为一些之前的麻烦买单。进入下半年后，7月底到8月中将有一波不错的偏财运，部分个人可以通过投资获利，另一些则可能通过朋友接到一些私活，或是通过个人兴趣爱好赚上一笔。10月之后，需要花钱的地方明显减少，事业开始平稳发展也让收入开始有质的飞跃，如有可能尽量做些积蓄，好为未来潜在的发展可能储备力量。","today":"今天是你活动非常丰富的一天，特别是各种社交朋友找上门来让你应接不暇，伴侣之间也可能产生许多小情趣，做一顿美味的料理，准备一场新鲜有趣的约会，就能捕获他的心哦。","week":"好偏财，投资计划过于理想化，易忽视风险。日常的冲动花销增多。","month":"上半月，有参与大项目投资的机会，或者是为事业发展有大笔的资金投入计划，由于冥王近年也在事业宫运作，无论是多好的机会，都不建议倾家荡产甚至是外借高债来参与。下半月，人际生财的机会，多向人多的地方钻钻，会打听有利的消息和机会。投资选众筹，消费选团购，是不错的选择。"}
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
         * id : 50
         * year : 上半年收入还算稳定，但也不太会有意料之外的收入，需要花钱的地方倒是不少，一不小心就会有入不敷出的感觉。1月到3月初，因为火星在危机宫位穿行，你会因为为未来投资而花上不少，工作上的收入还算不错，勉强略略弥补亏空。3月到4月相对轻松一些，但也因此无心工作，反而花费不少在旅游和聚会上。整个5月将是上半年财运最旺的时节，这个月内相应的意外开始也会减少，让你们过上一段丰衣足食的生活。6月则不得不为一些之前的麻烦买单。进入下半年后，7月底到8月中将有一波不错的偏财运，部分个人可以通过投资获利，另一些则可能通过朋友接到一些私活，或是通过个人兴趣爱好赚上一笔。10月之后，需要花钱的地方明显减少，事业开始平稳发展也让收入开始有质的飞跃，如有可能尽量做些积蓄，好为未来潜在的发展可能储备力量。
         * today : 今天是你活动非常丰富的一天，特别是各种社交朋友找上门来让你应接不暇，伴侣之间也可能产生许多小情趣，做一顿美味的料理，准备一场新鲜有趣的约会，就能捕获他的心哦。
         * week : 好偏财，投资计划过于理想化，易忽视风险。日常的冲动花销增多。
         * month : 上半月，有参与大项目投资的机会，或者是为事业发展有大笔的资金投入计划，由于冥王近年也在事业宫运作，无论是多好的机会，都不建议倾家荡产甚至是外借高债来参与。下半月，人际生财的机会，多向人多的地方钻钻，会打听有利的消息和机会。投资选众筹，消费选团购，是不错的选择。
         */

        private int id;
        private String year;
        private String today;
        private String week;
        private String month;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
