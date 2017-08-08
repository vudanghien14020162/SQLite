package com.example.hien.sqlitenote.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hien.sqlitenote.commom.CONST;
import com.example.hien.sqlitenote.databases.DatabasesNote;
import com.example.hien.sqlitenote.module.ItemNote;

/**
 * Created by hien on 8/8/17.
 */

public class ManagerNote {

    private DatabasesNote mDatabasesNote;

    private SQLiteDatabase mDatabases;

    public ManagerNote(Context context) {
        mDatabasesNote = new DatabasesNote(context);

    }

    //open databases
    public void openDatabase(){
        if(mDatabasesNote != null){
            mDatabases = mDatabasesNote.getReadableDatabase();
        }
    }

    //close databses
    public void closeDatabases(){
        if(mDatabases != null && mDatabases.isOpen()){
            mDatabases.close();
        }
    }

    public boolean insertNote(ItemNote note){

        ContentValues values = new ContentValues();

        values.put(CONST.NAME_NOTE, note.getNameNote().toString());

        long insert = mDatabases.insert(CONST.TABLE_NAME, null, values);

        if(insert != 0){
            return true;
        }

        return false;
    }

    //hien thi data co trong sql
    public Cursor showNote(String sql){

        openDatabase();

        Cursor cursor = mDatabases.rawQuery(sql, null);

        return cursor;

    }

    public void editNote(String sql){

        openDatabase();

        mDatabases.execSQL(sql);
    }

    public void deleteDatabases(String sql){
        openDatabase();

        mDatabases.execSQL(sql);
    }



}
