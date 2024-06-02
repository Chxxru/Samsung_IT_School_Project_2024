package com.example.myapplication.wordtest.domain;

import com.example.myapplication.wordtest.data.Word;

import java.util.ArrayList;

public interface WordRepository {
    ArrayList<Word> getAllWords();
    Word getWordById(long id);
    void deleteAllWords();
    void deleteWordById(long id);
    void addWord(Word word);
    int getGoodWordCount();
    int getBadWordCount();
    ArrayList<Word> getWordsToTest(long time);
    int update(Word word);
    public boolean isStudied(Word w);
    void addList(ArrayList<Word> a);
    ArrayList<Word> getSystemWordsByGrade(int grade, long lastLearned);
    void systemWordIsShown(long id);
    public void copyWord(Word w);



}
