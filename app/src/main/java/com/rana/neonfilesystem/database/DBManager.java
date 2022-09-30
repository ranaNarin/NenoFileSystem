package com.rana.neonfilesystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rana.neonfilesystem.models.ElementModel;

public class DBManager {
    private static final String TAG=DBManager.class.getSimpleName();
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        Log.e(TAG, ".......open");
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(ElementModel model) {
        Log.e(TAG, ".......insert");
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ELEMENT, model.getElement());
        contentValue.put(DatabaseHelper.PARENT_ID, model.getParent_id());
        contentValue.put(DatabaseHelper.IS_DIR, model.getIs_dir());
//        contentValue.put(DatabaseHelper.CREATE_TIME, desc);
//        contentValue.put(DatabaseHelper.MODIFIED_TIME, desc);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.ELEMENT, DatabaseHelper.PARENT_ID };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int getParentByElement(String element){
        String query = "SELECT element, _id FROM "+DatabaseHelper.TABLE_NAME +" WHERE element LIKE \"%" + element + "%\"";
        Cursor cursorC = database.rawQuery(query, null);
        while (cursorC.moveToNext()) {
            //System.out.println(cursorC.getString(0) + " : " + cursorC.getString(1));
            return cursorC.getInt(1);
        }
       return 0;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ELEMENT, name);
        contentValues.put(DatabaseHelper.PARENT_ID, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
}
