package uk.co.stephen_robinson.uni.lufelf.adapters;

/**
 * Created by James on 20/02/2014.
 */
public class MessageItem {
    private String messageFrom;
    private String messageContent;
    private String id;
    private String messageTo;



    /**
     * set the message from and content of a message item
     * @param messageFrom who the message is from
     * @param messageContent the message
     */
    public MessageItem(String messageFrom,String messageContent, String id,String messageTo){
        this.messageTo=messageTo;
        this.messageFrom=messageFrom;
        this.messageContent=messageContent;
        this.id = id;
    }

    public String getMessageTo() {
        return messageTo;
    }


    /**
     * returns the content of the message
     * @return message content
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * returns the sender of the message
     * @return the sender of the message
     */
    public String getMessageFrom() {
        return messageFrom;
    }

    /**
     * returns the id of the message sender
     * @return id of the person who sent the message
     */
    public String getId() {
        return id;
    }

    public static MessageItem getBlankResult(){
        return new MessageItem("","None","0",null);
    }
}
