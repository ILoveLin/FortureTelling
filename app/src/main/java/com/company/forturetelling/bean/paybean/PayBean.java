package com.company.forturetelling.bean.paybean;

/**
 * Created by Lovelin on 2020/1/14
 * <p>
 * Describe:
 */
public class PayBean {

private  String appid;    //应用id： 是你申请应用后就有的
private  String partnerid;    //商户号
private  String prepayid;    //预付单号：是走统一下单的接口后，微信返回的一个预支付单号
private  String packagee;    //扩展字段：这个字段暂时没啥用，微信是让我们填写固定值Sign=WXPay就行
private  String noncestr;    //随机字符串：这个是后台自己随便生成的一个随机字符串，但是不要超过32位，微信官方提供给了我们一个随机生成算法
private  String timestamp;    //时间戳： 没啥好说
private  String sign;    //后台参考微信提供给我们的

    @Override
    public String toString() {
        return "PayBean{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", packagee='" + packagee + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackagee() {
        return packagee;
    }

    public void setPackagee(String packagee) {
        this.packagee = packagee;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
