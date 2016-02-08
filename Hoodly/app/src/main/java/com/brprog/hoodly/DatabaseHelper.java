package com.brprog.hoodly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by User_1_Benjamin_Rosenthal on 2/5/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getCanonicalName();

    //03 Singleton
    private static DatabaseHelper mInstance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Hood.db";
    public static final String TABLE_NAME = "Hoods";
    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_ITEM_ADDRESS = "ITEM_ADDRESS";
    public static final String COL_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    public static final String COL_IS_FAVORITE = "IS_FAVORITE";

    public static final String[] HOODLY_COLUMNS = {COL_ID, COL_ITEM_NAME, COL_ITEM_ADDRESS, COL_ITEM_DESCRIPTION, COL_IS_FAVORITE};
    // Define sql statements to create and delete games table
    public static final String SQL_CREATE_HOOD_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_ITEM_NAME + " TEXT, " +
                    COL_ITEM_ADDRESS + " TEXT, " +
                    COL_ITEM_DESCRIPTION + " TEXT, " +
                    COL_IS_FAVORITE + " INTEGER )";

    //KEY AUTOINCREMENT
    public static final String SQL_DROP_HOOD_TABLE = "DROP TABLE IF EXISTS games";


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
//03 Singleton -- private constructor 
    public static DatabaseHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    // Create the games table when the database is created
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HOOD_TABLE);
        insertHood(db,1, "Big Building", "123 Street", "Building", 0);
        insertHood(db, 2, "Medium Building", "456 Street", "Building", 0);
        insertHood(db, 3, "Large Building", "789 Street", "Building", 0);
        insertHood(db, 4, "Small Park", "123 Avenue", "Park", 0);
        insertHood(db, 5, "Medium Park", "456 Avenue", "Park", 0);
        insertHood(db, 6, "Large Park", "789 Avenue", "Park", 0);
    }

    // When the database is upgraded, the old data isn't needed. Delete the games
    // table and recreate the table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_HOOD_TABLE);
        onCreate(db);
    }

    public void insertHood(SQLiteDatabase db, int id, String name, String address, String description, int favorite){
        // Get a reference to the database
        //SQLiteDatabase db = getWritableDatabase();

        // create a new content value to store values
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_ITEM_NAME, name);
        values.put(COL_ITEM_ADDRESS, address);
        values.put(COL_ITEM_DESCRIPTION, description);
        values.put(COL_IS_FAVORITE, favorite);


        // Insert the row into the games table
        db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<String> getEntityAspectsByName(String name) {
        ArrayList<String> entityAspects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_ITEM_NAME, COL_ITEM_DESCRIPTION},
                COL_ITEM_NAME + " = ?",
                new String[]{name},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            String resultName = cursor.getString(0);
            entityAspects.add(resultName);
            String resultDescription = cursor.getString(1);
            entityAspects.add(resultDescription);
        } else {
            entityAspects.add("Data not found");
        }
        cursor.close();
        return entityAspects;
    }

    public String getDescriptionByName(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COL_ITEM_NAME+" = ?",
                new String[] {name},
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            //String resultVal = cursor.getString(cursor.getColumnIndex(COL_ID));
            String resultVal = cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME));
            cursor.close();
            return resultVal;
            //return cursor.getString()
        } else {
            return "No Description Found";
        }
    }
//    public Hood getHood(int id){
//        // Get a reference to the database
//        SQLiteDatabase db = getReadableDatabase();
//
//        // Define a projection, which tells the query to return only the columns mentioned
//        // similar to "SELECT column1, column2, column3"
//        String[] projection = new String[]{ "id", "name", "year" };
//
//        // Define a selection, which defines the WHERE clause of the query (but not the values for it)
//        // similar to "WHERE x < 23", only without the value; "WHERE x < ?"
//        String selection = "id = ?";
//
//        // Define the selection values. The ?'s in the selection
//        // The number of values in the following array should equal the number of ? in the where clause
//        String[] selectionArgs = new String[]{ String.valueOf(id) };
//
//        // Make the query, getting the cursor object
//        Cursor cursor = db.query("games", projection, selection, selectionArgs, null, null, null, null);
//
//        // With the cursor, create a new game object and return it
//        cursor.moveToFirst();
//
//        String name = cursor.getString( cursor.getColumnIndex("name") );
//        String year = cursor.getString( cursor.getColumnIndex("year") );
//
//        return new Hood(id, name, year);
//    }

    public void delete(int id){
        // Get a reference to the database
        SQLiteDatabase db = getWritableDatabase();

        // Define the selection, or the where
        String selection = "id = ?";

        // Define the selection values. The ?'s in the selection
        // The number of values in the following array should equal the number of ? in the where clause
        String[] selectionArgs = new String[]{ String.valueOf(id) };

        // Delete everything that satisfies the selection
        db.delete("Hoods", selection, selectionArgs);
    }


}
