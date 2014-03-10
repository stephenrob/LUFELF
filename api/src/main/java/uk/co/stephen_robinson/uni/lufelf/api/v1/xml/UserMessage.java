package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 26/02/2014.
 */

/**
 * @author stephen
 *
 * Stored variables and xml tag names for User Message data returned from the api
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

    /**
     * getter for message id
     * @return message id
     */
    public int getMessage_id() {
        return message_id;
    }

    /**
     * getter for message from
     * @return who message was sent from (name)
     */
    public String getFrom() {
        return from;
    }

    /**
     * getter for content of the message
     * @return content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * getter for who message is to
     * @return who message was sent to (name)
     */
    public String getTo() {
        return to;
    }
}
