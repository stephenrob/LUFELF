package uk.co.stephen_robinson.uni.lufelf.route;


import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import android.util.Log;
import android.util.Xml;
import org.json.JSONArray;
import java.io.StringReader;

/**
 * Created by James on 24/01/2014.
 */
public class DirectionsParser {

    private JSONObject returnedData;


    public Route getRoute(String responseText){
        Route r = new Route();
        try{
            returnedData=new JSONObject(responseText);
            JSONObject routes=returnedData.getJSONArray("routes").getJSONObject(0);
            JSONObject legs=routes.getJSONArray("legs").getJSONObject(0);
            JSONArray steps=legs.getJSONArray("steps");

            for(int i=0;i<steps.length();i++){
                JSONObject start=steps.getJSONObject(i).getJSONObject("start_location");

                Log.e("Crap","GRRRRRRR "+start.getDouble("lat"));
                double lat=start.getDouble("lat");
                double longitude=start.getDouble("lng");
                r.addNode(new Point(lat,longitude));
            }
        }catch(Exception e){
            Log.e("Crap2","HERE bollocks "+e);
        }

        return r;
    }
}

