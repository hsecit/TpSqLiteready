package org.devmobile.tpsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_ID = "_id";
    public static final String TABLE_STUDENT = "Student";
    public static final String COLUMN_NOM = "Nom";
    public static final String COLUMN_TEL = "Tel";

    private static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
            COLUMN_NOM + " TEXT NOT NULL, " +
            COLUMN_TEL + " TEXT NOT NULL " +
            ");";

    public MySqLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static String[] getTableName(){
        return new String[]{COLUMN_ID, COLUMN_NOM, COLUMN_TEL};
    }
}
