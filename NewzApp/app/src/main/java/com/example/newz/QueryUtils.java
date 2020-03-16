package com.example.newz;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils(){

    }


    public static List<News> fetchData(String url){
        URL url1 = createUrl(url);

        String JsonResponse = null;
        try {
            JsonResponse = makeHttpRequest(url1);

        }catch (IOException e){
            Log.i(LOG_TAG,"Fetching Problem at fetchData");
        }

        List<News> list = extractInfoFromJson(JsonResponse);

        return list;
    }

    public static URL createUrl(String Url){
        URL url = null;
        try {
            url = new URL(Url);
        }catch (MalformedURLException e){
            Log.i(LOG_TAG,"Url error");
        }

        return url;
    }

    public static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder builder = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                builder.append(line);
                line = reader.readLine();
            }
        }

        return builder.toString();
    }


    public static String makeHttpRequest(URL url) throws IOException{
        String JsonResponse = "";
        if (url==null){
            return JsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                JsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e){
            Log.i(LOG_TAG,"Http connection Error");
        }finally {
            if (inputStream!=null){
                inputStream.close();
            }
            if (httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
        }
        return JsonResponse;
    }


    public static List<News> extractInfoFromJson(String JsonNews){
        if (TextUtils.isEmpty(JsonNews)){
            return null;
        }

        List<News> list = new ArrayList<>();

        try {
            JSONObject baseObject = new JSONObject(JsonNews);
            JSONArray articlesArray = baseObject.getJSONArray("articles");

            for (int i =0;i<articlesArray.length();i++){
                JSONObject arrayObjects = articlesArray.getJSONObject(i);

                JSONObject source = arrayObjects.getJSONObject("source");
                String source_name = source.getString("name");
                String description = arrayObjects.getString("description");

                String newsUrl = arrayObjects.getString("url");

                News news = new News(source_name,description,newsUrl);

                list.add(news);
            }

        }catch (JSONException e){
            Log.i(LOG_TAG,"extracting json problem");
        }

        return list;
    }








}
