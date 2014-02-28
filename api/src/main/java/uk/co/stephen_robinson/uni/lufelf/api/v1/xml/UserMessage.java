package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 26/02/2014.
 */
public class UserMessage extends Message {

    protected int message_id;
    protected String from;
    protected String to;
    protected String content;

    public static final String MESSAGE_ID = "message_id";
    public static final String MESSAGE_FROM = "message_from";
    public static final String MESSAGE_TO = "message_to";
    public static final String CONTENT = "message";
    public static final String RECEIVED_MESSAGES = "received_messages";
    public static final String SENT_MESSAGES = "sent_messages";

    public int getMessage_id() {
        return message_id;
    }

    public String getFrom() {
        return from;
    }

    public String getContent() {
        return content;
    }

    public String getTo() {
        return to;
    }
}
