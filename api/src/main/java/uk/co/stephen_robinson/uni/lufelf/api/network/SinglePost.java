package uk.co.stephen_robinson.uni.lufelf.api.network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.NetworkHelper;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Formatter;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * Created by Stephen on 20/02/14.
 */

/**
 * @author stephen
 * Generic async task class for issuing a single post request to the server and parsing the result
 */
public class SinglePost extends AsyncTask<List<NameValuePair>, Integer, Hashtable> {

    private Script serverScript = null;
    private HttpClient serverClient;
    private Single singleCallback;
    private int connectionTimeout = 5000;
    private int socketTimeout = 7000;
    private HttpParams httpParams;

    /**
     * Constructor method, sets the local callback and script variables
     * @param sc Single callback method to run when the async task completes
     * @param script Script of the server to call
     */
    public SinglePost(Single sc, Script script){
        this.singleCallback = sc;
        this.serverScript = script;
        serverClient = new DefaultHttpClient();
        httpParams = new BasicHttpParams();
    }

    /**
     * Executed in the background of the async task, sends request to the server and parses the result
     *
     * @param params server post parameters
     * @return Hashtable of returned data, passed immediately to onPostExecute
     */
    @Override
    protected Hashtable doInBackground(List<NameValuePair>... params) {

        Hashtable result = new Hashtable();

        if(!Api.isNetworkAvailable()){

            result = Formatter.message(new Message(400, "fail", "No network available"));

            return result;
        }

        HttpPost postData = new HttpPost(serverScript.protocol.getProtocol() + Helper.SERVER_IP_ADDRESSS + serverScript.path);
        HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);

        try {
            postData.setEntity(new UrlEncodedFormEntity(params[0]));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseText = serverClient.execute(postData, responseHandler);

            switch(Api.getVersion()){
                case 1:
                    result =  NetworkHelper.formatResult(this.serverScript, responseText);
                    break;

                default:
                    result = null;
                    break;
            }

        } catch (Exception e){
            result = Formatter.message(new Message(400, "fail", "Error"));

            return result;
        }

        return result;
    }

    /**
     * Called once the async task has completed, executes on the UI thread
     *
     * @param hashtable result returned from doInBackground
     */
    @Override
    protected void onPostExecute(Hashtable hashtable) {
        singleCallback.results(hashtable);
    }
}
