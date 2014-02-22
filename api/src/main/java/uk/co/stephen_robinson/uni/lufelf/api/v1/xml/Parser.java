package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

import android.content.UriMatcher;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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
        Message message = new Message();
        XmlPullParser parser;
        String tagName;

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
                        tagName = parser.getName();

                        if(userDetails == null){
                            break;
                        }

                        if(tagName.equalsIgnoreCase(User.RESPONSE)) {
                            message.status = parser.getAttributeValue(null, User.STATUS);
                            // Only status=fail produce return code
                            if(parser.getAttributeValue(null, User.CODE) != null) {
                                message.statusCode = Integer.parseInt(parser.getAttributeValue(null, User.CODE));
                            } else {
                                message.statusCode = 200;
                            }
                        }
                        else if(tagName.equalsIgnoreCase(User.MESSAGE)) {
                            message.message = parser.nextText();
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

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();

                        if(tagName.equalsIgnoreCase("user") && userDetails != null)

                        break;
                }
                eventType = parser.next();
            }

            userDetails.message = message.message;
            userDetails.status = message.status;
            userDetails.statusCode = message.statusCode;

        } catch (XmlPullParserException e){
            userDetails = null;
        } catch (IOException e){
            userDetails = null;
        }



        return userDetails;
    }

    public static ArrayList parsePlaces(String data){
        ArrayList places = new ArrayList();
        Message status = new Message();
        Place place = null;
        XmlPullParser parser;
        String tagName;

        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(data));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){

                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();

                        if(tagName.equalsIgnoreCase(Message.RESPONSE)){
                            status.status = parser.getAttributeValue(null, Message.STATUS);

                            if(parser.getAttributeValue(null, Message.CODE) != null){
                                status.statusCode = Integer.parseInt(parser.getAttributeValue(null, Message.CODE));
                            } else {
                                status.statusCode = 200;
                            }
                        } else if(tagName.equalsIgnoreCase(Message.MESSAGE)){
                            status.message = parser.nextText();
                        }

                        if(tagName.equalsIgnoreCase("place")){
                            place = new Place();
                        } else if(place != null){
                            if(tagName.equalsIgnoreCase(Place.PLACE_ID)){
                                place.id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Place.NAME)){
                                place.name = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Place.LATTITUDE)){
                                place.lattitude = Double.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Place.LONGDITUDE)){
                                place.longditude = Double.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Place.DESCRIPTION)){
                                place.description = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Place.TYPE)){
                                place.type = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Place.USER_ID)){
                                place.user_id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Place.ADDRESS)){
                                place.address = parser.nextText();
                            }
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();

                        if(tagName.equalsIgnoreCase("place") && place != null){
                            places.add(place);
                        }

                        break;

                }

                eventType = parser.next();

            }

        } catch (XmlPullParserException e){
            places = null;
        } catch (IOException e){
            places = null;
        }

        places.add(status);

        return places;

    }

}
