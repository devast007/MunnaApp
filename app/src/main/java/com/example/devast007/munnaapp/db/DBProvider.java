package com.example.devast007.munnaapp.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by devast007 on 3/8/16.
 */
public class DBProvider extends ContentProvider {

    private static String DBG_TAG = "DBProvider";
    private static  UriMatcher mMatcher ;
    private static final int STUDY = 0;
    private static final int CINEMA = 1;
    private static final int NEWS = 2;
    private static final int MEDICAL = 3;
    private DBHelper mDBHelper ;
    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(DBInfoConstants.AUTHORITY, "/"+DBInfoConstants.Study.TABLE, STUDY);
        mMatcher.addURI(DBInfoConstants.AUTHORITY, "/"+DBInfoConstants.Study.TABLE+"/#", STUDY);
        mMatcher.addURI(DBInfoConstants.AUTHORITY, "/"+DBInfoConstants.Cinema.TABLE, CINEMA);
        mMatcher.addURI(DBInfoConstants.AUTHORITY, "/"+DBInfoConstants.Cinema.TABLE+"/#", CINEMA);
        mMatcher.addURI(DBInfoConstants.AUTHORITY, "/"+DBInfoConstants.News.TABLE, NEWS);
        mMatcher.addURI(DBInfoConstants.AUTHORITY, "/"+DBInfoConstants.News.TABLE+"/#", NEWS);
        mMatcher.addURI(DBInfoConstants.AUTHORITY, "/"+DBInfoConstants.Medical.TABLE, MEDICAL);
        mMatcher.addURI(DBInfoConstants.AUTHORITY, "/"+DBInfoConstants.Medical.TABLE+"/#", MEDICAL);

}

    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, DBInfoConstants.DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            log("onCretae for SQLiteOpenHelper starts");
            sqLiteDatabase.execSQL(DBInfoConstants.Study.CREATE_TABLE);
            sqLiteDatabase.execSQL(DBInfoConstants.News.CREATE_TABLE);
            sqLiteDatabase.execSQL(DBInfoConstants.Cinema.CREATE_TABLE);
            sqLiteDatabase.execSQL(DBInfoConstants.Medical.CREATE_TABLE);

            log("onCretae for SQLiteOpenHelper Ends");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBInfoConstants.Study.TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBInfoConstants.News.TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBInfoConstants.Cinema.TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBInfoConstants.Medical.TABLE);
            onCreate(sqLiteDatabase);
        }
    }
    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String tableName = getTable(uri);
        queryBuilder.setTables(tableName);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTable(uri);
        long id = -1;


        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        id = db.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(tableName + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = getTable(uri);
        long rowsDeleted = -1;

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        rowsDeleted = db.delete( tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return (int)rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = getTable(uri);
        long id = -1;

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        id = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return  (int)id;
    }


    private String getTable(Uri uri){
        String tableName = null ;
        log("mMatcher.match(uri)"+mMatcher.match(uri));
        switch (mMatcher.match(uri)){
            case STUDY:
                tableName = DBInfoConstants.Study.TABLE;
                break;
            case CINEMA:
                tableName = DBInfoConstants.Cinema.TABLE;
                break;
            case NEWS:
                tableName = DBInfoConstants.News.TABLE;
                break;
            case MEDICAL:
                tableName = DBInfoConstants.Medical.TABLE;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        return  tableName ;
    }

    private static void log(String s){
        Log.i(DBG_TAG, "MYLOG : "+s);
    }
}
