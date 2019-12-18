package com.lipiao.makerandroid.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lipiao.makerandroid.Bean.ViewBean.RvAboutBean;
import com.lipiao.makerandroid.R;

import java.util.List;

/**
 * 用户界面 关于recycleView适配器
 */
public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ContactViewHolder> {

    //MyAdapter的成员变量contactInfoList, 这里被我们用作数据的来源
    private List<RvAboutBean> rvAboutBeanList;
    private Context context;
    private AboutAdapter.OnAboutItemClickListener onAboutItemClickListener;


    public AboutAdapter(List<RvAboutBean> rvAboutBeanList, Context context) {
        this.rvAboutBeanList = rvAboutBeanList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.user_about, viewGroup, false);
        return new AboutAdapter.ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
//通过其get()方法可以获得其中的对象
        RvAboutBean rvAboutBean = rvAboutBeanList.get(i);
        contactViewHolder.tvTitle.setText(rvAboutBean.getAboutTitleStr());
        contactViewHolder.ivAbout.setImageResource(rvAboutBean.getAboutImageId());
        //单击
        contactViewHolder.relativeLayout.setOnClickListener(v -> {
            //触发自定义监听的单击事件
            onAboutItemClickListener.onItemClick(contactViewHolder.itemView, i);
        });

    }

    @Override
    public int getItemCount() {
        return rvAboutBeanList.size();
    }

    //有关监听（外部调用）
    public void setOnAboutItemClickListener(AboutAdapter.OnAboutItemClickListener onAboutItemClickListener) {
        this.onAboutItemClickListener = onAboutItemClickListener;
    }


    //子项布局初始化 声明加初始化
    public class ContactViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAbout;
        TextView tvTitle;
        RelativeLayout relativeLayout;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAbout = itemView.findViewById(R.id.iv_user_about_images);
            tvTitle = itemView.findViewById(R.id.tv_user_about_title);
            relativeLayout = itemView.findViewById(R.id.rl_user_about_layout);
        }

    }


    /**
     * 自定义监听回调接口，RecyclerView 的 单击和长按事件
     */
    public interface OnAboutItemClickListener {
        void onItemClick(View view, int position);
    }
}
