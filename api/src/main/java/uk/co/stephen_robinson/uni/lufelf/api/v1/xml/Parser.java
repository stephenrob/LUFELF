package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Stephen on 21/02/14.
 */
public class Parser {

    public static Message parseGenericResult(String data){
        Message message = null;
        XmlPullParser parser;

        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(data));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){

                    case XmlPullParser.START_DOCUMENT:
                        message = new Message();
                        break;

                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();

                        if(message == null){
                            break;
                        }

                        if(tagName.equalsIgnoreCase(Message.RESPONSE)) {
                            message.status = parser.getAttributeValue(null, Message.STATUS);
                            if(parser.getAttributeValue(null, Message.CODE) != null) {
                                message.statusCode = Integer.parseInt(parser.getAttributeValue(null, Message.CODE));
                            } else {
                                message.statusCode = 200;
                            }
                        }
                        else if(tagName.equalsIgnoreCase(Message.MESSAGE)) {
                            message.message = parser.nextText();
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e){
            message = null;
        } catch (IOException e){
            message = null;
        }

        return message;
    }

    public static User parseUserDetails(String data){
        User userDetails = null;
        XmlPullParser parser;

        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(data));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){

                    case XmlPullParser.START_DOCUMENT:
                        userDetails = new User();
                        break;

                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();

                        if(userDetails == null){
                            break;
                        }

                        if(tagName.equalsIgnoreCase(User.RESPONSE)) {
                            userDetails.status = parser.getAttributeValue(null, User.STATUS);
                            // Only status=fail produce return code
                            if(parser.getAttributeValue(null, User.CODE) != null) {
                                userDetails.statusCode = Integer.parseInt(parser.getAttributeValue(null, User.CODE));
                            } else {
                                userDetails.statusCode = 200;
                            }
                        }
                        else if(tagName.equalsIgnoreCase(User.MESSAGE)) {
                            userDetails.message = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(User.USER_ID)){
                            userDetails.user_id = Integer.parseInt(parser.nextText());
                        }
                        else if(tagName.equalsIgnoreCase(User.NAME)){
                            userDetails.name = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(User.USERNAME)){
                            userDetails.username = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(User.LIBRARY_NUMBER)){
                            userDetails.lib_no = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(User.DATE_OF_BIRTH)){
                            userDetails.dob = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(User.TYPE)){
                            userDetails.type = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(User.DESCRIPTION)){
                            userDetails.description = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(User.IS_NEW)){
                            userDetails.is_new = parser.nextText();
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e){
            userDetails = null;
        } catch (IOException e){
            userDetails = null;
        }

        return userDetails;
    }

}
