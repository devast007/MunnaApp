package com.example.devast007.munnaapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.devast007.munnaapp.db.DBInfoConstants;
import com.firebase.client.DataSnapshot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by devast007 on 24/7/16.
 */
public class JasonDataParser {
    private static Context mContext ;
    private static DataSnapshot mSnapshot;
    static String mVersion ;
    private static String DBG_TAG = "JasonDataParser";

    public JasonDataParser(Context ctx,DataSnapshot data, String version){
        mContext = ctx ;
        mSnapshot = data;
        mVersion = version ;
        log("data"+data);
    }

    public static void addToDB(){
        if(mSnapshot !=null){
           Iterable<DataSnapshot> iterable_1 = mSnapshot.getChildren();
            Iterator<DataSnapshot> iterator_1 = iterable_1.iterator();
            log("delete =  "+mContext.getContentResolver().delete(DBInfoConstants.Study.CONTENT_URI,null,null)) ;
            while(iterator_1.hasNext()){
                ContentValues cv = new ContentValues();
               DataSnapshot snapshot_1 = iterator_1.next();
                if("version".equals(snapshot_1.getKey())){
                    continue;
                }
                Iterable<DataSnapshot> iterable_2 = snapshot_1.getChildren();
                Iterator<DataSnapshot> iterator_2 = iterable_2.iterator();
                while(iterator_2.hasNext()){
                    DataSnapshot snapshot_2 = iterator_2.next();
                    String key = snapshot_2.getKey();
                    if("name".equals(key)){
                        String name = snapshot_2.getValue().toString();
                        cv.put(DBInfoConstants.Study.CENTER_NAME, name);
                        log("2nd loop :: "+name ) ;
                    }else if("description".equals(key)){
                        String desc = snapshot_2.getValue().toString();
                        log("2nd loop :: "+desc) ;
                        cv.put(DBInfoConstants.Study.SUBJECT, desc);
                    }else if("teachers".equals(key)){
                        String t_name = null ;
                        String t_sub = null;
                        StringBuffer sb = new StringBuffer();
                        Iterable<DataSnapshot> iterable_3 = snapshot_2.getChildren();
                        Iterator<DataSnapshot> iterator_3 = iterable_3.iterator();
                        while(iterator_3.hasNext()){
                            DataSnapshot snapshot_3 = iterator_3.next();
                            t_name = snapshot_3.getKey();
                            t_sub = snapshot_3.getValue().toString();
                            sb.append(t_name).append(" - ").append(t_sub).append(",");
                        }
                        log("teachers :: "+sb ) ;
                        cv.put(DBInfoConstants.Study.TEACHER, sb.toString());
                    }else if("rating".equals(key)){
                        String u_name = null ;
                        String rating = null;
                        StringBuffer sb = new StringBuffer();
                        Iterable<DataSnapshot> iterable_4 = snapshot_2.getChildren();
                        Iterator<DataSnapshot> iterator_4 = iterable_4.iterator();
                        while(iterator_4.hasNext()){
                            DataSnapshot snapshot_4 = iterator_4.next();
                            u_name = snapshot_4.getKey();
                            rating = snapshot_4.getValue().toString();
                            sb.append(u_name).append(" - ").append(rating).append(",");
                        }
                        log("rating :: "+sb ) ;
                        cv.put(DBInfoConstants.Study.RATINGS, sb.toString());
                    }else if("timing".equals(key)){
                        String sub = null ;
                        String time = null;
                        StringBuffer sb = new StringBuffer();
                        Iterable<DataSnapshot> iterable_5 = snapshot_2.getChildren();
                        Iterator<DataSnapshot> iterator_5 = iterable_5.iterator();
                        while(iterator_5.hasNext()){
                            DataSnapshot snapshot_5 = iterator_5.next();
                            sub = snapshot_5.getKey();
                            time = snapshot_5.getValue().toString();
                                sb.append(sub).append(" - ").append(time).append(",");
                        }
                        log("timing :: "+sb ) ;
                        cv.put(DBInfoConstants.Study.TIMING, sb.toString());
                    }
                }
                log("insert =  "+mContext.getContentResolver().insert(DBInfoConstants.Study.CONTENT_URI, cv)) ;
                SharedPreferences preferences =  mContext.getSharedPreferences("version",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit() ;
                editor.putString("value",mVersion);
                editor.commit();
            }
        }
    }
    private static  void log(String s){
        Log.i(DBG_TAG, "MYLOG : "+s);
    }
}
