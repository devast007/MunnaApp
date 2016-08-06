package com.example.devast007.munnaapp.db;

import android.net.Uri;

/**
 * Created by devast007 on 3/8/16.
 */
public class DBInfoConstants {

    public static final String DB_NAME = "munnaDB.db";
    public static final String AUTHORITY = "com.example.devast007.munnaapp";
    /*Study ---> Starts*/
    public static final class Study {
        public static final int COL_ID = 0;
        public static final int COL_CENTER_NAME = 1;
        public static final int COL_TEACHER = 2;
        public static final int COL_SUBJECT = 3;
        public static final int COL_TIMING = 4;
        public static final int COL_RATINGS = 5;
        public static final int COL_MEDIA = 6;

        public static final String ID = "_id";
        public static final String CENTER_NAME = "center_name";
        public static final String TEACHER = "teacher";
        public static final String SUBJECT = "subject";
        public static final String TIMING = "timing";
        public static final String RATINGS = "ratings";
        public static final String MEDIA = "media";

        public static final String TABLE = "study";
        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+TABLE);
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE + "(" + ID + " integer primary key autoincrement,"
                + CENTER_NAME + " text," + TEACHER + " text," + SUBJECT+ " text," +TIMING + " text," +RATINGS +" text,"+MEDIA+" text "+ ");" ;
    }
    /*Study ---> Ends*/


    /*News ---> Starts*/
    public static final class News {
        public static final int COL_ID = 0;
        public static final int COL_TITLE = 1;
        public static final int COL_TIME = 2;
        public static final int COL_DESCRIPTION = 3;
        public static final int COL_MEDIA = 4;
        public static final int COL_SOURCE = 5;

        public static final String ID = "_id";
        public static final String TITLE = "title";
        public static final String TIME = "time";
        public static final String DESCRIPTION = "description";
        public static final String MEDIA = "media";
        public static final String SOURCE = "source";

        public static final String TABLE = "news";
        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+TABLE);
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE + "(" + ID + " integer primary key autoincrement,"
                + TITLE + " text," + TIME + " text," +DESCRIPTION+" text," +MEDIA +" text,"+SOURCE +" text " + ");";

    }
    /*News ---> Ends*/


    /*Cinema ---> Starts*/
    public static final class Cinema {
        public static final int COL_ID = 0;
        public static final int COL_PLACE = 1;
        public static final int COL_NAME = 2;
        public static final int COL_TIME = 3;
        public static final int COL_RATINGS = 4;
        public static final int COL_DESCRIPTION = 5;
        public static final int COL_MEDIA = 6;

        public static final String ID = "_id";
        public static final String PLACE = "place";
        public static final String NAME = "name";
        public static final String TIME = "time";
        public static final String RATINGS = "ratings";
        public static final String DESCRIPTION = "description";
        public static final String MEDIA = "media";

        public static final String TABLE = "cinema";
        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+TABLE);
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE + "(" + ID + " integer primary key autoincrement,"
                + PLACE + " text," + NAME + " text," + TIME + " text,"+RATINGS+" text," +DESCRIPTION+" text,"+ MEDIA +" text " + ");";

    }
    /*Cinema ---> Ends*/


    /*Medical ---> Starts*/
    public static final class Medical {

        public static final int COL_ID = 0;
        public static final int COL_NAME= 1;
        public static final int COL_PLACE  = 2;
        public static final int COL_TIME = 3;
        public static final int COL_RATINGS = 4;
        public static final int COL_DESCRIPTION = 5;
        public static final int COL_MEDIA = 6;

        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String PLACE = "place";
        public static final String TIME = "time";
        public static final String RATINGS = "ratings";
        public static final String DESCRIPTION = "description";
        public static final String MEDIA = "media";

        public static final String TABLE = "medical";
        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+TABLE);
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE + "(" + ID + " integer primary key autoincrement,"
                + NAME + " text," + PLACE + " text," + TIME + " text,"+RATINGS+" text," +DESCRIPTION+" text,"+ MEDIA +" text " + ");";

    }
    /*Medical ---> Ends*/
}
