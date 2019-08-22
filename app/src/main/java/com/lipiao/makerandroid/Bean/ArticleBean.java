package com.lipiao.makerandroid.Bean;

/**
 * top文章bean类简化版
 * 用于首页
 */
public class ArticleBean {
    private String author;
    private String title;
    private String time;
    private String kind;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ArticleBean(String author, String title, String time, String kind) {
        this.author = author;
        this.title = title;
        this.time = time;
        this.kind = kind;
    }
}
