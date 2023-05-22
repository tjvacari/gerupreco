package com.vacari.gerupreco.database;

import android.content.Context;

public class DatabaseManager {
    private static DatabaseHelper dbHelper;

    private static synchronized DatabaseHelper getDB(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context.getApplicationContext());
        }
        return dbHelper;
    }
}
