package com.example.myapplication.wordtest;

public class Word {
     String value;
     String translation;
    public Word() {
        value=null;
        translation=null;
    }
    public Word(String value,String translation) {
        this.value = value;
        this.translation=translation;
    }

    public String getValue() {
        return value;
    }
    public String getTranslation(){
        return translation;
    }

}
