package com.lipiao.makerandroid.Bean.RespondBean;

import java.util.List;

//http://localhost:8080/kesheApi/user/findAllArticle 参数userNumber

public class UserCollectArticleBean {

    /**
     * message : findAllArticleSuccess
     * ArticleList : [{"collectUrl":"collectUrlwfqwfqfw","collectTitles":"collectTitlesfqwfqwfyyyyy"}]
     */

    private String message;
    private List<ArticleListBean> ArticleList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ArticleListBean> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<ArticleListBean> ArticleList) {
        this.ArticleList = ArticleList;
    }

    public static class ArticleListBean {
        /**
         * collectUrl : collectUrlwfqwfqfw
         * collectTitles : collectTitlesfqwfqwfyyyyy
         */

        private String collectUrl;
        private String collectTitles;

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
    }
}
