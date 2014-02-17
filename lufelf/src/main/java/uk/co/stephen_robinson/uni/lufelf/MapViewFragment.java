package uk.co.stephen_robinson.uni.lufelf;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import uk.co.stephen_robinson.uni.lufelf.route.DirectionsQuery;
/**
 * Created by James on 31/01/2014.
 */
public class MapViewFragment extends BaseFragment implements LocationListener,GoogleMap.InfoWindowAdapter,GoogleMap.OnInfoWindowClickListener{
    private GoogleMap map;
    private LocationManager locationManager;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapViewFragment newInstance(LatLng finish) {
        if(finish==null)
            return new MapViewFragment();
        MapViewFragment m=new MapViewFragment();
        Bundle args=new Bundle();
        args.putDouble("lat",finish.latitude);
        args.putDouble("long",finish.longitude);

        m.setArguments(args);
        return m;
    }

    public MapViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setContext(rootView.getContext());
        showActivitySpinner();

        //get the location manager
        this.locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 5, this);

        //get the mapfragment
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

        populateWithPlaces();
        DirectionsQuery query = new DirectionsQuery();
        /*if(isNetworkAvailable())
            query.getRoute(getLocation(), new LatLng(54.0078566, -2.7856414),map);
        else
            Toast.makeText(context,"No Active Internet Connection Found",Toast.LENGTH_LONG).show();*/
        //query.loginUser(new LatLng(54.0097969,-2.7862154),new LatLng(54.0078566,-2.7856414));
        //navigateTo(new LatLng(54.0103,2.7856));
        //hideBusy();
        return rootView;
    }

    //alternative navigateto method using intents
    public void navigateTo(LatLng finish){
        LatLng current=getLocation();
        Intent navigation = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+current.latitude+","+current.longitude+"&daddr="+finish.latitude+","+finish.longitude));
        startActivity(navigation);
    }

    public void populateWithPlaces(){
        MarkerOptions mOptions1=new MarkerOptions();
        //Cursor places=provider.query(LufelfContentProvider.PLACES_CONTENT_URI,);
        mOptions1.title("A");
        mOptions1.snippet("ABCDEFGHIJKLMNOP");
        mOptions1.position(getLocation());

        map.addMarker(mOptions1);
        map.setInfoWindowAdapter(this);
        map.setOnInfoWindowClickListener(this);
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
    @Override
    public View getInfoWindow(Marker m){

        return null;
    }
    public View getInfoContents(Marker m){
        // Getting view from the layout file info_window_layout
        View v = View.inflate(context,R.layout.info_window_place_layout, null);

        // Getting the position from the marker
        LatLng latLng = m.getPosition();

        // Getting reference to the TextView to set latitude
        TextView title = (TextView) v.findViewById(R.id.place_title);

        // Getting reference to the TextView to set longitude
        TextView description = (TextView) v.findViewById(R.id.place_description);

        // Setting the latitude
        title.setText(m.getTitle());

        // Setting the longitude
        description.setText(m.getSnippet());

        // Returning the view containing InfoWindow contents
        return v;
    }
    public void onInfoWindowClick(Marker m){
        resetIndexes();
        fragmentManager.beginTransaction().replace(R.id.container, PlaceSubFragment.newInstance(), "PlaceSubView").addToBackStack(null).commit();
       // m.getPosition().latitude;
    }
}
