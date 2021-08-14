package com.vsga.aplikasisqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "digitaltalent.db";
    public static final String TABLE_SQLite = "sqlite";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_SQLite + " (" +
                ID + " INTEGER PRIMARY KEY autoincrement, " +
                NAME + " TEXT NOT NULL, " +
                ADDRESS + " TEXT NOT NULL " +
                " )";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAllData(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SQLite;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(ID, c.getString(0));
                map.put(NAME, c.getString(1));
                map.put(ADDRESS, c.getString(2));
                wordList.add(map);
            } while (c.moveToNext());
        }
        Log.e("select sqlite ", "" + wordList);

        database.close();
        return wordList;
    }

    public void insert(String name, String address){
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_SQLite + " (name, address) " +
                "VALUES ('" + name + "', '" + address + "')";

        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    public void update(int id, String name, String address){
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TABLE_SQLite + " SET "
                + NAME + "='" + name + "', "
                + ADDRESS + "='" + address + "'"
                + " WHERE " + ID + "=" + "'" + id + "'";
        Log.e("update sqlite", "" + updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void delete(int id){
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "DELETE FROM " + TABLE_SQLite + " WHERE " + ID + "=" + "'" + id + "'";
        Log.e("update sqlite", "" + updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
