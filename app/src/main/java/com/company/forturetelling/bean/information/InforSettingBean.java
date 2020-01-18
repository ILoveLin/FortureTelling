package com.company.forturetelling.bean.information;

/**
 * Created by Lovelin on 2019/12/12
 * <p>
 * Describe:
 */
public class InforSettingBean {

    /**
     * status : 0
     * msg : 资料更新成功
     * data : {}
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
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
