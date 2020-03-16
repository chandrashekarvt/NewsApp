package com.example.newz;

public class News {

    private String source_name;
    private String description;

    private String NewsUrl;

    public News(String source_name, String description,String NewsUrls) {
        this.source_name = source_name;
        this.description = description;
        this.NewsUrl = NewsUrls;
    }

    public String getSource_name() {
        return source_name;
    }

    public String getNewsUrl() {
        return NewsUrl;
    }

    public String getDescription() {
        return description;
    }
}
