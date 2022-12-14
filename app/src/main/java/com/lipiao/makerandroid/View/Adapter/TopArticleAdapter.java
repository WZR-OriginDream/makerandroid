package com.lipiao.makerandroid.View.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lipiao.makerandroid.Bean.ViewBean.ArticleBean;
import com.lipiao.makerandroid.R;

import java.util.List;


//每日文章推荐适配器（top）——recycleView+cardView
public class TopArticleAdapter  extends RecyclerView.Adapter<TopArticleAdapter.ContactViewHolder> {

    //MyAdapter的成员变量contactInfoList, 这里被我们用作数据的来源
    private List<ArticleBean> articleBeanList;

    //构造函数
    public TopArticleAdapter(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
    }

    //重写3个抽象方法
    @NonNull
    @Override
    public TopArticleAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.article_card_item, viewGroup, false);
        return new ContactViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TopArticleAdapter.ContactViewHolder contactViewHolder, int i) {
        //citybeanList中包含的都是citybean类的对象
        //通过其get()方法可以获得其中的对象
        ArticleBean articleBean=articleBeanList.get(i);
        contactViewHolder.author.setText(articleBean.getAuthor());
        contactViewHolder.title.setText(articleBean.getTitle());
        contactViewHolder.time.setText(articleBean.getTime());
        contactViewHolder.kind.setText(articleBean.getKind());
        contactViewHolder.article.setOnClickListener(v -> {
            //触发自定义监听的单击事件
            onItemClickListener.onItemClick(contactViewHolder.itemView, i);
        });
    }

    @Override
    public int getItemCount() {
        return articleBeanList.size();
    }

    //重点：该泛型必须是 自定义的适配器(如MyAdapter).ViewHolder
    class ContactViewHolder extends RecyclerView.ViewHolder {
        //create the viewHolder class
        protected CardView article;
        protected TextView author;
        protected TextView title;
        protected TextView time;
        protected TextView kind;

        public ContactViewHolder(View itemView) {
            super(itemView);
            article=itemView.findViewById(R.id.cv_top_article_card);
            author = itemView.findViewById(R.id.tv_top_article_author);
            title = itemView.findViewById(R.id.tv_top_article_title);
            time = itemView.findViewById(R.id.tv_top_article_time);
            kind = itemView.findViewById(R.id.tv_top_article_kind);
        }
    }

    //有关监听（外部调用）
    public void setOnItemClickListener(TopArticleAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;
    /**
     * 自定义监听回调接口，RecyclerView 的 单击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}//adapter.class