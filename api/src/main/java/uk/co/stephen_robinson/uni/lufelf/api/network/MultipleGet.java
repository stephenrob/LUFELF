package uk.co.stephen_robinson.uni.lufelf.api.Network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.NetworkHelper;

/**
 * Created by Stephen on 22/02/2014.
 */
public class MultipleGet extends AsyncTask<Void, Integer, List> {

    private Script serverScript = null;
    private HttpClient serverClient;
    private Multiple multipleCallback;


    public MultipleGet(Multiple mc, Script script) {
        this.multipleCallback = mc;
        this.serverScript = script;
        serverClient = new DefaultHttpClient();
    }

    @Override
    protected List doInBackground(Void... voids) {

        List result = new ArrayList();

        HttpGet getData = new HttpGet(serverScript.protocol.getProtocol() + Helper.SERVER_IP_ADDRESSS + serverScript.path);

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
            Log.e("LUFELF API", e.toString());
        }

        return result;
    }

    @Override
    protected void onPostExecute(List list) {
        multipleCallback.results(list);
    }
}
