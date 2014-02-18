import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

/**
 * Created by Stephen on 16/02/14.
 */
public class XmlParser {

    public static XmlMessage parseGenericResult(String data){
        XmlMessage message = null;
        XmlPullParser parser;

        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(data));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){

                    case XmlPullParser.START_DOCUMENT:
                        message = new XmlMessage();
                        break;

                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();

                        if(message == null){
                            break;
                        }

                        if(tagName.equalsIgnoreCase(XmlMessage.RESPONSE)) {
                            message.status = parser.getAttributeValue(null, XmlMessage.STATUS);
                            if(parser.getAttributeValue(null, XmlMessage.CODE) != null) {
                                message.statusCode = Integer.parseInt(parser.getAttributeValue(null, XmlMessage.CODE));
                            }
                        }
                        else if(tagName.equalsIgnoreCase(XmlMessage.MESSAGE)) {
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

    public static XmlUserDetails parseUserDetails(String data){
        XmlUserDetails userDetails = null;
        XmlPullParser parser;

        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(data));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){

                    case XmlPullParser.START_DOCUMENT:
                        userDetails = new XmlUserDetails();
                        break;

                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();

                        if(userDetails == null){
                            break;
                        }

                        if(tagName.equalsIgnoreCase(XmlUserDetails.RESPONSE)) {
                            userDetails.status = parser.getAttributeValue(null, XmlUserDetails.STATUS);
                            // Only status=fail produce return code
                            if(parser.getAttributeValue(null, XmlUserDetails.CODE) != null) {
                                userDetails.statusCode = Integer.parseInt(parser.getAttributeValue(null, XmlUserDetails.CODE));
                            }
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.MESSAGE)) {
                            userDetails.message = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.USER_ID)){
                            userDetails.user_id = Integer.parseInt(parser.getAttributeValue(null, XmlUserDetails.USER_ID));
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.NAME)){
                            userDetails.name = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.USERNAME)){
                            userDetails.username = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.LIBRARY_NUMBER)){
                            userDetails.lib_no = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.DATE_OF_BIRTH)){
                            userDetails.dob = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.TYPE)){
                            userDetails.type = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.DESCRIPTION)){
                            userDetails.description = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(XmlUserDetails.IS_NEW)){
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

    public static void parseFriendRequests(String data){}

    public static void parseFriendHandshake(String data){}

    public static void parseFriendList(String data){}

    public static void parseEventDetails(String data){}

    public static void parseEventList(String data){}

    public static void parsePlaceList(String data){}

    public static void parseSentMessages(String data){}

    public static void parseReceivedMessages(String data){}

}
