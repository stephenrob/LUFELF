package uk.co.stephen_robinson.uni.lufelf.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * @author James
 * A class that displays the toast messages returned from the api.
 * Determines if the returned value is an error and displays the message returned from the api.
 */
public class ToastMaker {

    private Context context;

    /**
     * give the class the app context
     * @param context the application context
     */
    public ToastMaker(Context context){
        this.context=context;
    }

    /**
     * determines whether the code returned from the server is an error
     * @param code the code returned from the api
     * @param message the message returned from the api
     * @return if true there is an error, otherwise it is false
     */
    public boolean isError(String code,String message){
        int numberCode=-1;
        try{
            numberCode=Integer.valueOf(code);
        }catch(Exception e){

        }
        switch(numberCode){
            case 400:
                makeToast(message);
                return true;
            case 500:
                makeToast(message);
                return true;
            case -1:
                makeToast("An unknown error occurred.");
                return true;
            default:
                return false;
        }
    }

    /**
     * Makes a toast when give a message
     * @param message the message you would like to display
     */
    public void makeToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
