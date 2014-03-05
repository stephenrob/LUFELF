package uk.co.stephen_robinson.uni.lufelf.route;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author James
 * Parses the JSON data returned from the server and forms it into a route.
 */
public class DirectionsParser {

    private JSONObject returnedData;
    private Activity activity;
    public DirectionsParser(Activity activity){
        this.activity=activity;
    }

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
            JSONArray legs=routes.getJSONArray("legs");

            for(int x=0;x<legs.length();x++){
                JSONObject currentLeg=(JSONObject)legs.get(x);
                JSONArray steps=currentLeg.getJSONArray("steps");
                for(int i=0;i<steps.length();i++){
                    JSONObject start=steps.getJSONObject(i);
                    String polyLine=(String)((JSONObject)start.get("polyline")).get("points");
                    r.combineList(decodePoly(polyLine));

                }
            }
        }catch(Exception e){
            Log.e("Route parser error",e.toString());
            Toast.makeText(activity,"No Route Available",Toast.LENGTH_LONG);
        }
        return r;
    }

    /**
     * decodes a polyline returned from google direction
     * @param encoded the string to decode
     * @return a list of latlng
     */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),(((double) lng / 1E5)));

            poly.add(p);
        }

        return poly;
    }

}

