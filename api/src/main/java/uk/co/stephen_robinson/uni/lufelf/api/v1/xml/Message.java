package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 20/02/14.
 */
public class Message {

    protected String message = null;
    protected int statusCode = 0;
    protected String status = null;

    public static final String RESPONSE = "rsp";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String CODE = "code";

    public static final String SUCCESS = "ok";
    public static final String FAILURE = "fail";

}
