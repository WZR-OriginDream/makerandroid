package com.lipiao.makerandroid.View.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lipiao.makerandroid.Bean.ArticleBean;
import com.lipiao.makerandroid.Bean.ProjectArticleBean;
import com.lipiao.makerandroid.R;

import java.util.List;

/**
 * 项目文章适配器
 * 与topArticleAdapter类似，适配recycleView+cardView
 * 新增项目图片预览
 */
public class ProjectArticleAdapter extends RecyclerView.Adapter<ProjectArticleAdapter.ContactViewHolder> {

    //MyAdapter的成员变量contactInfoList, 这里被我们用作数据的来源
    private List<ProjectArticleBean> projectArticleBeanList;

    //构造函数
    public ProjectArticleAdapter(List<ProjectArticleBean> projectArticleBeanList) {
        this.projectArticleBeanList = projectArticleBeanList;
    }

    //重写3个抽象方法
    @NonNull
    @Override
    public ProjectArticleAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.article_card_item, viewGroup, false);
        return new ProjectArticleAdapter.ContactViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ProjectArticleAdapter.ContactViewHolder contactViewHolder, int i) {
        //citybeanList中包含的都是citybean类的对象
        //通过其get()方法可以获得其中的对象
        ProjectArticleBean projectArticleBean = projectArticleBeanList.get(i);
        contactViewHolder.author.setText(projectArticleBean.getAuthor());
        contactViewHolder.title.setText(projectArticleBean.getTitle());
        contactViewHolder.time.setText(projectArticleBean.getTime());
        //图片
    }

    @Override
    public int getItemCount() {
        return projectArticleBeanList.size();
    }

    //重点：该泛型必须是 自定义的适配器(如MyAdapter).ViewHolder
    class ContactViewHolder extends RecyclerView.ViewHolder {
        //create the viewHolder class
        protected TextView author;

        protected TextView title;
        protected TextView time;
        protected TextView kind;

        public ContactViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_top_article_author);
            title = itemView.findViewById(R.id.tv_top_article_title);
            time = itemView.findViewById(R.id.tv_top_article_time);
            kind = itemView.findViewById(R.id.tv_top_article_kind);
        }
    }

}//adapter.class