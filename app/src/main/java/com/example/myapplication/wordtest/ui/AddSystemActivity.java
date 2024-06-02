package com.example.myapplication.wordtest.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewTreeLifecycleOwner;

import com.example.myapplication.R;
import com.example.myapplication.wordtest.data.Word;
import com.example.myapplication.wordtest.domain.TestManager;

import java.util.ArrayList;

public class AddSystemActivity extends AppCompatActivity {
    public final int A_GRADE = 10;
    public final int B_GRADE = 20;
    public final int C_GRADE = 30;



    private TestManager manager;
    private ArrayList<Word> data;
    private int currentIndex;
    private TextView angWordTV;
    private TextView rusWordTV;
    private TextView example;


    private float currY;



    private CardView wordCardCV;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_system_words);
        int grade = getIntent().getIntExtra("grade",0);
        angWordTV = (TextView) findViewById(R.id.ang_wd_TV);
        rusWordTV = (TextView) findViewById(R.id.rus_wd_TV) ;
        example=(TextView)findViewById(R.id.example_TV);
        wordCardCV=(CardView) findViewById(R.id.wor_To_Learn_CV);



        manager = new TestManager(this);
        data = manager.getSystemWordsByGrade(grade,System.currentTimeMillis());
        currentIndex=0;


        angWordTV.setText(String.valueOf(data.get(currentIndex).getValue()));
        rusWordTV.setText(String.valueOf(data.get(currentIndex).getTranslation()));
        example.setText(String.valueOf(data.get(currentIndex).getExample()));





        wordCardCV.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                float min_swipe_distance = (float)displayMetrics.heightPixels/2 - (float) 300;
                float max_swipe_distance = (float)displayMetrics.heightPixels/2 + (float) 300;

                int cardHeight=wordCardCV.getHeight();
                float cardStart=((float)displayMetrics.heightPixels/ 2) - (float)cardHeight / 2;
                float cardEnd=((float)displayMetrics.heightPixels/ 2) + (float)cardHeight / 2;
                if (event.getAction()==MotionEvent.ACTION_MOVE){

                    float newY=event.getRawY();
                    if ((newY - cardHeight/2) < cardStart ||(newY + cardHeight/2) > cardEnd) {
                        wordCardCV.animate().y(newY - ((float) cardHeight / 2))
                                .setDuration(0).start();

                    }
                }else if (event.getAction()==MotionEvent.ACTION_UP){

                    currY=event.getRawY();
                    wordCardCV.animate()
                            .y(cardStart)
                            .setDuration(150);
                    if (currY<min_swipe_distance){
                        manager.systemWordIsShown(data.get(currentIndex).getId());
                        updateWord();
                    } else if (currY>max_swipe_distance) {
                        manager.systemWordIsShown(data.get(currentIndex).getId());
                        manager.addWord(data.get(currentIndex));
                        updateWord();
                    }
                }

                v.performClick();
                return true;
            }
        });



    }

    public void updateWord(){
        if (currentIndex+1==data.size()){
            Intent i = new Intent(AddSystemActivity.this,LearnWordsActivity.class);
            startActivity(i);
        }else{
            currentIndex++;
            angWordTV.setText(data.get(currentIndex).getValue());
            rusWordTV.setText(data.get(currentIndex).getTranslation());
            example.setText(data.get(currentIndex).getExample());

        }
    }
}
