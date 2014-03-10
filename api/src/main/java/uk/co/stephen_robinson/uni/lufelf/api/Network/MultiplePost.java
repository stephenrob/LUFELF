package uk.co.stephen_robinson.uni.lufelf.api.Network;

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
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.NetworkHelper;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * Created by Stephen on 20/02/14.
 */

/**
 * @author stephen
 * Generic async task class for issuing a multiple post request to the server and parsing the result
 */
public class MultiplePost extends AsyncTask<List<NameValuePair>, Integer, ArrayList> {

    private Script serverScript = null;
    private HttpClient serverClient;
    private Multiple multipleCallback;
    private int connectionTimeout = 5000;
    private int socketTimeout = 7000;
    private HttpParams httpParams;

    /**
     * Constructor method, sets the local callback and script being used
     *
     * @param mc Multiple callback method to return data to
     * @param script Script being accessed on the server
     */

    public MultiplePost(Multiple mc, Script script){
        this.multipleCallback = mc;
        this.serverScript = script;
        serverClient = new DefaultHttpClient();
        httpParams = new BasicHttpParams();
    }

    /**
     * Executed in the background of the async task, sends request to the server and parses the result
     *
     * @param params server post parameters
     * @return arraylist of results which is immediately passed to the onPostExecute method
     */
    @Override
    protected ArrayList doInBackground(List<NameValuePair>... params) {

        ArrayList result = new ArrayList();

        if(!Api.isNetworkAvailable()){

            result.add(new Message(400, "fail", "No network available"));

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
                    result =  NetworkHelper.formatMultipleResults(this.serverScript, responseText);
                    break;

                default:
                    result = null;
                    break;
            }

        } catch (Exception e){
            result = new ArrayList();
            Log.e("crap",Log.getStackTraceString(e));
            result.add(new Message(400, "fail", "Error"));

            return result;
        }

        return result;
    }

    /**
     * Code to be executed on the ui thread when the async task completes
     *
     * @param list arraylist of results returned from the doInBackground method
     */

    @Override
    protected void onPostExecute(ArrayList list) {
        multipleCallback.results(list);
    }
}
