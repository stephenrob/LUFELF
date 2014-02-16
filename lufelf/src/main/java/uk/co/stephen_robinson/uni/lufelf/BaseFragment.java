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
import android.util.Log;
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
    private int option=0;
    private Dialog dialog;
    protected FragmentManager fragmentManager;
    protected Context context;

    protected View rootView;
    private Uri imageURI;

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

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        ImageView imageView=(ImageView)rootView.findViewById(R.id.profile_image);
        switch(requestCode) {
            case 0:
                if(resultCode != getActivity().RESULT_CANCELED){
                    if(resultCode == getActivity().RESULT_OK){
                        if(imageReturnedIntent!=null){
                            Log.e("CRAP","inside camera");
                            Uri selectedImage = imageReturnedIntent.getData();

                            if(selectedImage!=null){
                                imageURI=selectedImage;
                                imageView.setImageURI(imageURI);
                            }
                        }else{
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
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(null);
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideDialog();
    }

    public void showDialog(){
        dialog = new Dialog(context, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_indicator);
        dialog.setCancelable(false);
    }
    public void hideDialog(){
        dialog.hide();
    }
}
