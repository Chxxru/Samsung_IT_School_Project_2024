package com.example.myapplication.wordtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class AddCustomsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText angWordET = (EditText) findViewById(R.id.angInputEt);
        final EditText rusWordET = (EditText) findViewById(R.id.ruInputET);
        final Button saveWordBtn = (Button) findViewById(R.id.save_User_Wd_Btn);

        saveWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
