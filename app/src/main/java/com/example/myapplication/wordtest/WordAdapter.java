package com.example.myapplication.wordtest;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends BaseAdapter {
    /*Context ctx;
    LayoutInflater lInflater;
    ArrayList<Word> objects;

    WordAdapter(Context context, ArrayList<Word> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.adapter_item, parent, false);
        }

        Word p = getWord(position);

        ((TextView) view.findViewById(R.id.word_value_tv)).setText(p.value);
        ((TextView) view.findViewById(R.id.word_translation_tv)).setText(p.translation);
        return view;
    }*/



    private ArrayList<Word> data;
    private Context ctx;
    public WordAdapter(Context context, ArrayList<Word> wordsList){
        ctx=context;
        data=wordsList;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    Word getWord(int position) {
        return ((Word) getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_item, null);
            convertView.setOnTouchListener(new OnSwipeTouchListener(convertView.getContext()){
                @Override
                public void onSwipeLeft() {
                    DatabaseWd.delete(position);
                    notifyDataSetChanged();

                }
            });
        }
            Word word = getWord(position);

        ((TextView) convertView.findViewById(R.id.word_value_tv)).setText(word.value);
        ((TextView) convertView.findViewById(R.id.word_translation_tv)).setText(word.translation);


            return convertView;

    }
}
