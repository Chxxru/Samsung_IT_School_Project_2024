package com.example.myapplication.wordtest.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.wordtest.data.Word;
import com.example.myapplication.wordtest.domain.TestManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class LearnWordsActivity extends AppCompatActivity {

    private TestManager manager;
    private WordAdapter adapter;
    private TextView amountWordsLearnTv;
    private TextView amountWordsKnowTv;
    private ConstraintLayout layoutMain;
    private Button howWorks;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         final Button learnWordsBtn = (Button) findViewById(R.id.learnint_words_btn);
         final ImageButton addWordBtn = (ImageButton) findViewById(R.id.add_word_btn);
         final ImageButton addCustomWordBtn=(ImageButton) findViewById(R.id.add_custom_word_btn);
         amountWordsKnowTv=(TextView) findViewById(R.id.amount_know_tv);
         amountWordsLearnTv=(TextView) findViewById(R.id.amount_learning_tv);
         layoutMain = findViewById(R.id.main_constraint);
         final RecyclerView wordsListLV=(RecyclerView) findViewById(R.id.data_list);
         final ImageView image = (ImageView)findViewById(R.id.image_IV);
         final Button deleteAllBtn = (Button)findViewById(R.id.deleteAll_btn);
         howWorks = (Button)findViewById(R.id.how_works_btn);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(wordsListLV);

        manager = new TestManager(this);
        adapter = new WordAdapter(this);
        wordsListLV.setAdapter(adapter);
        adapter.refresh(manager.getAllWords());

        amountWordsKnowTv.setText(String.valueOf(manager.getGoodWordCount()));
        amountWordsLearnTv.setText(String.valueOf(manager.getBadWordCount()));
        howWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment df = new HowDialogFragment();
                df.show(getSupportFragmentManager(),"t");
            }
        });
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Word> deleted = manager.getAllWords();
                manager.deleteAllWords();
                adapter.clearList();
                amountWordsLearnTv.setText("0");
                amountWordsKnowTv.setText("0");
                Snackbar snackbar = Snackbar.make(layoutMain,"Словарь очищен",Snackbar.LENGTH_LONG).setAction("отмена", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Thread t1 = new Thread(){
                            @Override
                            public void run() {
                                synchronized (manager){
                                    manager.addList(deleted);
                                }
                            }
                        };
                        try{
                            t1.start();
                            t1.join();
                        }catch (RuntimeException e){
                            Toast.makeText(LearnWordsActivity.this,"Время ожидания превышено",Toast.LENGTH_SHORT).show();
                        }catch (InterruptedException e1){
                            e1.printStackTrace();
                        }
                        adapter.refresh(deleted);
                        int a = manager.getBadWordCount();
                        amountWordsLearnTv.setText(String.valueOf(manager.getBadWordCount()));
                        amountWordsKnowTv.setText(String.valueOf(manager.getGoodWordCount()));

                    }
                });
                snackbar.show();
            }
        });
        learnWordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Word> testData=manager.getWordsToTest(System.currentTimeMillis());
                if(testData==null||testData.size()<3){
                    DialogFragment newDialog = new LearnDialogFragment();
                    newDialog.show(getSupportFragmentManager(),"t");
                }else {
                    Intent i=new Intent(LearnWordsActivity.this, WordTestActivity.class);
                    startActivity(i);
                }
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
                Intent i = new Intent(LearnWordsActivity.this, ChooseGradeActivity.class);
                startActivity(i);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.refresh(manager.getAllWords());
        amountWordsLearnTv.setText(String.valueOf(manager.getBadWordCount()));
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Word deleteTo = adapter.getByPosition(viewHolder.getAdapterPosition());


            manager.deleteWordById(adapter.removeByPosition(viewHolder.getAdapterPosition()).getId());
            amountWordsKnowTv.setText(String.valueOf(manager.getGoodWordCount()));
            amountWordsLearnTv.setText(String.valueOf(manager.getBadWordCount()));
                Snackbar snackbar = Snackbar.make(layoutMain,"Слово удалено",Snackbar.LENGTH_LONG).setAction("отмена", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        manager.copyWord(deleteTo);
                        adapter.refresh(manager.getAllWords());
                            amountWordsKnowTv.setText(String.valueOf(manager.getGoodWordCount()));
                        amountWordsLearnTv.setText(String.valueOf(manager.getBadWordCount()));
                    }
                });
                snackbar.show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(LearnWordsActivity.this, R.color.delete_red))
                    .addActionIcon(R.drawable.baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                adapter.refresh(manager.getAllWords());
                amountWordsLearnTv.setText(String.valueOf(manager.getBadWordCount()));
                break;
        }
    }*/
    public static class LearnDialogFragment extends DialogFragment {
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.no_words_dialog, null))
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            LearnDialogFragment.this.getDialog().cancel();


                        }
                    });
            return builder.create();
        }
    }
    public static class HowDialogFragment extends DialogFragment {
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.how_works_dialog, null))
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            HowDialogFragment.this.getDialog().cancel();


                        }
                    });
            return builder.create();
        }
    }
}
