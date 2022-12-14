package com.lipiao.makerandroid.Bean.ViewBean;

/**
 * 项目文章bean类简化版
 * 用于项目碎片中的任意项目种类碎片
 * 表示recycleView的item数据抽象类
 * 类似于ArticleBean top文章
 */
public class ProjectArticleBean {
    private String author;//作者
    private String title;//文章标题
    private String time;//发布时间
    private String describe;//文章简介
    private String webURL;//文章web链接

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    private String previewPicUrl;//项目预览图片

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

    public String getPreviewPicUrl() {
        return previewPicUrl;
    }

    public void setPreviewPicUrl(String previewPicUrl) {
        this.previewPicUrl = previewPicUrl;
    }

    public ProjectArticleBean(String author, String title, String time, String describe, String webURL, String previewPicUrl) {
        this.author = author;
        this.title = title;
        this.time = time;
        this.describe = describe;
        this.webURL = webURL;
        this.previewPicUrl = previewPicUrl;
    }

    public String outString() {
        return this.author + this.title + this.time + this.describe + this.previewPicUrl + this.webURL;
    }
}

