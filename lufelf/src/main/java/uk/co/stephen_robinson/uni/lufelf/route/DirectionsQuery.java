package uk.co.stephen_robinson.uni.lufelf.route;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

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

import java.util.List;

/**
 * Created by James on 24/01/2014.
 */
public class DirectionsQuery {

    private HttpClient client;
    private DirectionsParser directions;

    public DirectionsQuery(){
        directions=new DirectionsParser();
        client= new DefaultHttpClient();
    }

    public void getRoute(LatLng start, LatLng finish, GoogleMap m){
        final LatLng startLoc=start;
        final LatLng finishLoc=finish;
        final GoogleMap map=m;
        Route finalRoute=null;
        AsyncTask test = new AsyncTask<Void, Void, Route>() {
            protected void onPreExecute() {
                // perhaps show a dialog
                // with a progress bar
                // to let your users know
                // something is happening
            }
            @Override
            protected Route doInBackground(Void...voided) {
                String response="";
                //HttpPost getData=new HttpPost("http://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&sensor=false");
                HttpPost getData = new HttpPost("http://maps.googleapis.com/maps/api/directions/json?origin="+String.valueOf(startLoc.latitude)+","+String.valueOf(startLoc.longitude)
                        +"&destination="+String.valueOf(finishLoc.latitude)+","+String.valueOf(finishLoc.longitude)+"&mode=walking&sensor=true");

                HttpResponse resp;
                try{

                    ResponseHandler<String> responseHandler=new BasicResponseHandler();
                    String responseText=client.execute(getData,responseHandler);

                    return directions.getRoute(responseText);
                }catch(Exception e){
                    Log.e("Crap",e.toString());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Route r) {
                DirectionsQuery query = new DirectionsQuery();
                List<Point> point=r.getRoute();
                if(point!=null){
                    PolylineOptions line = new PolylineOptions();
                    line.width(5);
                    line.color(Color.RED);

                    MarkerOptions mOptions1=new MarkerOptions();
                    mOptions1.title("A");
                    mOptions1.position(point.get(0).getLatLng());

                    MarkerOptions mOptions2=new MarkerOptions();
                    mOptions2.title("B");
                    mOptions2.position(point.get(point.size()-1).getLatLng());

                    map.addMarker(mOptions1);
                    map.addMarker(mOptions2);
                    for (Point p:point) {
                        line.add(p.getLatLng());

                }

                map.addPolyline(line);
                }
            }
        }.execute();
    }
}

