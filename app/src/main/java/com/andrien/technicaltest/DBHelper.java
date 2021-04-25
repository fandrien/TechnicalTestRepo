package com.andrien.technicaltest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String PHOTOS_TABLE_NAME = "photos";
    public static final String PHOTOS_COLUMN_ALBUM_ID = "albumId";
    public static final String PHOTOS_COLUMN_ID = "id";
    public static final String PHOTOS_COLUMN_TITLE = "title";
    public static final String PHOTOS_COLUMN_URL = "url";
    public static final String PHOTOS_COLUMN_THUMBNAIL_URL= "thumbnailUrl";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table photos " +
                        "(id integer primary key, albumId integer,title text,url text, thumbnailUrl text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS photos");
        onCreate(db);
    }

    public boolean insertPhoto (int albumId, int id, String title, String url,String thumbnailUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("albumId", albumId);
        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("url", url);
        contentValues.put("thumbnailUrl", thumbnailUrl);
        db.insert("photos", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from photos where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PHOTOS_TABLE_NAME);
        return numRows;
    }



    public ArrayList<Photo> getAllPhotos() {
        ArrayList<Photo> photos = new ArrayList<Photo>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from photos", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            photos.add(new Photo(res.getInt(res.getColumnIndex(PHOTOS_COLUMN_ALBUM_ID)),
                    res.getInt(res.getColumnIndex(PHOTOS_COLUMN_ID)),
                    res.getString(res.getColumnIndex(PHOTOS_COLUMN_TITLE)),
                    res.getString(res.getColumnIndex(PHOTOS_COLUMN_URL)),
                    res.getString(res.getColumnIndex(PHOTOS_COLUMN_THUMBNAIL_URL))

                    ));
            res.moveToNext();
        }
        return photos;
    }
}