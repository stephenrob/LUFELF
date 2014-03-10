package uk.co.stephen_robinson.uni.lufelf.api.network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.NetworkHelper;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * Created by Stephen on 22/02/2014.
 */

/**
 * @author stephen
 * Generic async task class for issuing a multiple get request to the server and parsing the result
 */
public class MultipleGet extends AsyncTask<Void, Integer, ArrayList> {

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

    public MultipleGet(Multiple mc, Script script) {
        this.multipleCallback = mc;
        this.serverScript = script;
        serverClient = new DefaultHttpClient();
        httpParams = new BasicHttpParams();
    }

    /**
     * Method which is executed in the background of the async task
     *
     * @param voids No parameters passed through
     * @return array list of results, immediately passed into onPostExecute
     */

    @Override
    protected ArrayList doInBackground(Void... voids) {

        ArrayList result = new ArrayList();

        if(!Api.isNetworkAvailable()){

            result.add(new Message(400, "fail", "No network available"));

            return result;
        }

        HttpGet getData = new HttpGet(serverScript.protocol.getProtocol() + Helper.SERVER_IP_ADDRESSS + serverScript.path);
        HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);

        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseText = serverClient.execute(getData, responseHandler);

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

            result.add(new Message(400, "fail", "Error"));

            return result;
        }

        return result;
    }

    /**
     * Anything to execute after the async task has completed
     *
     * @param list array list of data returned from the server and parser
     */
    @Override
    protected void onPostExecute(ArrayList list) {
        multipleCallback.results(list);
    }
}
