import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

/**
 * Created by Stephen on 16/02/14.
 */
public class GenericXmlParser {

    public static XmlMessage parseResult(String data){
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

}
