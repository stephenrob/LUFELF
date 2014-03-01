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
 * Created by James on 28/02/2014.
 */
public class CSVGenerator {
    private String filename;
    public CSVGenerator(){
        File folder = new File(Environment.getExternalStorageDirectory()+ "/LUFELF");

        Log.e("MADE",String.valueOf(folder.exists()));

        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();
        if(var)
            Log.e("MADE","FOLDER");
        Log.e("PATH",folder.toString());

        filename = folder.toString() + "/" + "EventBlackList.csv";
    }
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
    public void clearAll(){
        try{
            FileWriter fw = new FileWriter(filename);
            fw.append("");
            fw.close();
        }catch(Exception e){
            Log.e("exception!",Log.getStackTraceString(e));
        }
    }
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
