package com.company.forturetelling.bean.bus;

/**
 * Created by Lovelin on 2020/2/27
 * <p>
 * Describe:
 */
public class RefreshPayTypeEvent {
    private String message;
    public RefreshPayTypeEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
