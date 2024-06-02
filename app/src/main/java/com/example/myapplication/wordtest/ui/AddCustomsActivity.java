package com.example.myapplication.wordtest.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;
import com.example.myapplication.wordtest.data.Word;
import com.example.myapplication.wordtest.domain.TestManager;
import com.google.android.material.snackbar.Snackbar;


public class AddCustomsActivity extends AppCompatActivity {
    private Context ctx;
    private TestManager tm ;
    private WordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customs);

        final EditText angWordET = (EditText) findViewById(R.id.angInputEt);
        final EditText rusWordET = (EditText) findViewById(R.id.ruInputET);
        final EditText exampleET = (EditText) findViewById(R.id.add_example_et);
        final Button saveWordBtn = (Button) findViewById(R.id.save_User_Wd_Btn);
        final ConstraintLayout layout = findViewById(R.id.add_cust_constraintLy);

        ctx = this;
        tm = new TestManager(this);
        adapter = new WordAdapter(this);
        adapter.refresh(tm.getAllWords());

        saveWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(angWordET.getText().toString().equals("")|| rusWordET.getText().toString().equals("")){
                    Snackbar snackbar = Snackbar.make(layout,"Введите слово и перевод",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
                    String rus=rusWordET.getText().toString();
                    String eng=angWordET.getText().toString();
                    String example = exampleET.getText().toString();
                    Word word = new Word(eng,rus,example,System.currentTimeMillis());
                    try {
                        tm.addWord(word);
                    }catch (RuntimeException e){
                        Toast.makeText(ctx,"что- то пошло не так",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(ctx,"OK",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddCustomsActivity.this,LearnWordsActivity.class);
                    startActivity(i);


                }
            }
        });
    }
}
