package com.example.myapplication.wordtest.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SystemWordsBd {
    private SQLiteDatabase sDataBase;
    private static final String DATABASE_NAME = "cards.db";
    private static final int DATABASE_VERSION = 5;
    private static final String TABLE_NAME = "Card";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ENG = "English";
    private static final String COLUMN_RUS = "Translation";
    private static final String COLUMN_EXAMPLE = "Example";
    private static final String COLUMN_GRADE = "Grade";
    private static final String COLUMN_SHOWN = "Shown";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_ENG = 1;
    private static final int NUM_COLUMN_RUS = 2;
    private static final int NUM_COLUMN_EXAMPLE = 3;
    private static final int NUM_COLUMN_GRADE =4;
    private static final int NUM_COLUMN_SHOWN =5;


    public ArrayList<Word> select(int grade,long lastlearn) {
        String query = String.format("SELECT * FROM %s WHERE %s=%s AND %s=0", TABLE_NAME,COLUMN_GRADE,
                String.valueOf(grade),COLUMN_SHOWN);
        Cursor mCursor = sDataBase.rawQuery(query,null);
        ArrayList<Word> arr = new ArrayList<Word>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String value = mCursor.getString(NUM_COLUMN_ENG);
                String translation = mCursor.getString(NUM_COLUMN_RUS);
                String example = mCursor.getString(NUM_COLUMN_EXAMPLE);
                arr.add(new Word(value,translation,example,lastlearn,id));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }
    public boolean systemDataIsNotEmpty(){
        String query = String.format("SELECT * FROM %s ", TABLE_NAME);
        Cursor mCursor = sDataBase.rawQuery(query,null);
        boolean notEmpty = mCursor.moveToFirst();
        mCursor.close();
        return notEmpty;
    }
    public long insert(int grade, String value, String translation, String example){
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ENG, value);
        cv.put(COLUMN_RUS, translation);
        cv.put(COLUMN_EXAMPLE, example);
        cv.put(COLUMN_GRADE, grade);
        cv.put(COLUMN_SHOWN,0);
        return sDataBase.insert(TABLE_NAME, null, cv);
    }
    public void deleteAll() {
        sDataBase.delete(TABLE_NAME, null, null);
    }
    public void insData(){
        int s = MyDictionary.DICTIONARY.length;
        for(int i =0;i<s;i++){
            int grade = Integer.parseInt(MyDictionary.DICTIONARY[i][0]);
            String value = MyDictionary.DICTIONARY[i][1];
            String translation = MyDictionary.DICTIONARY[i][2];
            String example = MyDictionary.DICTIONARY[i][3];
            insert(grade,value,translation,example);
        }
    }

    public void shown(long id){
        String query = String.format("UPDATE %s SET %s=1 WHERE %s = %s", TABLE_NAME, COLUMN_SHOWN, COLUMN_ID, id );
        sDataBase.execSQL(query);
    }






    public SystemWordsBd(Context context){
        SystemWordsBd.OpenHelper mOpenHelper = new SystemWordsBd.OpenHelper(context);
        sDataBase = mOpenHelper.getWritableDatabase();
    }
    private class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ENG + " TEXT, " +
                    COLUMN_RUS + " TEXT, " +
                    COLUMN_EXAMPLE + " TEXT, "+
                    COLUMN_GRADE + " INT, "+
                    COLUMN_SHOWN + " INT);";
            db.execSQL(query);
        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
