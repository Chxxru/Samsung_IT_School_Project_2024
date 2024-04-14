package com.example.myapplication.wordtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LearnWordsActivity extends AppCompatActivity {


    ArrayList<Word> data =new ArrayList<Word>();
    WordAdapter adapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




         final Button learnWordsBtn = (Button) findViewById(R.id.learnint_words_btn);
         final ImageButton addWordBtn = (ImageButton) findViewById(R.id.add_word_btn);
         final ImageButton addCustomWordBtn=(ImageButton) findViewById(R.id.add_custom_word_btn);
         final TextView amountWordsTv=(TextView) findViewById(R.id.amount_words_Tv);
         final ListView wordsListLV=(ListView) findViewById(R.id.data_list);

        fillData();
        adapter =new WordAdapter(this,data);
        wordsListLV.setAdapter(adapter);

        learnWordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LearnWordsActivity.this, WordTestActivity.class);
                startActivity(i);
            }
        });
        addCustomWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LearnWordsActivity.this, AddCustomsActivity.class);
                startActivity(i);
            }
        });
        addWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LearnWordsActivity.this,AddSystemActivity.class);
                startActivity(i);
            }
        });



        /*wordsListLV.setOnTouchListener(new OnSwipeTouchListener(LearnWordsActivity.this){
            public void onSwipeRight() {

            }
        });*/

    }
    void fillData() {
        for (int i = 1; i <= 5; i++) {
            switch (i){
                case 1: data.add(new Word("cow" , "корова"));
                case 2: data.add(new Word("sleep in" , "проспать"));
                case 3: data.add(new Word("to manage" , "руководить"));
                case 4: data.add(new Word("change" , "менять"));
                case 5: data.add(new Word("maintain" , "поддерживать"));
            }
            data.add(new Word("Product " + i, ""+i));
        }
    }

    /*public void wordsAmountUpdate(){
        amountWordsTv.setText(""+DatabaseWd.amount());
    }

    //заполнение тест. массива слов
    /*private void makeArr(){
        arr=new ArrayList<>(5);
        String[] values={"sun","goal","primary","wipe","sure"};
        String[] translations ={"солнце","цель","первостепенный","вытирать","конечно"};
        for (int i = 0; i < arr.size(); i++) {
            arr.set(i,new Word(values[i],translations[i]));
        }
    }*/
}
