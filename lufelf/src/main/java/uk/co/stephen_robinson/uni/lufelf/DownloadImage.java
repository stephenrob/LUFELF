package uk.co.stephen_robinson.uni.lufelf;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by James on 18/02/2014.
 */
public class DownloadImage{
    //global variables
    private ImageView imageView;
    private Activity activity;
    private int resp;
    private InputStream inputStream;
    private Bitmap image;
    private String imageUrl;

    public DownloadImage(ImageView imageView, Activity activity, String imageUrl){
        //set the global variables
        this.imageView=imageView;
        this.activity=activity;
        this.imageUrl=imageUrl;
    }
    //downloads image from server and places it in the desired image view
    public void downloadFromServer(){
        new AsyncTask<Void,Void,Void>(){

            protected void onPreExecute() {
            }

            protected Void doInBackground(Void... Params) {
                //set inputstream,image and resp
                inputStream = null;
                image=null;
                resp = -1;

                try {
                    //init new url based on imageURl
                    URL url = new URL(imageUrl);

                    //open connection
                    URLConnection conn = url.openConnection();

                    //cast that connection to a httpurlconnection
                    HttpURLConnection httpConn = (HttpURLConnection) conn;
                    httpConn.setAllowUserInteraction(false);
                    httpConn.setInstanceFollowRedirects(true);
                    httpConn.setRequestMethod("GET");

                    //get the response code
                    resp = httpConn.getResponseCode();

                    //place the returned data into input stream
                    if (resp == HttpURLConnection.HTTP_OK) {
                        inputStream = httpConn.getInputStream();
                    }
                    //decode that input stream and place it in the image global var
                    image=BitmapFactory.decodeStream(inputStream);
                    inputStream.close();

                } catch (Exception e) {
                    Log.e("Crap", e.toString());
                }

                //we can't update ui stuff from the background thread
                //run this code on the ui thread.
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp != -1){
                            //set the image bitmap to the downloaded image
                            imageView.setImageBitmap(image);
                        }

                    }
                });
                return null;
            }

            protected void onPostExecute() {
            }
        }.execute();
    }
}
