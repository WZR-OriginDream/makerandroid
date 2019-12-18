package com.lipiao.makerandroid.Bean.ViewBean;

import android.support.annotation.NonNull;

public class CollectArticleBean {
    private String collectUrl;
    private String collectTitles;

    public CollectArticleBean(String collectUrl, String collectTitles) {
        this.collectUrl = collectUrl;
        this.collectTitles = collectTitles;
    }

    public String getCollectUrl() {
        return collectUrl;
    }

    public void setCollectUrl(String collectUrl) {
        this.collectUrl = collectUrl;
    }

    public String getCollectTitles() {
        return collectTitles;
    }

    public void setCollectTitles(String collectTitles) {
        this.collectTitles = collectTitles;
    }

    @NonNull
    @Override
    public String toString() {
        return "网址 "+collectUrl+"标题 "+collectTitles;
    }
}
