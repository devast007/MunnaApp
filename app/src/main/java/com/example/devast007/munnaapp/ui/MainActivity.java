package com.example.devast007.munnaapp.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.devast007.munnaapp.R;
import com.example.devast007.munnaapp.db.DBInfoConstants;
import com.example.devast007.munnaapp.util.MainActivityAdater;
import com.example.devast007.munnaapp.util.MunnaDefine;
import com.example.devast007.munnaapp.util.MunnaUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView ;
    private TextView tempView ;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String DBG_TAG = "MainActivity";
    private Context mContext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        tempView = (TextView) findViewById(R.id.textview_temp);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.search_not_available, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            mHandler.sendEmptyMessage(MunnaDefine.MSG_NAVI_NEWS);
        } else if (id == R.id.nav_study) {
            mHandler.sendEmptyMessage(MunnaDefine.MSG_NAVI_STUDY);
        } else if (id == R.id.nav_cinema) {
            mHandler.sendEmptyMessage(MunnaDefine.MSG_NAVI_CINEMA);
        } else if (id == R.id.nav_medical) {
            mHandler.sendEmptyMessage(MunnaDefine.MSG_NAVI_MEDICAL);
        } else if (id == R.id.nav_contact_us) {
            mHandler.sendEmptyMessage(MunnaDefine.MSG_NAVI_CONTACT_US);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MunnaDefine.MSG_NAVI_STUDY:
                    updateList(MunnaDefine.UPDATE_STUDY_LIST);
                    break ;
                case MunnaDefine.MSG_NAVI_CINEMA:
                    updateList(MunnaDefine.UPDATE_CINEMA_LIST);
                    break ;
                case MunnaDefine.MSG_NAVI_MEDICAL:
                    insert();
                    updateList(MunnaDefine.UPDATE_MEDICAL_LIST);
                    break ;
                case MunnaDefine.MSG_NAVI_NEWS:
                    updateList(MunnaDefine.UPDATE_NEWS_LIST);
                    break ;
                case MunnaDefine.MSG_NAVI_CONTACT_US:
                    updateList(MunnaDefine.UPDATE_CONTACT_US_LIST);
                    break ;
            }
        }
    };

    private void insert() {
        ContentValues values = new ContentValues();
        values.put(DBInfoConstants.News.TITLE, "Moti cinema");
        values.put(DBInfoConstants.News.MEDIA, "Image_path");
        values.put(DBInfoConstants.News.TIME, "12/12/2106");
        log( "insert :: "+mContext.getContentResolver().insert(DBInfoConstants.News.CONTENT_URI, values)) ;
    }

    private void updateList(int type) {
        if(type == MunnaDefine.UPDATE_STUDY_LIST){
            mRecyclerView.setVisibility(View.VISIBLE);
            tempView.setVisibility(View.GONE);
            mRecyclerView.setAdapter(new MainActivityAdater(mContext,R.layout.row_study,mContext.getContentResolver().query(DBInfoConstants.Study.CONTENT_URI,null,null,null,null)));
        }else{
            mRecyclerView.setVisibility(View.GONE);
            tempView.setVisibility(View.VISIBLE);
        }

    }

    private static void log(String s){
        Log.i(DBG_TAG, "MYLOG : "+s);
    }
}
