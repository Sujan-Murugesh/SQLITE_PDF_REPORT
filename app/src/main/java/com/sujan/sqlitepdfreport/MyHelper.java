package com.sujan.sqlitepdfreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(@Nullable Context context) {
        super(context, "MyDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable ="create table myTable(tripNo INTEGER PRIMARY KEY AUTOINCREMENT,customername TEXT,"+
                "contactno TEXT, date INTEGER, item1 TEXT, qty1 INTEGER, amount1 INTEGER, item2 TEXT,qty2 INTEGER,amount2 INTEGER);";

        db.execSQL(createtable);
    }

    public  void insert(String name,String contact,Long date,String item1,int qty1,int amount1,String item2,int qty2,int amount2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("customername",name);
        contentValues.put("contactno",contact);
        contentValues.put("date",date);
        contentValues.put("item1",item1);
        contentValues.put("qty1",qty1);
        contentValues.put("amount1",amount1);
        contentValues.put("item2",item2);
        contentValues.put("qty2",qty2);
        contentValues.put("amount2",amount2);

        db.insert("myTable",null,contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
