package com.lipiao.makerandroid.Bean;

//体系标签简化Bean类 体系大标题+下属的小标签集合
public class SystemSimpleBean {
    String title;
    String[] tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public SystemSimpleBean(String title, String[] tags) {
        this.title = title;
        this.tags = tags;
    }
}
