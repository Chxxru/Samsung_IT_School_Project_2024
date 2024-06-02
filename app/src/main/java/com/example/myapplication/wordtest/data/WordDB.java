package com.example.myapplication.wordtest.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class WordDB {
    private SQLiteDatabase mDataBase;

    private static final String DATABASE_NAME = "words.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Words";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ENG = "English";
    private static final String COLUMN_RUS = "Translation";
    private static final String COLUMN_EXAMPLE = "Example";
    private static final String COLUMN_LEARNED ="LastLearn";
    private static final String COLUMN_PERIODICITY ="Periodicity";
    private static final String COLUMN_STAGE = "Stage";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_ENG = 1;
    private static final int NUM_COLUMN_RUS = 2;
    private static final int NUM_COLUMN_EXAMPLE = 3;
    private static final int NUM_COLUMN_LEARNED =4;
    private static final int NUM_COLUMN_PERIODICITY = 5;

    private static final int NUM_COLUMN_STAGE = 6;

    public WordDB(Context context){
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public void insert(String eng,String rus, String example, long learnedTime, int periodicity, boolean stage, long id) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ENG, eng);
        cv.put(COLUMN_RUS, rus);
        cv.put(COLUMN_EXAMPLE, example);
        cv.put(COLUMN_LEARNED, learnedTime);
        cv.put(COLUMN_PERIODICITY, periodicity);
        cv.put(COLUMN_STAGE, stage?1:0);
        cv.put(COLUMN_ID,id);
        mDataBase.insert(TABLE_NAME, null, cv);
    }
    public long insert(String eng,String rus, String example, long learnedTime) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ENG, eng);
        cv.put(COLUMN_RUS, rus);
        cv.put(COLUMN_EXAMPLE, example);
        cv.put(COLUMN_LEARNED, learnedTime);
        cv.put(COLUMN_PERIODICITY, 0);
        cv.put(COLUMN_STAGE, 0);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }
    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }
    public Word select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null,
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);

        mCursor.moveToFirst();
        String value = mCursor.getString(NUM_COLUMN_ENG);
        String translation = mCursor.getString(NUM_COLUMN_RUS);
        String example = mCursor.getString(NUM_COLUMN_EXAMPLE);
        long lastLearn =mCursor.getLong(NUM_COLUMN_LEARNED);
        boolean isStudied = mCursor.getInt(NUM_COLUMN_STAGE)==1;
        int periodicity = mCursor.getInt(NUM_COLUMN_PERIODICITY);
        mCursor.close();
        return new Word(value,translation,example,lastLearn,periodicity,isStudied,id);
    }
    public ArrayList<Word> selectToTest(long currentTime){
        String a =String.valueOf(currentTime);
        String query = "SELECT * FROM Words WHERE LastLearn+(Periodicity*86400000)<="+a;

        Cursor mCursor = mDataBase.rawQuery(query, null);

        ArrayList<Word> arr = new ArrayList<Word>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long learned = mCursor.getLong(NUM_COLUMN_LEARNED);
                int period = mCursor.getInt(NUM_COLUMN_PERIODICITY);
                long o = learned+period*86400;


                    long id = mCursor.getLong(NUM_COLUMN_ID);
                    String value = mCursor.getString(NUM_COLUMN_ENG);
                    String translation = mCursor.getString(NUM_COLUMN_RUS);
                    String example = mCursor.getString(NUM_COLUMN_EXAMPLE);
                    long lastLearn = mCursor.getLong(NUM_COLUMN_LEARNED);
                    boolean isStudied = mCursor.getInt(NUM_COLUMN_STAGE) == 1;
                    int periodicity = mCursor.getInt(NUM_COLUMN_PERIODICITY);
                    arr.add(new Word(value, translation, example, lastLearn, periodicity, isStudied, id));
            } while (mCursor.moveToNext());
        }
        mCursor.close();

        return arr;
    }
    public int update(Word wd) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ENG, wd.getValue());
        cv.put(COLUMN_RUS, wd.getTranslation());
        cv.put(COLUMN_EXAMPLE, wd.getExample());
        cv.put(COLUMN_LEARNED, wd.getLastLearn());
        cv.put(COLUMN_PERIODICITY, wd.getPeriodicity());
        cv.put(COLUMN_STAGE, wd.isStudied() ? 1 : 0);
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(wd.getId())});
    }
    public int getWordCount(boolean isBad){
        Cursor mCursor = mDataBase.query(TABLE_NAME, new String[]{"count(*)"}, COLUMN_STAGE+"=?",
                new String[]{isBad?"0":"1"} ,
                null, null, null);
        mCursor.moveToFirst();
        int count = mCursor.getInt(0);
        mCursor.close();
        return count;

    }
    public ArrayList<Word> selectAll(){
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Word> arr = new ArrayList<Word>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String value = mCursor.getString(NUM_COLUMN_ENG);
                String translation = mCursor.getString(NUM_COLUMN_RUS);
                String example = mCursor.getString(NUM_COLUMN_EXAMPLE);
                long lastLearn =mCursor.getLong(NUM_COLUMN_LEARNED);
                boolean isStudied = mCursor.getInt(NUM_COLUMN_STAGE)==1;
                int periodicity = mCursor.getInt(NUM_COLUMN_PERIODICITY);
                arr.add(new Word(value,translation,example,lastLearn,periodicity,isStudied,id));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
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
                    COLUMN_LEARNED + " INT, "+
                    COLUMN_PERIODICITY + " INT, " +
                    COLUMN_STAGE + " INT);";
            db.execSQL(query);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
