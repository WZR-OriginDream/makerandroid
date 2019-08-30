package com.lipiao.makerandroid.Bean;

//体系标签与拓维导航 tagBean
// 简化Bean类 体系大标题+下属的小标签集合
//与导航解析使用统一简化Bean类（需求相同title+tags），提高代码复用率
public class TagsSimpleBean {
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

    public TagsSimpleBean(String title, String[] tags) {
        this.title = title;
        this.tags = tags;
    }
}
