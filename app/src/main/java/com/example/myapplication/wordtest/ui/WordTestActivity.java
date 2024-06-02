package com.example.myapplication.wordtest.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.wordtest.data.Word;
import com.example.myapplication.wordtest.domain.TestManager;

import java.util.ArrayList;
import java.util.Collections;

public class WordTestActivity extends AppCompatActivity {

    private TestManager manager;
    private ArrayList<Word> testData;
    private Context ctx;
    private int currentWordIndex=0;
    private int trnslteStage=0;
    private int testStage=0;

    Button badKnowBtn;
    Button dontKnowBtn;
    Button middleKnowBtn;
    Button knowBtn;
    TextView angWord;
    TextView example;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_test);

        badKnowBtn=(Button) findViewById(R.id.answ1_btn);
        dontKnowBtn=(Button) findViewById(R.id.answ2_btn);
        middleKnowBtn=(Button) findViewById(R.id.answ3_btn);
         knowBtn=(Button) findViewById(R.id.answ4_btn);
         angWord = (TextView)findViewById(R.id.task_text);
         example = (TextView)findViewById(R.id.select_translation_tv);

        ctx=this;
        manager=new TestManager(ctx);

        testData=manager.getWordsToTest(System.currentTimeMillis());
        angWord.setText(testData.get(currentWordIndex).getValue());
        example.setText(testData.get(currentWordIndex).getExample());
        angWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (trnslteStage){
                    case 0:
                        angWord.setText(testData.get(currentWordIndex).getTranslation());
                        trnslteStage=1;
                        break;
                    case 1:
                        angWord.setText(testData.get(currentWordIndex).getValue());
                        trnslteStage=0;
                }

            }
        });
        //не знаю
        dontKnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (testStage) {
                    case 0:
                        if (testData.get(currentWordIndex).getPeriodicity() != 0) {
                            testData.get(currentWordIndex).setPeriodicity(1);
                            manager.update(testData.get(currentWordIndex));
                        }
                        if(testData.size()-1>currentWordIndex){
                            currentWordIndex++;
                            up();
                        }else if(testData.size()>0){
                            Collections.shuffle(testData);
                            currentWordIndex=0;
                            testStage=1;
                            up();
                        }
                        break;
                    case 1:
                        if(testData.size()-1>currentWordIndex){
                            currentWordIndex++;
                            up();
                        }else if(testData.size()>0){
                            Collections.shuffle(testData);
                            currentWordIndex=0;
                            up();
                        }

                }
            }
        });
        //плохо помню

        badKnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int per =testData.get(currentWordIndex).getPeriodicity();
                switch (testStage) {
                    case 0:
                        if(per!=1){
                            testData.get(currentWordIndex).setPeriodicity(per/2);
                            manager.update(testData.get(currentWordIndex));
                        }
                        int size=testData.size();
                        boolean a = size-1>currentWordIndex;
                        if(a){
                            currentWordIndex++;
                            up();
                        }else if(testData.size()>0){
                            Collections.shuffle(testData);
                            currentWordIndex=0;
                            testStage=1;
                            up();
                        }
                        break;
                    case 1:
                        if(testData.size()-1>currentWordIndex){
                            currentWordIndex++;
                            up();
                        }else if(testData.size()>0){
                            Collections.shuffle(testData);
                            currentWordIndex=0;
                            up();
                        }
                }

            }
        });
        middleKnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Word w = testData.get(currentWordIndex);
                w.setLastLearn(System.currentTimeMillis());
                if(w.getPeriodicity()==0){
                    w.setPeriodicity(1);
                }
                manager.update(w);

                testData.remove(currentWordIndex);
                int k=testData.size()-1;
                if(currentWordIndex<k){
                    currentWordIndex++;
                    up();
                }else if(testData.size()>0){
                    Collections.shuffle(testData);
                    currentWordIndex=0;
                    up();
                    testStage=1;

                }else {
                    Intent i = new Intent(ctx,LearnWordsActivity.class);
                    startActivity(i);
                }
            }
        });
        knowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int per =testData.get(currentWordIndex).getPeriodicity();

                Word w = testData.get(currentWordIndex);
                w.setLastLearn(System.currentTimeMillis());
                manager.isStudied(w);
                if(testStage==0) {
                    w.setPeriodicity(per * 2 + 1);
                }
                manager.update(w);
                testData.remove(currentWordIndex);
                int k=testData.size()-1;

                if(currentWordIndex<k){
                    up();
                }else if(testData.size()>0){
                    Collections.shuffle(testData);
                    currentWordIndex=0;
                    up();
                    testStage=1;
                }else {
                    Intent i = new Intent(ctx,LearnWordsActivity.class);
                    startActivity(i);
                }
            }
        });





    }
    public void up(){
        angWord.setText(testData.get(currentWordIndex).getValue());
        example.setText(testData.get(currentWordIndex).getExample());
    }


}
