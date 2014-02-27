package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 20/02/14.
 */
public class Message {

    public String message = "";
    public int statusCode = 0;
    public String status = "";

    public static final String RESPONSE = "rsp";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String CODE = "code";

    public static final String SUCCESS = "ok";
    public static final String FAILURE = "fail";

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public Message(Integer code, String status, String message){
        this.message = message;
        this.status = status;
        this.statusCode = code;
    }
}
