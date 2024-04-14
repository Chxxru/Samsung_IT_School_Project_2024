package com.example.myapplication.wordtest;

import android.widget.TextView;

import java.util.ArrayList;

public class DatabaseWd {

    static ArrayList<Word> wordsDataBase;

    /*private static final String DATABASE_NAME = "words.db";
    private static final int DATABASE_VERSION = 1;

    private static final String WORDS_TABLE_NAME = "Words";
    private static final String WORD_ID_COLUMN_NAME = "id";
    private static final String WORD_COLUMN_NAME = "word";
    private static final String WORD_TRANSLATION_COLUMN_NAME = "translation";*/
    public DatabaseWd(){
        wordsDataBase=makeArr();
    }

    public static ArrayList<Word> getWordsDataBase() {

        return wordsDataBase;
    }
    public static void delete(int position){
        try {
            if (position < wordsDataBase.size()) {
                wordsDataBase.remove(position);
            }
        }catch (NullPointerException e){

        }
    }
    public static int amount(){
        return wordsDataBase.size();
    }


    //тест лист
    private static ArrayList<Word> makeArr(){
        ArrayList<Word> arr=new ArrayList<>(5);
        String[] values={"sun","goal","primary","wipe","sure"};
        String[] translations ={"солнце","цель","первостепенный","вытирать","конечно"};
        for (int i = 0; i < arr.size(); i++) {
            arr.set(i,new Word((String) values[i],(String) translations[i]));
        }
        return arr;
    }


}
