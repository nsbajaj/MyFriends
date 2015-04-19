package com.example.nitish.myfriends;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyDBHandler extends SQLiteOpenHelper {
	
    //The Android's default system path of your application database.
    private static final int DATABASE_VERSION = 1; //database version
    private static final String DATABASE_NAME = "students.db"; //database name
    public static final String STUDENTS = "students"; //table name
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_NAME = "studentname";
    public static final String STUDENT_EMAIL = "studentemail";
    private static final String STUDENT_PHONENUMBER = "phonenumber";
    public static final String[] ALL_KEYS = new String[] {STUDENT_ID, STUDENT_NAME, STUDENT_EMAIL, STUDENT_PHONENUMBER};

    private final Context myContext;
    String DB_PATH = null;
    private SQLiteDatabase myDataBase;

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.myContext = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        copyDataBase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        /*String CREATE_STUDENTS_TABLE = "CREATE TABLE " + STUDENTS + "(" + STUDENT_ID + " INTEGER PRIMARY KEY," + STUDENT_NAME + " TEXT," + STUDENT_EMAIL + " TEXT," + STUDENT_PHONENUMBER + " INTEGER" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS);
        onCreate(db);
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = 	db.query(STUDENTS, ALL_KEYS,where,null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    private void createDBFolder() {
        File file = new File(DB_PATH);
        file.mkdir();
    }

    private void copyDataBase(){
        //Open your local db as the input stream
        InputStream myInput = null;
        try {
            myInput = myContext.getAssets().open(DATABASE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creates databases folder if it does not exist
        createDBFolder();

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = null;
        try {
            myOutput = new FileOutputStream(outFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Close the streams
        try {
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
