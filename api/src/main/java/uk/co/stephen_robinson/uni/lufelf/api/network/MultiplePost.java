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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.NetworkHelper;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * Created by Stephen on 20/02/14.
 */
public class MultiplePost extends AsyncTask<List<NameValuePair>, Integer, ArrayList> {

    private Script serverScript = null;
    private HttpClient serverClient;
    private Multiple multipleCallback;

    public MultiplePost(Multiple mc, Script script){
        this.multipleCallback = mc;
        this.serverScript = script;
        serverClient = new DefaultHttpClient();
    }

    @Override
    protected ArrayList doInBackground(List<NameValuePair>... params) {

        ArrayList result = new ArrayList();

        if(!Api.isNetworkAvailable()){

            result.add(new Message(400, "fail", "No network available"));

            return result;
        }

        HttpPost postData = new HttpPost(serverScript.protocol.getProtocol() + Helper.SERVER_IP_ADDRESSS + serverScript.path);

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

            result.add(new Message(400, "fail", "Error"));

            return result;
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList list) {
        multipleCallback.results(list);
    }
}
