package uk.co.stephen_robinson.uni.lufelf.api.Network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.NetworkHelper;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * Created by Stephen on 22/02/2014.
 */
public class MultipleGet extends AsyncTask<Void, Integer, ArrayList> {

    private Script serverScript = null;
    private HttpClient serverClient;
    private Multiple multipleCallback;


    public MultipleGet(Multiple mc, Script script) {
        this.multipleCallback = mc;
        this.serverScript = script;
        serverClient = new DefaultHttpClient();
    }

    @Override
    protected ArrayList doInBackground(Void... voids) {

        ArrayList result = new ArrayList();

        if(!Api.isNetworkAvailable()){

            result.add(new Message(400, "fail", "No network available"));

            return result;
        }

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
            Log.e("LUFELF API", Log.getStackTraceString(e));
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList list) {
        multipleCallback.results(list);
    }
}
