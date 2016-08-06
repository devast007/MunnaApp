package com.example.devast007.munnaapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.devast007.munnaapp.R;
import com.example.devast007.munnaapp.util.JasonDataParser;
import com.example.devast007.munnaapp.util.MunnaUtil;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private static final String FIREBASE_URL = "https://boiling-torch-880.firebaseio.com";
    private View mControlsView;
    private Firebase mFirebase;
    private Context mContext ;
    private static String DBG_TAG = "SplashscreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mContext = this;
        Firebase.setAndroidContext(this);
        mContentView = findViewById(R.id.fullscreen_content);
        mFirebase = new Firebase(FIREBASE_URL);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mFirebase.child("study").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //dataSnapshot.getRef().push().getKey() ;
                new DataParseThread(dataSnapshot).start();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    class DataParseThread extends  Thread{
        DataSnapshot data ;
        DataParseThread(DataSnapshot d){
            data = d;
        }
        @Override
        public void run() {
            Intent intent = new Intent(mContext, MainActivity.class);
           SharedPreferences preferences =  getSharedPreferences("version",MODE_PRIVATE);
            String str = preferences.getString("value", "-1");
            log("version == "+str);
            if(!data.child("version").getValue().toString().equals(str)) {
                new JasonDataParser(mContext, data, data.child("version").getValue().toString() ).addToDB();
            }
            log("data.getChildrenCount() :: "+data.getChildrenCount() );
           startActivity(intent);
            finish();

        }
    }

    private static void log(String s){
        Log.i(DBG_TAG, "MYLOG : "+s);
    }
}