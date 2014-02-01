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
import android.location.*;
import com.google.android.gms.maps.SupportMapFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.Toast;

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

        Log.e("CRAP", "HELP HELP HELP");

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);


        if(!locationManager.isProviderEnabled(Context.LOCATION_SERVICE))
            Toast.makeText(context,"Enable GPS",5).show();

        //this.locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
       // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1000, this);

        map = ((MapFragment) fragmentManager.findFragmentById(R.id.map)).getMap();
        //map.setMyLocationEnabled(true);
        CameraUpdate center= CameraUpdateFactory.newLatLng(getLocation());
        //CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        map.moveCamera(center);

        //map.animateCamera(zoom);

        return rootView;
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
}
