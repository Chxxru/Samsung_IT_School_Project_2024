package com.example.myapplication.wordtest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class WordTestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_test);

        Button answ1=(Button) findViewById(R.id.answ1_btn);
        Button answ2=(Button) findViewById(R.id.answ2_btn);
        Button answ3=(Button) findViewById(R.id.answ3_btn);
        Button answ4=(Button) findViewById(R.id.answ4_btn);
        TextView angWord=(TextView) findViewById(R.id.task_text);
    }
}
