package com.rana.neonfilesystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG=DatabaseHelper.class.getSimpleName();
    // Table Name
    public static final String TABLE_NAME = "fileSystem";

    // Table columns
    public static final String _ID = "_id";
    public static final String ELEMENT = "element";
    public static final String PARENT_ID = "parent_id";
    public static final String IS_DIR = "is_dir";
    public static final String CREATE_TIME = "create_time";
    public static final String MODIFIED_TIME = "modified_time";

    // Database Information
    static final String DB_NAME = "NEON_FILE_SYSTEM.DB";

    // database version
    static final int DB_VERSION = 1;


    /*
    * - Creating table query,
    * - both directories and files are saved in this table.
    * - if a directory or a file has a parent, it's parent's _id is saved to parent_id.
    * - if the entry is a directory the is_dir field is set to 1, else 0.
    */
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ELEMENT + " TEXT NOT NULL, " +
            IS_DIR + " INTEGER DEFAULT 0, " +
            PARENT_ID + " INTEGER DEFAULT 0,"+
            CREATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP ,"+
            MODIFIED_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"+
            "CONSTRAINT fk_parent FOREIGN KEY ( "+PARENT_ID+" ) REFERENCES entry("+ _ID+" ));";


//    CREATE TABLE entry (
//            _id INTEGER PRIMARY KEY,
//            name TEXT,
//            parent_id INTEGER DEFAULT 0,
//            is_dir INTEGER DEFAULT 0,
//            CONSTRAINT fk_parent FOREIGN KEY ( parent_id ) REFERENCES entry( _id )
//            );

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "OnCreate..........");
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}