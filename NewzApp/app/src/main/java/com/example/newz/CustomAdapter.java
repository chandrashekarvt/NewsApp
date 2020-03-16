package com.example.newz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<News> {

    public CustomAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, 0,news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View newView = convertView;

        if (newView==null){
            newView = LayoutInflater.from(getContext()).inflate(R.layout.list_items,parent,false);
        }

        TextView source = newView.findViewById(R.id.source);
        TextView description = newView.findViewById(R.id.description);

        News news = getItem(position);

        source.setText(news.getSource_name());

        description.setText(news.getDescription());

        return newView;
    }
}
