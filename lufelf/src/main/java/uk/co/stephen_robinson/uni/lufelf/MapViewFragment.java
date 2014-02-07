package uk.co.stephen_robinson.uni.lufelf;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.*;
import android.location.*;
import com.google.android.gms.maps.SupportMapFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import uk.co.stephen_robinson.uni.lufelf.route.*;
import uk.co.stephen_robinson.uni.lufelf.route.DirectionsQuery;
import java.util.List;
import android.graphics.Color;
/**
 * Created by James on 31/01/2014.
 */
public class MapViewFragment extends BaseFragment implements LocationListener{
    private GoogleMap map;
    private LocationManager locationManager;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapViewFragment newInstance() {
        return new MapViewFragment();
    }

    public MapViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        setFragmentManager(getFragmentManager());

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setContext(rootView.getContext());

        this.locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 5, this);

        map = ((MapFragment) fragmentManager.findFragmentById(R.id.map)).getMap();

        //check if location services are available
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            //if they're not available display toast message
            Toast.makeText(context,"Please enable your GPS",5).show();
            //change
            //center on the university campus
            CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(54.0103,2.7856));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);

            map.moveCamera(center);
            map.animateCamera(zoom);
        }else{

            //set the call back request for every 400 milliseconds or every 5 metres
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 5, this);

            //set the locationenabled boolean
            map.setMyLocationEnabled(true);

            //centre on current location
            CameraUpdate center= CameraUpdateFactory.newLatLng(getLocation());
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);

            map.moveCamera(center);

            map.animateCamera(zoom);

        }

        DirectionsQuery query = new DirectionsQuery();
        query.getRoute(getLocation(), new LatLng(54.0078566, -2.7856414),map);
        //query.loginUser(new LatLng(54.0097969,-2.7862154),new LatLng(54.0078566,-2.7856414));
        //navigateTo(new LatLng(54.0103,2.7856));
        return rootView;
    }
    public void navigateTo(LatLng finish){
        LatLng current=getLocation();
        Intent navigation = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+current.latitude+","+current.longitude+"&daddr="+finish.latitude+","+finish.longitude));
        startActivity(navigation);
    }

    public LatLng getLocation(){
        double latitude =0;
        double longitude =0;

        Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        longitude=loc.getLongitude();
        latitude=loc.getLatitude();

        return new LatLng(latitude,longitude);
    }
    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        map.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MapFragment f = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (f != null)
            fragmentManager.beginTransaction().remove(f).commit();
    }
}
