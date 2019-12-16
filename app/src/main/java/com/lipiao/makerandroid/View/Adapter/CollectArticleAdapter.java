//package com.lipiao.makerandroid.View.Adapter;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//public class CollectArticleAdapter extends BaseAdapter {
//    static String[] arrStr;
//
//    public CollectArticleAdapter(String[] arrStr) {
//        arrStr = this.arrStr
//    }
//
//    @Override
//    public int getCount() {
//        return arrStr.length;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder holder = null;
//        //如果缓存convertView为空，则需要创建View
//
//        if (convertView == null) {
//            holder = new ViewHolder();
//            //根据自定义的Item布局加载布局
//            convertView = mInflater.inflate(R.layout.record_info, null);
//            holder.time = (Button) convertView.findViewById(R.id.time);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//
//        holder.time.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                //主要是以下两句代码起作用
//                data.remove(position);
//                adapater.notifyDataSetChanged();
//            }
//        });
//    return convertView;
//       }
//
////    原文链接：https://blog.csdn.net/zsy657917826/article/details/7821224
//
//}
