package com.lipiao.makerandroid.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lipiao.makerandroid.Bean.SystemSimpleBean;
import com.lipiao.makerandroid.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

//导向碎片中 知识体系与拓维导航 标签适配器——recycleView+cardView+flowLayout
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ContactViewHolder> {

    String TAG ="TagsAdapter";

    //TagsAdapter的成员变量systemBeanList.DataBean, 这里被我们用作数据的来源
    private List<SystemSimpleBean> systemSimpleBeanList;
    private Context context;

    //构造函数
    public TagsAdapter(List<SystemSimpleBean> systemSimpleBeanList) {
        this.systemSimpleBeanList = systemSimpleBeanList;
    }


    @NonNull
    @Override
    public TagsAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //获取context
        context = viewGroup.getContext();

        View itemView = LayoutInflater.from(context).
                inflate(R.layout.flow_tags_card_item, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {

        //通过其get()方法可以获得其中的对象
        SystemSimpleBean systemSimpleBean = systemSimpleBeanList.get(i);
        contactViewHolder.tvTitle.setText(systemSimpleBean.getTitle());
        Log.d(TAG, "title: "+systemSimpleBean.getTitle());


        //获取布局填充器,一会将tv.xml文件填充到标签内.
        final LayoutInflater mInflater = LayoutInflater.from(context);

        //流式布局
        contactViewHolder.tflTags.setAdapter(new TagAdapter<String>(systemSimpleBean.getTags()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv_item,
                        contactViewHolder.tflTags, false);
                tv.setText(s);
                return tv;
            }
        });

        //          为点击标签设置点击事件.
        contactViewHolder.tflTags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent) {
                Toast.makeText(context, systemSimpleBean.getTags()[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return systemSimpleBeanList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        //d对应flow_tags_card_item.xml
        //体系标题
        TextView tvTitle;
        //流失布局
        TagFlowLayout tflTags;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_flow_tags_card_title);
            tflTags = itemView.findViewById(R.id.id_flow_layout);
        }
    }
}
