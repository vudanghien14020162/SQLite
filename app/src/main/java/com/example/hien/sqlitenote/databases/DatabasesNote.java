package com.example.hien.sqlitenote.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hien.sqlitenote.commom.CONST;

/**
 * Created by hien on 8/8/17.
 */

public class DatabasesNote extends SQLiteOpenHelper {

    public DatabasesNote(Context context) {
        super(context, CONST.DATABASE, null, CONST.version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //tao bang
        String sql = "CREATE TABLE NOTE(id INTEGER primary key not null, name TEXT not null)";

        sqLiteDatabase.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //xoa bo bang o version cu
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS");

        //tao ra bang moi
        onCreate(sqLiteDatabase);
    }
}
