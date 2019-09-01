package com.lipiao.makerandroid.Bean;


/**
 * 自定义简化Banner数据Bean类
 * 属性如下：
 * 网络图片地址String类型
 * banner标题String类型
 * web链接地址String类型
 *
 */
public class BannerSimpleBean {
    private String images ;//图片资源集合
    private String titles ;//标题
    private String webURL ;//web链接

    public String getImages() {
        return images;
    }

    public String getTitles() {
        return titles;
    }

    public String getWebURL() {
        return webURL;
    }

    public BannerSimpleBean(String images, String titles, String webURL) {
        this.images = images;
        this.titles = titles;
        this.webURL = webURL;
    }
}
