package com.company.forturetelling.bean.information;

/**
 * Created by Lovelin on 2019/12/12
 * <p>
 * Describe:
 */
public class UpdateImg {
    /**
     * status : 0
     * msg : ok
     * data : {"img":"/uploads/20191211/5df097229b2a7.jpeg"}
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
         * img : /uploads/20191211/5df097229b2a7.jpeg
         */

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
