package uk.co.stephen_robinson.uni.lufelf.route;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author James
 * Parses the JSON data returned from the server and forms it into a route.
 */
public class DirectionsParser {

    private JSONObject returnedData;

    /**
     * returns the route of the returned directions
     * @param responseText the response from the directions server
     * @return Route object containing the latitudes and longitudes of the route.
     */
    public Route getRoute(String responseText){
        Route r = new Route();
        try{
            returnedData=new JSONObject(responseText);
            JSONObject routes=returnedData.getJSONArray("routes").getJSONObject(0);
            JSONObject legs=routes.getJSONArray("legs").getJSONObject(0);
            JSONArray steps=legs.getJSONArray("steps");

            for(int i=0;i<steps.length();i++){
                JSONObject start=steps.getJSONObject(i).getJSONObject("start_location");

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

