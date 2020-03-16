package com.example.newz;

import android.content.AsyncTaskLoader;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {

        if (mUrl==null){
            return null;
        }

        List<News> news = QueryUtils.fetchData(mUrl);
        return news;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
