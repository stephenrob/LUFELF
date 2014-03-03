package uk.co.stephen_robinson.uni.lufelf.route;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;


/**
 * @author James
 * This class queries the google directions server and draws the route onto the map
 */

public class DirectionsQuery {

    private HttpClient client;
    private DirectionsParser directions;
    private Context context;
    private Activity activity;
    /**
     * instantiates the directions and client variables.
     */
    public DirectionsQuery(Activity activity){
        directions=new DirectionsParser(activity);
        client= new DefaultHttpClient();
        this.context=activity;
        this.activity=activity;
    }

    /**
     * queries google directions and draws the returned route on the map
     * @param start start point
     * @param finish end point
     * @param m the map to draw the route on
     */
    public void getRoute(LatLng start, LatLng finish, GoogleMap m){
        final LatLng startLoc=start;
        final LatLng finishLoc=finish;
        final GoogleMap map=m;
        AsyncTask test = new AsyncTask<Void, Void, Route>() {
            protected void onPreExecute() {
                // perhaps show a dialog
                // with a progress bar
                // to let your users know
                // something is happening
            }
            @Override
            protected Route doInBackground(Void...voided) {
                if(startLoc==null||finishLoc==null){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"Location information not available - unable to route",Toast.LENGTH_SHORT);
                        }
                    });

                    return null;
                }
                String response="";
                //HttpPost getData=new HttpPost("http://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&sensor=false");
                HttpPost getData = new HttpPost("http://maps.googleapis.com/maps/api/directions/json?origin="+String.valueOf(startLoc.latitude)+","+String.valueOf(startLoc.longitude)
                        +"&destination="+String.valueOf(finishLoc.latitude)+","+String.valueOf(finishLoc.longitude)+"&mode=walking&sensor=false");

                HttpResponse resp;
                try{

                    ResponseHandler<String> responseHandler=new BasicResponseHandler();
                    String responseText=client.execute(getData,responseHandler);

                    return directions.getRoute(responseText);
                }catch(Exception e){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Internet Connection Unavailable", Toast.LENGTH_SHORT);
                        }
                    });
                }
                return null;
            }
            @Override
            protected void onPostExecute(Route r) {
                if(r!=null){
                    ArrayList<LatLng> point=r.getRoute();
                    if(point.size()>0){
                        PolylineOptions line = new PolylineOptions();
                        line.width(5);
                        line.color(Color.RED);

                        MarkerOptions mOptions1=new MarkerOptions();
                        mOptions1.title("A");
                        mOptions1.position(point.get(0));

                        MarkerOptions mOptions2=new MarkerOptions();
                        mOptions2.title("B");
                        mOptions2.position(point.get(point.size() - 1));

                        map.addMarker(mOptions1);
                        map.addMarker(mOptions2);
                        line.addAll(point);
                        map.addPolyline(line);
                    }else{
                        Log.e("HERE","ROUTE2");
                        Toast.makeText(context,"No Route Found",Toast.LENGTH_LONG);
                    }
                }else{
                    Log.e("HERE","ROUTE");

                }
            }
        }.execute();
    }
}

