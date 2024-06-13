package com.example.notetakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static final String DBName = "NotesApp";
    private static final int DBversion = 1;

    private static final String CreateNotesTBL = "CREATE TABLE Notes (Note_id INTEGER PRIMARY KEY AUTOINCREMENT, Notes_Title VARCHAR(50), Notes_Content VARCHAR(255))";
    private static final String CreateUsersTBL = "CREATE TABLE Users (User_id INTEGER PRIMARY KEY, UserName VARCHAR(50), Password VARCHAR(30))";

    private static final String DropUsersTBL = "DROP TABLE IF EXISTS Users";
    private static final String DropNotesTBL = "DROP TABLE IF EXISTS Notes";

    private static Database instance;
    private String title = "";
    private String content = "";

    public static Database getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Database(context);
    }

    public Database(@Nullable Context context) {
        super(context, DBName, null, DBversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateUsersTBL);
        db.execSQL(CreateNotesTBL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DropUsersTBL);
        db.execSQL(DropNotesTBL);

        onCreate(db);
    }

    public void AddTitle(String title) {
        this.title = title;
    }

    public void AddContents(String content) {
        this.content = content;
    }

    public boolean build() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Notes_Title", title);
        cv.put("Notes_Content", content);

        long result = db.insert("Notes", null, cv);

        content = "";
        title = "";
        return result != -1;
    }

    public boolean update(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Notes_Title", title);
        cv.put("Notes_Content", content);

        long result = db.update("Notes", cv, "Note_id=?", new String[] {id+""});

        content = "";
        title = "";
        return result != -1;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Notes", "Note_id=?", new String[] {id+""});

        content = "";
        title = "";
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Notes", null);
        return res;
    }
    public Cursor getID(int id) {
        Log.d("Output", id+"");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(
                "SELECT Notes_Title, Notes_Content FROM Notes WHERE Note_id = " + id,
                null);
        return res;
    }
}