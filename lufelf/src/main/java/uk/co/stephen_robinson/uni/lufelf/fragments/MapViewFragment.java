package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.PlaceItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Place;
import uk.co.stephen_robinson.uni.lufelf.route.DirectionsQuery;
import uk.co.stephen_robinson.uni.lufelf.utilities.CustomMessages;
import uk.co.stephen_robinson.uni.lufelf.utilities.DialogCallback;

/**
 * @author James
 * Fragment that displays the map with location or the navigation view
 */
public class MapViewFragment extends BaseFragment implements LocationListener,GoogleMap.InfoWindowAdapter,GoogleMap.OnInfoWindowClickListener{
    private GoogleMap map;
    private LocationManager locationManager;
    private ArrayList placeItems;
    private Criteria crit = new Criteria();

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
    public void onResume(){
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000, 5, this);

    }
    @Override
    public void onPause(){
        super.onResume();
        locationManager.removeUpdates(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();
        //get the mapfragment
        map = ((MapFragment) fragmentManager.findFragmentById(R.id.map)).getMap();
        //get the location manager
        this.locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);


        //check if location services are available
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

            DialogCallback dc = new DialogCallback() {
                @Override
                public void messageComplete(int result) {
                    if(result==1){
                        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        context.startActivity(myIntent);
                    }
                }
            };

            //if they're not available display toast message
            CustomMessages.showMessage("GPS Not Enabled","Would you like to enable your GPS?","Yes","No",context,dc);

            //change
            //center on the university campus
            CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(54.0084879,-2.7850552));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);

            map.moveCamera(center);
            map.animateCamera(zoom);
        }else{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000, 5, this);
            //set the locationenabled boolean
            map.setMyLocationEnabled(true);

            //centre on current location
            CameraUpdate center= CameraUpdateFactory.newLatLng(getLocation());
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);

            map.moveCamera(center);

            map.animateCamera(zoom);

        }


        DirectionsQuery query = new DirectionsQuery(getActivity());

        Bundle args = getArguments();
        if(args!=null){
            if(isNetworkAvailable())
                query.getRoute(getLocation(), new LatLng(args.getDouble("lat"),args.getDouble("long") ),map);
            else
                Toast.makeText(context,"No Active Internet Connection Found",Toast.LENGTH_LONG).show();
        }else{
            populateWithPlaces();
        }

        return rootView;
    }

    /**
     * Alternative navigate to method using intents
     * @param finish where you want to go to
     */
    public void navigateTo(LatLng finish){
        LatLng current = getLocation();
        Intent navigation = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+current.latitude+","+current.longitude+"&daddr="+finish.latitude+","+finish.longitude));
        startActivity(navigation);
    }

    /**
     * Adds a marker to the map
     */
    public void populateWithPlaces(){

        showActivitySpinner();

        placeItems=new ArrayList<PlaceItem>();

        Multiple multipleCallback = new Multiple() {
            @Override
            public void results(ArrayList result) {
                Message m = (Message)result.get(result.size()-1);
                if(!toastMaker.isError(String.valueOf(m.statusCode),m.message)){
                    for(int i=0;i<result.size()-1;i++){
                        Place p =(Place)result.get(i);
                        placeItems.add(new PlaceItem(p.getId(),p.getName(),p.getAddress(),p.getType(),p.getDescription(),p.getUser_id(),p.getImage_url(),p.getLattitude(),p.getLongditude()));
                    }

                    MarkerOptions mOptions1=new MarkerOptions();
                    for(int count=0;count<placeItems.size();count++){

                        //get the current place item
                        PlaceItem p=(PlaceItem)placeItems.get(count);

                        //create bitmap
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), p.getIcon());

                        //set the title, description, position
                        mOptions1.title(count+","+p.getName());
                        mOptions1.snippet(p.getDescription());
                        mOptions1.position(p.getLocation());
                        mOptions1.icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bm, 70, 70, false)));

                        //add marker
                        map.addMarker(mOptions1);
                    }
                }
                hideActivitySpinner();
            }

        };

        api.v1.getAllPlaces(multipleCallback);

        //set the window adapter handler
        map.setInfoWindowAdapter(this);

        //set the infowindow click listener
        map.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("location","updating");
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 17));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(17)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if(api.v1.isLoggedIn()){
            Single single = new Single() {
                @Override
                public void results(Hashtable result) {
                    Enumeration keys = result.keys();
                    while(keys.hasMoreElements()){
                        Log.e("CRAP",keys.nextElement().toString());
                    }

                    Log.e("location", "LOCATIONUPDATE");
                    boolean error=toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString());
                }
            };

            api.v1.updateLocation(latLng.latitude,latLng.longitude,single);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

    @Override
    public View getInfoWindow(Marker m){
        return null;
    }

    /**
     * Getting the information from the marker when clicked
     * @param m the marker on the map
     * @return the view containing all the information
     */
    public View getInfoContents(Marker m){
        // Getting view from the layout file info_window_layout
        View v = View.inflate(context,R.layout.info_window_place_layout, null);

        // Getting the position from the marker
        LatLng latLng = m.getPosition();

        // Getting reference to the TextView to set latitude
        TextView title = (TextView) v.findViewById(R.id.place_title);

        // Getting reference to the TextView to set longitude
        TextView description = (TextView) v.findViewById(R.id.place_description);


        String[] idTitle=stripID(m.getTitle());




        // Setting the latitude
        title.setText(idTitle[1]);

        // Setting the longitude
        description.setText(m.getSnippet());

        // Returning the view containing InfoWindow contents
        return v;
    }

    /**
     * When marker is clicked, it swaps fragments
     * @param m the marker on the map
     */
    public void onInfoWindowClick(Marker m){
        resetIndexes();
        String[] idTitle=stripID(m.getTitle());
        fragmentManager.beginTransaction().add(R.id.container, PlaceProfileFragment.newInstance((PlaceItem) placeItems.get(Integer.valueOf(idTitle[0]))), "PlaceSubView").addToBackStack(null).commit();
       // m.getPosition().latitude;
    }

    /**
     * Separates id and place name
     * @param id id from the marker
     * @return an array of the id and marker name
     */
    public String[] stripID(String id){
        return id.split(",");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MapFragment f = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }
}
