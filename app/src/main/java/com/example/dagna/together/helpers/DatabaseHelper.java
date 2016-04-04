package com.example.dagna.together.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gapsa on 04.04.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "together";

    // Table Names
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_USER = "user";
    private static final String TABLE_EVENT = "event";
    private static final String TABLE_USER_EVENT = "user_event";


    private static final String KEY_CITY = "city";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_EVENT_ID = "event_id";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_DSCRP = "description";

    // CATEGORY colun names
    private static final String KEY_CATEGORY_NAME = "name";

    // USER Table - column nmaes
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PWD = "password";
    private static final String KEY_GRADE = "grade";

    // EVENT Table - column names
    private static final String KEY_EVENT_NAME = "name";
    private static final String KEY_STREET_NAME = "street_name";
    private static final String KEY_STREET_NUMBER = "street_number";
    private static final String KEY_ZIPCODE = "zipcode";
    private static final String KEY_COUNTRY = "country";


    // USER_EVENTS Table - column names
    private static final String USER_EVENT_KEY_ID = "user_event_id";


    // Table Create Statements
    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "
            + TABLE_CATEGORY + "(" + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY_NAME
            + " TEXT" + ")";

    // User table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER
            + "(" + KEY_USER_ID + " INTEGER PRIMARY KEY," + KEY_LOGIN + " TEXT,"
            + KEY_PWD + " TEXT," + KEY_GRADE + " INTEGER" + ")";

    // Event table create statement
    private static final String CREATE_TABLE_EVENT = "CREATE TABLE "
            + TABLE_EVENT + "(" + KEY_EVENT_ID + " INTEGER PRIMARY KEY,"
            + KEY_EVENT_NAME + " TEXT," + KEY_DSCRP + " TEXT,"
            + KEY_CATEGORY_ID + " INTEGER, " + KEY_USER_ID + " INTEGER,"
            + KEY_STREET_NAME + " TEXT," + KEY_STREET_NUMBER + " INTEGER," + KEY_CITY + " TEXT,"
            + KEY_ZIPCODE + " TEXT," + KEY_COUNTRY + " TEXT" + ")";

    // User_event table create statement
    private static final String CREATE_TABLE_USER_EVENT = "CREATE TABLE " + TABLE_USER_EVENT
            + "(" + USER_EVENT_KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID + " INTEGER,"
            + KEY_EVENT_ID + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_USER_EVENT);
        //createCategories();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_EVENT);

        // create new tables
        onCreate(db);
    }

    /*
 * Creating categories
 */
    public long createCategories() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_NAME, "Drinking");

        // insert row
        long category_id = db.insert(TABLE_CATEGORY, null, values);

        return category_id;
    }

    /*
 * get single todo
 */
    public String getCategory(long category_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY + " WHERE "
                + KEY_CATEGORY_ID + " = " + category_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

//        Todo td = new Todo();
//        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
//        td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
//        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return c.getString(c.getColumnIndex(KEY_CATEGORY_NAME));
    }
}
