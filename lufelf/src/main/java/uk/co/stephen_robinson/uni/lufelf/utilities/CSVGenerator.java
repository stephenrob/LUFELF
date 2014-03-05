package uk.co.stephen_robinson.uni.lufelf.utilities;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author James
 * creates a csv file for the blacklist
 */
public class CSVGenerator {

    private String filename;

    /**
     * create the csv file for the blacklist
     */
    public CSVGenerator(){
        File folder = new File(Environment.getExternalStorageDirectory()+ "/LUFELF");


        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();

        filename = folder.toString() + "/" + "EventBlackList.csv";
    }

    /**
     * add an event to the blacklist
     * @param eventID the event id to be added
     */
    public void append(String eventID){
        ArrayList<String> allData=getAll();
        if(!allData.contains(eventID)){
            try{
                FileWriter fw = new FileWriter(filename,true);
                fw.append(eventID + ",");
                fw.close();
            }catch(Exception e){
                Log.e("exception!",Log.getStackTraceString(e));
            }
        }
    }

    /**
     * clears the blacklist
     */
    public void clearAll(){
        try{
            FileWriter fw = new FileWriter(filename);
            fw.append("");
            fw.close();
        }catch(Exception e){
            Log.e("exception!",Log.getStackTraceString(e));
        }
    }

    /**
     * returns a list of ids held in the blacklist
     * @return list of event ids
     */
    public ArrayList<String> getAll(){
        ArrayList<String> ids= new ArrayList<String>();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                for(int i=0;i<rowData.length;i++)
                    ids.add(rowData[i]);

            }
            reader.close();
        }catch(Exception e){

        }
        return ids;
    }
}
