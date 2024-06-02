package com.example.myapplication.wordtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ChooseGradeActivity extends AppCompatActivity{
    public final int A_GRADE = 10;
    public final int B_GRADE = 20;
    public final int C_GRADE = 30;

    Button a;
    Button b;
    Button c;
    int grade=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_grade_dialog);
        a=findViewById(R.id.a_grade_btn);
        b=findViewById(R.id.b_grade_btn);
        c=findViewById(R.id.c_grade_btn);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = A_GRADE;
                Intent i = new Intent(ChooseGradeActivity.this,AddSystemActivity.class);
                i.putExtra("grade",grade);
                startActivity(i);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = B_GRADE;
                Intent i = new Intent(ChooseGradeActivity.this,AddSystemActivity.class);
                i.putExtra("grade",grade);
                startActivity(i);
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = C_GRADE;
                Intent i = new Intent(ChooseGradeActivity.this,AddSystemActivity.class);
                i.putExtra("grade",grade);
                startActivity(i);
            }
        });



    }
}
