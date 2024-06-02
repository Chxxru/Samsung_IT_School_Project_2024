package com.example.myapplication.wordtest.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.wordtest.data.Word;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Word> words;


    WordAdapter(Context context) {
        this.words = new ArrayList<Word>();
        this.inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordAdapter.ViewHolder holder, int position) {
        Word word = words.get(position);
        holder.nameView.setText(word.getValue());
        holder.capitalView.setText(word.getTranslation());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
    public Word removeByPosition(int position){
        Word toDelete = words.get(position);
        words.remove(position);
        notifyItemRemoved(position);
        return toDelete;
    }


    public void refresh(List<Word> arr){
        words.clear();
        words.addAll(arr);
        notifyDataSetChanged();
    }
    public Word getByPosition(int position){
        return words.get(position);
    }
    public void clearList(){
        words.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, capitalView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.word_value_tv);
            capitalView = view.findViewById(R.id.word_translation_tv);
        }
    }
}
