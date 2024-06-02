package com.example.myapplication.wordtest.domain;

import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.wordtest.data.MyDictionary;
import com.example.myapplication.wordtest.data.SystemWordsBd;
import com.example.myapplication.wordtest.data.Word;
import com.example.myapplication.wordtest.data.WordDB;
import com.example.myapplication.wordtest.ui.LearnWordsActivity;

import java.util.ArrayList;

public class TestManager implements WordRepository  {
    private final WordDB database;
    private final SystemWordsBd systemDtabase;


    @Override
    public ArrayList<Word> getAllWords() {
        return database.selectAll();
    }

    @Override
    public Word getWordById(long id) {
        Word n = database.select(id);
        return database.select(id);
    }

    @Override
    public void deleteAllWords() {
        database.deleteAll();
    }

    @Override
    public void deleteWordById(long id) {
        database.delete(id);

    }

    @Override
    public void addWord(Word wd) {
        String value = wd.getValue();
        String translation = wd.getTranslation();
        String example = wd.getExample();
        long lastLearn =wd.getLastLearn();
        boolean isStudied = wd.isStudied();
        int periodicity = wd.getPeriodicity();
        database.insert(value,translation,example,lastLearn);
    }
    @Override
    public void copyWord(Word wd) {
        String value = wd.getValue();
        String translation = wd.getTranslation();
        String example = wd.getExample();
        long lastLearn =wd.getLastLearn();
        boolean isStudied = wd.isStudied();
        int periodicity = wd.getPeriodicity();
        long id = wd.getId();
        database.insert(value,translation,example,lastLearn,periodicity,isStudied,id);
    }
    @Override
    public void addList(ArrayList<Word> arr){

        for (Word wd: arr){
            copyWord(wd);
        }
    }

    @Override
    public int getGoodWordCount() {

        return database.getWordCount(false);
    }

    @Override
    public int getBadWordCount() {
        return database.getWordCount(true);
    }

    @Override
    public ArrayList<Word> getWordsToTest(long time) {
        return database.selectToTest(time);
    }
    @Override
    public int update(Word word){
        return database.update(word);
    }
    @Override
    public boolean isStudied(Word w){
        w.setStudied(w.getPeriodicity()>=30);
        database.update(w);
        return w.isStudied();
    }
    @Override
    public ArrayList<Word>  getSystemWordsByGrade(int grade, long lastLearned){
        if (!systemDtabase.systemDataIsNotEmpty()){
            Thread t1 = new Thread(){
                @Override
                public void run() {
                    synchronized (this){
                        systemDtabase.insData();
                    }
                }
            };
            try{
                t1.start();
                t1.join();
            }catch (InterruptedException e1){
                return null;
            }
        }
        return systemDtabase.select(grade,lastLearned);
    }
    @Override
    public void systemWordIsShown(long id){

        systemDtabase.shown(id);
    }



    public TestManager(Context ctx){
        this.database=new WordDB(ctx);
        this.systemDtabase = new SystemWordsBd(ctx);
    }
}
