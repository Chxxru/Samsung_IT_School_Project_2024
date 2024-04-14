package com.example.myapplication.wordtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;

import com.example.myapplication.R;

public class AddSystemActivity extends AppCompatActivity{
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_system_words);

        TextView angWordTV = (TextView) findViewById(R.id.ang_wd_TV);
        TextView rusWordTV = (TextView) findViewById(R.id.rus_wd_TV) ;
        CardView wordCardCV=(CardView) findViewById(R.id.wor_To_Learn_CV);

        wordCardCV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int cardHeight=wordCardCV.getHeight();
                float cardStart=((float)displayMetrics.heightPixels/ 2) - (float)cardHeight / 2;
                if (event.getAction()==MotionEvent.ACTION_MOVE){
                    //event.rawXдает нам абсолютное значение новой координаты относительно ширины экрана
                    float newY=event.getRawY();
                    if ((newY - cardHeight) < cardStart) {
                        wordCardCV.animate().y(Math.min(cardStart, newY - ((float) cardHeight / 2)))
                                .setDuration(0).start();

                    }
                }if (event.getAction()==MotionEvent.ACTION_UP){
                    float currX=wordCardCV.getX();
                    wordCardCV.animate().y(cardStart).setDuration(150).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    });
                }
                v.performClick();
                return true;
            }
        });



    }
}
