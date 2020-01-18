package com.company.forturetelling.bean.bus;

/**
 * Created by Lovelin on 2019/12/12
 * <p>
 * Describe:
 */
public class ExitEvent {
    private String message;
    public  ExitEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
