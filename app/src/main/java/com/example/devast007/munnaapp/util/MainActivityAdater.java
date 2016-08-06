package com.example.devast007.munnaapp.util;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.devast007.munnaapp.R;
import com.example.devast007.munnaapp.db.DBInfoConstants;
import com.firebase.client.DataSnapshot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by devast007 on 24/7/16.
 */
public class MainActivityAdater extends RecyclerView.Adapter<MainActivityAdater.ViewHolder> {

    private final Context mContext;
    private Cursor mCursor;
    private int mLayout ;
    private static String DBG_TAG = "MainActivityAdater";
    public MainActivityAdater(Context ctx, int layout, Cursor c){
        mContext = ctx ;
        mLayout =layout;
        mCursor = c ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch(mLayout){
            case R.layout.row_study:
                mCursor.moveToPosition(position);
                holder.textView1.setText(mCursor.getString(DBInfoConstants.Study.COL_CENTER_NAME));
                holder.textView2.setText(mCursor.getString(DBInfoConstants.Study.COL_SUBJECT));
                //holder.ratingbar.setRating(Float.valueOf(mCursor.getInt(DBInfoConstants.Study.COL_RATINGS)) );
                break;
            case R.layout.row_news:
                holder.textView1.setText(mCursor.getString(DBInfoConstants.News.COL_TITLE));
                holder.textView2.setText(mCursor.getString(DBInfoConstants.News.COL_SOURCE));
                break;
            case R.layout.row_cinema:
                holder.textView1.setText(mCursor.getString(DBInfoConstants.Cinema.COL_NAME));
                holder.textView2.setText(mCursor.getString(DBInfoConstants.Cinema.COL_PLACE));
                holder.ratingbar.setRating(Float.valueOf(mCursor.getInt(DBInfoConstants.Cinema.COL_RATINGS)) );
                break;
            case R.layout.row_medical:
                holder.textView1.setText(mCursor.getString(DBInfoConstants.Medical.COL_NAME));
                holder.textView2.setText(mCursor.getString(DBInfoConstants.Medical.COL_DESCRIPTION));
                holder.ratingbar.setRating(Float.valueOf(mCursor.getInt(DBInfoConstants.Medical.COL_RATINGS)) );
                break;
            default:
                log("default case ???????");

        }

    }


    @Override
    public int getItemCount() {
        return mCursor.getCount() ;
    }


    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView textView1=null, textView2=null;
        RatingBar ratingbar=null;
        public ViewHolder(View v) {
            super(v);
            switch(mLayout){
                case R.layout.row_study:
                    textView1 = (TextView) v.findViewById(R.id.name_study);
                    textView2 = (TextView) v.findViewById(R.id.place_study);
                    ratingbar = (RatingBar) v.findViewById(R.id.rating_study);
                    break;
                case R.layout.row_news:
                    textView1 = (TextView) v.findViewById(R.id.name_news);
                    textView2 = (TextView) v.findViewById(R.id.text_source_news);
                    break;
                case R.layout.row_cinema:
                    textView1 = (TextView) v.findViewById(R.id.name_cinema);
                    textView2 = (TextView) v.findViewById(R.id.place_cinema);
                    ratingbar = (RatingBar) v.findViewById(R.id.rating_cinema);
                    break;
                case R.layout.row_medical:
                    textView1 = (TextView) v.findViewById(R.id.name_medical);
                    textView2 = (TextView) v.findViewById(R.id.place_medical);
                    ratingbar = (RatingBar) v.findViewById(R.id.rating_medical);
                    break;
                default:
                    log("default case ???????");

            }
        }
    }
    private static void log(String s){
        Log.i(DBG_TAG, "MYLOG : "+s);
    }
}
