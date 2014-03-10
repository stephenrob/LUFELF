package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 20/02/14.
 */

/**
 * @author stephen
 *
 * Stored variables and xml tag names for Message data returned from the api, all other xml types extend message
 */
public class Message {

    protected String message = "";
    protected int statusCode = 0;
    protected String status = "";

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

    /**
     * Blank constructor used in some parsing methods
     */
    public Message(){

    }

    /**
     * Constructor used to generate a quick status message
     *
     * @param code Status code returned from the server
     * @param status Status success/failure returned from the server
     * @param message Message returned from the server
     */
    public Message(Integer code, String status, String message){
        this.message = message;
        this.status = status;
        this.statusCode = code;
    }
}
