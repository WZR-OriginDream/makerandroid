package com.lipiao.makerandroid.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private Context context;

    //构造函数 增加context glide加载图片要使用
    public ProjectArticleAdapter(List<ProjectArticleBean> projectArticleBeanList, Context context) {
        this.projectArticleBeanList = projectArticleBeanList;
        this.context = context;
    }

    //重写3个抽象方法
    @NonNull
    @Override
    public ProjectArticleAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.article_card_item_pic, viewGroup, false);
        return new ProjectArticleAdapter.ContactViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ProjectArticleAdapter.ContactViewHolder contactViewHolder, int i) {
        //citybeanList中包含的都是citybean类的对象
        //通过其get()方法可以获得其中的对象
        ProjectArticleBean projectArticleBean = projectArticleBeanList.get(i);
        contactViewHolder.tvAuthor.setText(projectArticleBean.getAuthor());
        contactViewHolder.tvTitle.setText(projectArticleBean.getTitle());
        contactViewHolder.tvDate.setText(projectArticleBean.getTime());
        contactViewHolder.tvDescribe.setText(projectArticleBean.getDescribe());
        //图片
        Glide.with(context).load(projectArticleBean.getPreviewPicUrl()).into(contactViewHolder.ivPreview);
        //cardView监听事件
        //单击
        contactViewHolder.cvProjectArticle.setOnClickListener(v -> {
            //触发自定义监听的单击事件
            onItemClickListener.onItemClick(contactViewHolder.itemView, i);
        });
        //长按
        contactViewHolder.cvProjectArticle.setOnLongClickListener(v -> {
            //触发自定义监听的长按事件
            onItemClickListener.onItemLongClick(contactViewHolder.itemView, i);
            return true;//表示此事件已经消费，不会触发单击事件
        });


    }

    @Override
    public int getItemCount() {
        return projectArticleBeanList.size();
    }

    //重点：该泛型必须是 自定义的适配器(如MyAdapter).ViewHolder
    class ContactViewHolder extends RecyclerView.ViewHolder {
        //create the viewHolder class
        protected CardView cvProjectArticle;//需点击得view
        protected TextView tvAuthor;
        protected TextView tvTitle;
        protected TextView tvDate;
        protected TextView tvDescribe;
        protected ImageView ivPreview;


        public ContactViewHolder(View itemView) {
            super(itemView);
            cvProjectArticle = itemView.findViewById(R.id.cv_project_article_card);
            tvAuthor = itemView.findViewById(R.id.tv_project_article_author);
            tvTitle = itemView.findViewById(R.id.tv_project_article_title);
            tvDate = itemView.findViewById(R.id.tv_project_article_date);
            tvDescribe = itemView.findViewById(R.id.tv_project_article_describe);
            ivPreview = itemView.findViewById(R.id.iv_project_article_preview);
        }
    }//ContactViewHolder class

    //有关监听（外部调用）
    public void setOnItemClickListener(ProjectArticleAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 自定义监听回调接口，RecyclerView 的 单击和长按事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

}//adapter.class