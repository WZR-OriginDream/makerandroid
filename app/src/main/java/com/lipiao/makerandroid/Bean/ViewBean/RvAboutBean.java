package com.lipiao.makerandroid.Bean.ViewBean;

/**
 * 用户碎片
 * 关于数据Bean类 属性如下:
 * 图片资源id int
 * 文字标题 String
 * 网页链接 String
 */
public class RvAboutBean {
    int aboutImageId;
    String aboutTitleStr;
    String aboutUrlStr;

    public int getAboutImageId() {
        return aboutImageId;
    }

    public void setAboutImageId(int aboutImageId) {
        this.aboutImageId = aboutImageId;
    }

    public String getAboutTitleStr() {
        return aboutTitleStr;
    }

    public void setAboutTitleStr(String aboutTitleStr) {
        this.aboutTitleStr = aboutTitleStr;
    }

    public String getAboutUrlStr() {
        return aboutUrlStr;
    }

    public void setAboutUrlStr(String aboutUrlStr) {
        this.aboutUrlStr = aboutUrlStr;
    }

    public RvAboutBean(int aboutImageId, String aboutTitleStr, String aboutUrlStr) {
        this.aboutImageId = aboutImageId;
        this.aboutTitleStr = aboutTitleStr;
        this.aboutUrlStr = aboutUrlStr;
    }
}
