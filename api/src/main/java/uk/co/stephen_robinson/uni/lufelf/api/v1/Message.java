package uk.co.stephen_robinson.uni.lufelf.api.v1;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.SessionManager;
import uk.co.stephen_robinson.uni.lufelf.api.network.MultiplePost;
import uk.co.stephen_robinson.uni.lufelf.api.network.SinglePost;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.UserMessage;

/**
 * Created by Stephen on 26/02/2014.
 */
public class Message {

    protected int message_id;
    protected String from;
    protected String content;

    public static final String MESSAGE_ID = "message_id";
    public static final String MESSAGE_FROM = "message_from";
    public static final String CONTENT = "message";

    public int getMessage_id() {
        return message_id;
    }

    public String getFrom() {
        return from;
    }

    public String getContent() {
        return content;
    }

    static void sent(Multiple mc){
        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        MultiplePost networkTask =  new MultiplePost(mc, Scripts.SENT_MESSAGES);
        networkTask.execute(params);
    }

    static void received(Multiple mc){
        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        MultiplePost networkTask =  new MultiplePost(mc, Scripts.RECEIVED_MESSAGE);
        networkTask.execute(params);
    }

    static void send(Integer to, String message, Single sc){
        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(4);

        params.add(new BasicNameValuePair("user_id_from", Integer.toString(userId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));
        params.add(new BasicNameValuePair("user_id_to", Integer.toString(to)));
        params.add(new BasicNameValuePair(Message.CONTENT, message));

        SinglePost networkTask =  new SinglePost(sc, Scripts.SEND_MESSAGE);
        networkTask.execute(params);
    }
}
