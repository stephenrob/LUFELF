package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.activities.MainActivity;
import uk.co.stephen_robinson.uni.lufelf.api.V1;
import uk.co.stephen_robinson.uni.lufelf.utilities.ToastMaker;
import uk.co.stephen_robinson.uni.lufelf.utilities.UploadImage;

/**
 * @author James
 * the fragment that all fragments extend from. Implements the methods that are used by all fragments
 */
public class BaseFragment  extends Fragment{
    //init global vars
    private Dialog dialog;
    protected FragmentManager fragmentManager;
    protected Context context;
    public V1 api = new V1();
    public ToastMaker toastMaker;
    protected View rootView;
    private Uri imageURI;
    private Uri tempDir;

    /**
     * Set the fragment manager
     * @param fragmentManager the chosen fragment manager
     */
    //set fragmentManager
    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }

    /**
     * Set the context
     * @param context the chosen context
     */
    //set the context
    public void setContext(Context context){
        this.context=context;
        this.toastMaker=new ToastMaker(context);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = (Fragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }
    /**
     * method used to check if the network is currently available.
     * @return Boolean that indicates if a data connection is available.
     */

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * reset the indexes of the navigation drawer fragment
     */
    //
    public void resetIndexes(){
        MainActivity mainActivity=(MainActivity)getActivity();
        mainActivity.mNavigationDrawerFragment.updateChildGroup(-1,-1);
        mainActivity.updateCurrentPositions(-1,-1);
    }

    /**
     * get the current location of the device
     * @return A LatLng of the current location of the device
     */
    //
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

    /**
     * present a custom dialog to the user in order to obtain the resultingimage. MUST HAVE R.id.profile_image implemented in the layout.
     */
    //
    public void showCameraDialog(){
        final Dialog showMessage = new Dialog(context);
        showMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    /**
     *when the user has selected the image or taken a picture, the resulting action occurs here. MUST HAVE R.id.profile_image implemented in the layout.
     * @param requestCode the code returned from the intent
     * @param resultCode success/fail code returned from the intent
     * @param imageReturnedIntent the data returned from the intent
     */
    //
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

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
                            startCropIntent(selectedImage);

                        }else{
                            //handle the bug on certain devices where the image isn't returned normally.
                            startCropIntent(imageURI);
                            //UploadImage imageUploader = new UploadImage("214",getRealPathFromURI(imageURI),UploadImage.PLACE);
                            //imageUploader.uploadToServer();
                        }
                    }
                }
                break;
            case 1:
                if(resultCode == getActivity().RESULT_OK){
                    //user has selected from gallery
                    //set the imageview
                    Uri selectedImage = imageReturnedIntent.getData();

                    startCropIntent(selectedImage);
                }
                break;
            case 2:
                if(resultCode != getActivity().RESULT_CANCELED){
                    setImage();
                    UploadImage imageUploader = new UploadImage("215",tempDir.getPath(),UploadImage.AVATAR);
                    imageUploader.uploadToServer();
                }
                break;
        }

    }

    //when the fragment is loaded hide the activity spinner - this will be changed
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        dialog.dismiss();
    }
    public void setImage(){
        ImageView imageView=(ImageView)rootView.findViewById(R.id.profile_image);
        Bitmap temp=null;
        try{
            temp=MediaStore.Images.Media.getBitmap(context.getContentResolver(), tempDir);
        }catch (Exception e){
            Log.e("crap", e.toString());
        }
        /*if(temp == null)

        else*/
        //imageView.setImageURI(tempDir);
        imageView.setImageBitmap(getRoundedCornerBitmap(temp,20));
    }

    /**
     * Begins a crop intent of the selected image
     * @param data the URI of the selected image
     */
    public void startCropIntent(Uri data){
        tempDir=Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "tmp_file_store.jpg"));
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setData(data);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("output", tempDir);
        startActivityForResult(intent, 2);
    }

    /**
     * Rounds an image
     * @param src the source bitmap
     * @param radius the radius of the corner
     * @return  new bitmap with rounded corners
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap src, int radius) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        RectF rect = new RectF(0, 0, width, height);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, 0, 0, paint);
        paint.setXfermode(null);

        return result;
    }

    /**
     * hash a string using md5
     * @param s the string to hash
     * @return a hashed string or a blank string
     */
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void resetEditText(EditText editText){
        editText.setError(null);
    }
}
