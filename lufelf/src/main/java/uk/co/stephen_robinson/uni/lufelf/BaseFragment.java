package uk.co.stephen_robinson.uni.lufelf;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Field;
/**
 * Created by James on 30/01/2014.
 */
public class BaseFragment  extends Fragment{
    protected FragmentManager fragmentManager;
    protected Context context;

    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }
    public void setContext(Context context){
        this.context=context;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void resetIndexes(){
        MainActivity mainActivity=(MainActivity)getActivity();
        mainActivity.mNavigationDrawerFragment.updateChildGroup(-1,-1);
        mainActivity.updateCurrentPositions(-1,-1);
    }
    public LatLng getLocation(){
        LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        double latitude =0;
        double longitude =0;

        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            return null;

        Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        longitude=loc.getLongitude();
        latitude=loc.getLatitude();

        return new LatLng(latitude,longitude);
    }
}
