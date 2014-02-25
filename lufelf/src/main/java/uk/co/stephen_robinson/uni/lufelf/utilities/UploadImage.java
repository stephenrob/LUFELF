package uk.co.stephen_robinson.uni.lufelf.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;

/**
 * @author James
 * Uploads an image to server depending on type choosen
 */
public class UploadImage{

    Api api;
    //initialise ip constant
    private static final String ipAddress="148.88.32.47";

    //initalise the submission type constants (determines what php file to use)
    public static final String AVATAR ="upload_picture.php";
    public static final String PLACE ="upload_picture_place.php";
    public static final String EVENT ="upload_picture_event.php";

    //init global variables to hold important details
    private String user_id;
    private String path;
    private String type;
    private String query_id;

    /**
     * Standard constructor for avatar upload
     * @param user_id user id
     * @param path path of the image
     * @param type determines the type of upload
     */
    public UploadImage(String path, String type, Context context){
        this.api = new Api(context, Api.Version.V1);
        this.user_id=user_id;
        this.path=path;
        this.type=type;
    }

    /**
     * Constructor for uploading a place or event
     * @param path path of the image
     * @param type determines the type of upload
     * @param query_id id of place or event
     */
    public UploadImage(String path, String type,String query_id, Context context){
        this.api = new Api(context, Api.Version.V1);
        this.user_id=user_id;
        this.path=path;
        this.query_id=query_id;
        this.type=type;
    }

    /**
     * Method that takes a filepath and encodes it, this integrates with the image selection method implemented into the ui
     * @return base 64 encoded string
     */
    public String encodeImage(){
        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * Upload to the server
     */
    public void uploadToServer(){
        new AsyncTask<Void,Void,Void>(){

            protected void onPreExecute() {
            }

            protected Void doInBackground(Void... Params) {
                String response="";
                HttpClient client = new DefaultHttpClient();
                HttpPost postData = new HttpPost("http://"+ipAddress+"/"+type);
                HttpResponse resp;
                try{

                    //create list namevaluepair
                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();

                    //add the parameters
                    pairs.add(new BasicNameValuePair("user_id",api.v1.getCurrentId()));
                    pairs.add(new BasicNameValuePair("password",api.v1.getCurrentPassword()));
                    pairs.add(new BasicNameValuePair("image", encodeImage()));

                    //if the type is an event upload, then attach the event id
                    if(type.equals(EVENT))
                        pairs.add(new BasicNameValuePair("event_id",query_id));

                    //if the type is an place upload, then attach the place id
                    if(type.equals(PLACE))
                        pairs.add(new BasicNameValuePair("place_id",query_id));


                    //handle the response
                    ResponseHandler<String> responseHandler=new BasicResponseHandler();

                    //set the params
                    postData.setEntity(new UrlEncodedFormEntity(pairs));

                    //execute request
                    response = client.execute(postData,responseHandler);

                    //print result
                    Log.e("CRAP",response);
                    return null;
                }catch(Exception e){
                    Log.e("Crap",e.toString());
                }
                return null;
            }

            protected void onPostExecute() {
            }
        }.execute();
    }
}
