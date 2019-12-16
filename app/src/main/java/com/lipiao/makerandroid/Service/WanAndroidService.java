package com.lipiao.makerandroid.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 玩安卓服务接口
 * baseURL:https://www.wanandroid.com/
 */
public interface WanAndroidService {

    /**
     * 1.2首页banner
     * 原接口:https://www.wanandroid.com/banner/json
     * 方法：GET
     * 参数：无
     */
    @GET("banner/json")
    Call<ResponseBody> getBannerData();

    /**
     * 1.5 置顶文章
     * 原接口:https://www.wanandroid.com/article/top/json
     * 方法：GET
     * 参数：无
     */
    @GET("article/top/json")
    Call<ResponseBody> getTopArticle();


    /**
     * 2.1 知识体系数据
     * 原接口:https://www.wanandroid.com/tree/json
     * 方法：GET
     * 参数：无
     */
    @GET("tree/json")
    Call<ResponseBody> getTree();

    /**
     * 2.2获取知识体系下的数据
     * https://www.wanandroid.com/article/list/0/json?cid=60
     * 方法：GET
     * 参数：
     * 	cid 分类的id，上述二级目录的id
     * 	页码：拼接在链接上，从0开始。
     */
    @GET("article/list/{page}/json")
    Call<ResponseBody> getTreeData(@Path("page") int page,@Query("cid") int cid);


    /**
     * 3.1 导航数据 标题
     * 原接口:https://www.wanandroid.com/navi/json
     * 方法：GET
     *      * 参数：无
     * @return
     */
    @GET("navi/json")
    Call<ResponseBody> getNavigation();



    /**
     * 4.1 获取项目类别
     * 原接口:https://www.wanandroid.com/project/tree/json
     * 方法： GET
     * 参数： 无
     */
    @GET("project/tree/json")
    Call<ResponseBody> getProjectCategory();

    /**
     * 4.2 项目列表数据 某个项目类别下的文章
     * 原接口:https://www.wanandroid.com/project/list/1/json?cid=294
     * 方法：GET
     * 参数：
     * cid 分类的id，上面项目分类接口 int类型
     * 页码page：拼接在链接中，从1开始。 int类型
     */
    @GET("project/list/{page}/json")
    Call<ResponseBody> getProjectArticle(@Path("page") int page, @Query("cid") int cid);

}
