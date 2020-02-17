package com.company.forturetelling.bean;

import java.util.List;

/**
 * Created by Lovelin on 2020/2/17
 * <p>
 * Describe:
 */
public class WechatLoginBean {


    /**
     * openid : oh1gJj77vnpxAfNdii2zWJ1PaVZ0
     * nickname : 乐观
     * sex : 1
     * language : zh_CN
     * city :
     * province : Paris
     * country : FR
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIpDnvrfGsKKxXBiccAVZMZK0qH79ybg48GU7IlMeMU8pNBSJwhv5vgibQK3JyrJCjo1W4BJhgjab1Q/132
     * privilege : []
     * unionid : oLIdYs4bO-3Dfe-PmOjDEprvgUiE
     */

    private String openid;
    private String nickname;
    private String sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
