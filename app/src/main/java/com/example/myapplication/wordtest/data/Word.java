package com.example.myapplication.wordtest.data;

import java.io.Serializable;

public class Word implements Serializable {
     private String value;
     private String translation;
    private String example;

    private long lastLearn;

    private boolean isStudied;
    private long id;

    private int periodicity;


    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }


    public void setLastLearn(long lastLearn) {
        this.lastLearn = lastLearn;
    }

    public void setStudied(boolean studied) {
        isStudied = studied;
    }




    public boolean isStudied(){
        return isStudied;
    }
    public void setStudiedState(boolean state){
        this.isStudied=state;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
    public long getLastLearn() {return lastLearn;}

    public int getPeriodicity() {return periodicity;}


    public long getId() {
        return id;
    }



    public void setValue(String value) {
        this.value = value;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Word() {
        value=null;
        translation=null;

        example=null;

        lastLearn=-1;

        isStudied=false;
        id=-1;

        periodicity=-1;
    }
    public Word(String value,String translation,String example,long lastLearn) {
        this.value = value;
        this.translation=translation;
        this.example=example;
        this.lastLearn=lastLearn;
        periodicity=0;
        this.isStudied = false;
    }
    public Word(String value,String translation,String example,long lastLearn,long id) {
        this.value = value;
        this.translation=translation;
        this.example=example;
        this.lastLearn=lastLearn;
        periodicity=0;
        this.id=id;
        this.isStudied = false;
    }

    public Word(String value, String translation, String example, long lastLearn, int periodicity, boolean isStudied, long id) {
        this.value = value;
        this.translation = translation;
        this.example = example;
        this.lastLearn = lastLearn;
        this.periodicity = periodicity;
        this.isStudied = isStudied;
        this.id = id;
    }

    public String getValue() {
        return value;
    }
    public String getTranslation(){
        return translation;
    }

}
