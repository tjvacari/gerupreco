package com.vacari.gerupreco.database;

import android.content.Context;

public class DatabaseManager {
    private static DatabaseHelper dbHelper;

    public static void initDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
        // force call onCreate
        dbHelper.getWritableDatabase();
    }

    public static synchronized DatabaseHelper getDB(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context.getApplicationContext());
        }
        return dbHelper;
    }
}
