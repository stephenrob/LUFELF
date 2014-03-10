package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Stephen on 21/02/14.
 */

/**
 * @author stephen
 *
 * Parses all incoming xml from the server
 */
public class Parser {

    /**
     * static method for parsing a generic message returned from the server
     *
     * @param data xml string returned from the server
     * @return parsed data as Message
     */
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
            message = new Message(400, "fail", "Error");
        } catch (IOException e){
            message = new Message(400, "fail", "Error");
        }

        return message;
    }

    /**
     * static method for parsing user details returned from the server
     *
     * @param data xml string returned from the server
     * @return parsed data as user
     */
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

        }  catch (XmlPullParserException e){
            message = new Message(400, "fail", "Error");
            userDetails = new User();
            userDetails.message = message.message;
            userDetails.status = message.status;
            userDetails.statusCode = message.statusCode;
        } catch (IOException e){
            message = new Message(400, "fail", "Error");
            userDetails = new User();
            userDetails.message = message.message;
            userDetails.status = message.status;
            userDetails.statusCode = message.statusCode;
        }

        return userDetails;
    }

    /**
     * static method for parsing list of places
     * @param data xml string returned from the server
     * @return array list of all places found
     */
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
                                try{
                                    place.lattitude = Double.valueOf(parser.nextText());
                                }catch(Exception e){
                                    place.lattitude=0.0;
                                }
                            } else if(tagName.equalsIgnoreCase(Place.LONGDITUDE)){
                                try{
                                    place.longditude = Double.valueOf(parser.nextText());
                                }catch(Exception e){
                                    place.longditude=0.0;
                                }
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

            places.add(status);

        } catch (XmlPullParserException e){
            places = new ArrayList();

            places.add(new Message(400, "fail", "Error"));
        } catch (IOException e){
            places = new ArrayList();

            places.add(new Message(400, "fail", "Error"));
        }

        return places;

    }

    /**
     * static method for parsing events returned from the server
     *
     * @param data xml string returned from the server
     * @return array list of all events found
     */
    public static ArrayList parseEvents(String data){
        ArrayList events = new ArrayList();
        Message status = new Message();
        ArrayList<EventUser> users = null;
        Event event = null;
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

                        if(tagName.equalsIgnoreCase("event")){
                            event = new Event();
                        } else if(event != null){
                            if(tagName.equalsIgnoreCase(Event.ID)){
                                event.id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Event.NAME)){
                                event.name = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.DESCRIPTION)){
                                event.description = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.TYPE)){
                                event.type = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.DATE)) {
                                event.date = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.SINGLE_NAME)){
                                event.name = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.SINGLE_DESCRIPTION)){
                                event.description = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.SINGLE_TYPE)){
                                event.type = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.SINGLE_DATE)) {
                                event.date = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.USER_ID)){
                                event.user_id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Place.PLACE_ID)){
                                event.place_id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Place.IMAGE_URL)){
                                event.image_url = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.LOCATION_NAME)){
                                event.location_name = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.LOCATION_ADDRESS)){
                                event.location_address = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Event.LOCATION_GPS)){
                                event.location = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(User.USERNAME)){
                                event.owner.name = parser.nextText();
                            } else if(tagName.equalsIgnoreCase("email")){
                                event.owner.username = parser.nextText();
                            }
                        }

                        if(tagName.equalsIgnoreCase("users")){
                            users = new ArrayList<EventUser>();
                        }

                        if(tagName.equalsIgnoreCase("user")){
                            users.add(parseEventUser(parser));
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if(tagName.equalsIgnoreCase("event") && event != null){
                            event.attendees = users;
                            events.add(event);
                        }

                        break;

                }

                eventType = parser.next();

            }
            events.add(status);

        } catch (XmlPullParserException e){
            events = new ArrayList();

            events.add(new Message(400, "fail", "Error"));
        } catch (IOException e){
            events = new ArrayList();

            events.add(new Message(400, "fail", "Error"));
        }

        return events;

    }

    /**
     * static private method for parsing attendees of an event, called and returns to parseEvents
     *
     * @param parser parser object to parse event user
     * @return EventUser who is attending even
     * @throws XmlPullParserException
     * @throws IOException
     */
    private static EventUser parseEventUser(XmlPullParser parser) throws XmlPullParserException, IOException {

        EventUser eventUser = new EventUser();

        parser.require(XmlPullParser.START_TAG, null, "user");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if(tagName.equalsIgnoreCase(EventUser.NAME)){
                eventUser.name = parser.nextText();
            } else if(tagName.equalsIgnoreCase(EventUser.DATE_ACCEPTED)){
                eventUser.date_accepted = parser.nextText();
            }
        }
        return eventUser;

    }

    /**
     * static method for parsing returned location update
     *
     * @param data xml string returned from the server
     * @return Status of update location
     */
    public static Status parseUpdateLocation(String data){
        Status status = null;
        XmlPullParser parser;

        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(data));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){

                    case XmlPullParser.START_DOCUMENT:
                        status = new Status();
                        break;

                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();

                        if(status == null){
                            break;
                        }

                        if(tagName.equalsIgnoreCase(Status.RESPONSE)) {
                            status.status = parser.getAttributeValue(null, Status.STATUS);
                            if(parser.getAttributeValue(null, Status.CODE) != null) {
                                status.statusCode = Integer.parseInt(parser.getAttributeValue(null, Status.CODE));
                            } else {
                                status.statusCode = 200;
                            }
                        }
                        else if(tagName.equalsIgnoreCase(Message.MESSAGE)) {
                            status.message = parser.nextText();
                        }
                        else if(tagName.equalsIgnoreCase(Status.IS_NEW)) {
                            status.is_new = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e){
            Message message = new Message(400, "fail", "Error");
            status = new Status();
            status.status = message.status;
            status.statusCode = message.statusCode;
            status.message = message.message;
            status.is_new = false;
        } catch (IOException e){
            Message message = new Message(400, "fail", "Error");
            status = new Status();
            status.status = message.status;
            status.statusCode = message.statusCode;
            status.message = message.message;
            status.is_new = false;
        }

        return status;
    }

    /**
     * static method for parsing friend requests list from server
     *
     * @param data xml string returned from the server
     * @return array list of all friend requests found
     */
    public static ArrayList parseFriendRequests(String data){

        ArrayList requests = new ArrayList();
        Message status = new Message();
        Friend currentFriend = null;
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

                        if(tagName.equalsIgnoreCase(Friend.USER)){
                            currentFriend = new Friend();
                        } else if(currentFriend != null){
                            if(tagName.equalsIgnoreCase(Friend.USER_ID)){
                                currentFriend.user_id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Friend.REQUEST_ID)){
                                currentFriend.request_id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Friend.NAME)){
                                currentFriend.name = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(User.LIBRARY_NUMBER)){
                                currentFriend.lib_no = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Friend.USERNAME)){
                                currentFriend.username = parser.nextText();
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();

                        if(tagName.equalsIgnoreCase(Friend.FRIEND) && currentFriend != null){
                            requests.add(currentFriend);
                        }

                        break;

                }

                eventType = parser.next();

            }

            requests.add(status);

        } catch (XmlPullParserException e){
            requests = new ArrayList();

            requests.add(new Message(400, "fail", "Error"));
        } catch (IOException e){
            requests = new ArrayList();

            requests.add(new Message(400, "fail", "Error"));
        }

        return requests;

    }

    /**
     * static method for parsing result of updating a friend request
     *
     * @param data xml string returned from the server
     * @return Message indicating success/failure of updating friend request
     */
    public static Message parseFriendHandshake(String data){
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
                        else if(tagName.equalsIgnoreCase("friendship")) {
                            message.message = parser.nextText();
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e){
            message = new Message(400, "fail", "Error");
        } catch (IOException e){
            message = new Message(400, "fail", "Error");
        }

        return message;
    }

    /**
     * static method for parsing list of friends from the server
     *
     * @param data xml string returned from the server
     * @return array list of friends found
     */
    public static ArrayList parseFriendsList(String data){
        ArrayList friends = new ArrayList();
        Message status = new Message();
        Friend currentFriend = null;
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

                        if(tagName.equalsIgnoreCase(Friend.USER)){
                            currentFriend = new Friend();
                        } else if(currentFriend != null){
                            if(tagName.equalsIgnoreCase(Friend.USER_ID)){
                                currentFriend.user_id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Friend.FRIEND_ID)){
                                currentFriend.friend_id = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Friend.FRIEND_NAME)){
                                currentFriend.name = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Friend.USERNAME)){
                                currentFriend.username = parser.nextText();
                            } else if(tagName.equalsIgnoreCase(Friend.LOCATION_STATUS)){
                                currentFriend.location_status = Integer.valueOf(parser.nextText());
                            } else if(tagName.equalsIgnoreCase(Friend.LATTITUDE)){
                                String temp = parser.nextText();
                                if(temp.equals("0/.")){
                                    currentFriend.lattitude = 0.0;
                                }else if(!temp.equals("")){
                                    currentFriend.lattitude = Double.valueOf(temp);
                                }else {
                                    currentFriend.lattitude = 0.0;
                                }
                            } else if(tagName.equalsIgnoreCase(Friend.LONGITUDE)){
                                String temp = parser.nextText();
                                if(temp.equals("0/.")){
                                    currentFriend.longitude = 0.0;
                                }else if(!temp.equals("")){
                                    currentFriend.longitude = Double.valueOf(temp);
                                }else {
                                    currentFriend.longitude = 0.0;
                                }
                            }

                        }

                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();

                        if(tagName.equalsIgnoreCase(Friend.USER) && currentFriend != null){
                            friends.add(currentFriend);
                        }

                        break;

                }

                eventType = parser.next();

            }

            friends.add(status);

        } catch (XmlPullParserException e){
            friends = new ArrayList();

            friends.add(new Message(400, "fail", "Error"));
        } catch (IOException e){
            friends = new ArrayList();

            friends.add(new Message(400, "fail", "Error"));
        }

        return friends;

    }

    /**
     * static method for parsing list of user messages sent/received
     *
     * @param data xml string returned from the server
     * @return array list of message found
     */
    public static ArrayList parseMessages(String data){
        ArrayList messages = new ArrayList();
        Message status = new Message();
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
                        } else if(tagName.equalsIgnoreCase(Message.MESSAGE) && status.statusCode != 200){
                            status.message = parser.nextText();
                        }
                        if(status.message.equals("")){
                            if(tagName.equalsIgnoreCase(UserMessage.MESSAGE)){
                                messages.add(parseUserMessage(parser));
                            }
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        break;

                }

                eventType = parser.next();

            }

            messages.add(status);

        } catch (XmlPullParserException e){
            messages = new ArrayList();

            messages.add(new Message(400, "fail", "Error"));
        } catch (IOException e){
            messages = new ArrayList();

            messages.add(new Message(400, "fail", "Error"));
        }

        return messages;
    }

    /**
     * private static method for parsing individual message found for parseMessages
     *
     * @param parser parser object to parse user message for
     * @return Usermessage, to directly add to the list of messages in parseMessages
     * @throws XmlPullParserException
     * @throws IOException
     */
    private static UserMessage parseUserMessage(XmlPullParser parser) throws XmlPullParserException, IOException {

        UserMessage userMessage = new UserMessage();

        parser.require(XmlPullParser.START_TAG, null, UserMessage.MESSAGE);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if(tagName.equalsIgnoreCase(UserMessage.MESSAGE_ID)){
                userMessage.message_id = Integer.valueOf(parser.nextText());
            } else if(tagName.equalsIgnoreCase(UserMessage.MESSAGE_FROM)){
                userMessage.from = parser.nextText();
            } else if(tagName.equalsIgnoreCase(UserMessage.MESSAGE_TO)){
                userMessage.to = parser.nextText();
            } else if(tagName.equalsIgnoreCase(UserMessage.CONTENT)){
                userMessage.content = parser.nextText();
            }
        }
        return userMessage;

    }

    /**
     * static method for parsing a list of multiple users
     *
     * @param data xml string returned from the server
     * @return array list of users found from server query
     */
    public static ArrayList parseMultipleUsers(String data){
        ArrayList users = new ArrayList();
        Message status = new Message();
        XmlPullParser parser;
        String tagName;
        User userDetails = null;

        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(data));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){

                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();

                        if(tagName.equalsIgnoreCase(User.RESPONSE)) {
                            status.status = parser.getAttributeValue(null, User.STATUS);
                            // Only status=fail produce return code
                            if(parser.getAttributeValue(null, User.CODE) != null) {
                                status.statusCode = Integer.parseInt(parser.getAttributeValue(null, User.CODE));
                            } else {
                                status.statusCode = 200;
                            }
                        }
                        else if(tagName.equalsIgnoreCase(User.MESSAGE)) {
                            status.message = parser.nextText();
                        }
                        if(tagName.equalsIgnoreCase("user")){
                            userDetails = new User();
                        } else if(userDetails != null){
                            if(tagName.equalsIgnoreCase(User.USER_ID)){
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
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();

                        if(tagName.equalsIgnoreCase("user") && userDetails != null){
                            users.add(userDetails);
                        }

                        break;
                }
                eventType = parser.next();
            }


            users.add(status);

        } catch (XmlPullParserException e){
            users = new ArrayList();

            users.add(new Message(400, "fail", "Error"));
        } catch (IOException e){
            users = new ArrayList();

            users.add(new Message(400, "fail", "Error"));
        }

        return users;
    }

}
