package uk.co.stephen_robinson.uni.lufelf;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Field;

/**
 * Created by James on 30/01/2014.
 */
public class BaseFragment  extends Fragment{
    //init global vars
    private Dialog dialog;
    protected FragmentManager fragmentManager;
    protected Context context;

    protected View rootView;
    private Uri imageURI;

    //set fragmentManager
    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }

    //set the context
    public void setContext(Context context){
        this.context=context;
    }

    //onAttach override - does nothing
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //handle onDetach from activity
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

    //method used to check if the network is currently available.
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //reset the indexes of the navigation drawer fragment
    public void resetIndexes(){
        MainActivity mainActivity=(MainActivity)getActivity();
        mainActivity.mNavigationDrawerFragment.updateChildGroup(-1,-1);
        mainActivity.updateCurrentPositions(-1,-1);
    }

    //get the current location of the device
    public LatLng getLocation(){
        //get the app location manager via context
        LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        //initialise variables
        double latitude =0;
        double longitude =0;

        //check if the location isn't enabled, return null
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            return null;

        //otherwise get the location
        Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        longitude=loc.getLongitude();
        latitude=loc.getLatitude();

        //return LatLng
        return new LatLng(latitude,longitude);
    }

    //present a custom dialog to the user in order to obtain the resultingimage
    //MUST HAVE R.id.profile_image implemented in the layout.
    public void showCameraDialog(){
        final Dialog showMessage = new Dialog(context);
        showMessage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showMessage.setContentView(R.layout.alert_layout);

        TextView titleText = (TextView) showMessage.findViewById(R.id.alert_title);
        TextView messageText = (TextView) showMessage.findViewById(R.id.alert_message);
        Button buttonOption1 = (Button) showMessage.findViewById(R.id.alert_button_pos);
        Button buttonOption2 = (Button) showMessage.findViewById(R.id.alert_button_neg);

        titleText.setText("Select Resource");
        messageText.setText("Would you like to take a new photo? Or choose one from the Gallery?");

        buttonOption1.setText("Take A New Photo");
        buttonOption2.setText("Gallery");

        buttonOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new ContentValues(0));
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
                startActivityForResult(takePicture, 0);
                showMessage.hide();
            }
        });
        buttonOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
                showMessage.hide();
            }
        });
        showMessage.show();
    }

    //when the user has selected the image or taken a picture, the resulting action occurs here.
    //MUST HAVE R.id.profile_image implemented in the layout.
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        ImageView imageView=(ImageView)rootView.findViewById(R.id.profile_image);
        switch(requestCode) {
            case 0:
                //check if the user hasn't cancelled input
                if(resultCode != getActivity().RESULT_CANCELED){
                    //if the resultcode is ok move on
                    if(resultCode == getActivity().RESULT_OK){
                        //check if the image intent is null
                        if(imageReturnedIntent!=null){

                            //get the uri data
                            Uri selectedImage = imageReturnedIntent.getData();
                            //set the imageview
                            if(selectedImage!=null){
                                imageURI=selectedImage;
                                imageView.setImageURI(imageURI);
                            }
                        }else{
                            //handle the bug on certain devices where the image isn't returned normally.
                            Bitmap bitmap;
                            try{
                                bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageURI);
                                imageView.setImageBitmap(bitmap);
                            }catch (Exception e){

                            }
                        }
                    }
                }
                break;
            case 1:
                if(resultCode == getActivity().RESULT_OK){
                    //user has selected from gallery
                    //set the imageview
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(null);
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }

    //when the fragment is loaded hide the activity spinner - this will be changed
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(dialog!=null)
            hideActivitySpinner();
    }

    //a method to build the activity spinner dialog
    public void showActivitySpinner(){
        dialog = new Dialog(context, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_indicator);
        dialog.setCancelable(false);
        dialog.show();
    }

    //hide the activity spinner dialog
    public void hideActivitySpinner(){
        dialog.hide();
    }
}
