package com.company.forturetelling.bean.bus;

/**
 * Created by Lovelin on 2020/2/17
 * <p>
 * Describe:
 */
public class WeChartEvent {
    private String message;
    public  WeChartEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
