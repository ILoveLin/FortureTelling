package com.company.forturetelling.bean;

/**
 * Created by Lovelin on 2019/12/20
 * <p>
 * Describe:
 */
public class UpdateBean {

    /**
     * id : 3
     * type : 1
     * version : 1
     * is_force : 0
     * downurl : /app/forturetelling_v1.apk
     * size : 12.22 MB
     * time : 1576824136
     */

    private int id;
    private int type;
    private int version;
    private int is_force;
    private String downurl;
    private String size;
    private int time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getIs_force() {
        return is_force;
    }

    public void setIs_force(int is_force) {
        this.is_force = is_force;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
