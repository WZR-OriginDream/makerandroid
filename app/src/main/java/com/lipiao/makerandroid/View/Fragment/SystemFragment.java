package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.View.Adapter.TagsAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SystemFragment extends Fragment {
    View rootView;
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

//    @BindView(R.id.srl_sf)
//    RefreshLayout refreshLayout;

    //流式布局
    @BindView(R.id.id_flow_layout)
    TagFlowLayout tagFlowLayout;

    @BindView(R.id.rv_fragment_system)
    RecyclerView mRecyclerView;
    TagsAdapter tagsAdapter;

    //放入流式布局标签中的内容
    private String[] mVals = new String[]
            {"有信用卡", "有微粒贷", "我有房", "我有车", "有社保", "有公积金",
                    "有人寿保险", "工资银行卡转账", "啥都没有"};

    public SystemFragment() {
        // Required empty public constructor
    }

    public static SystemFragment newInstance() {
        return new SystemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_system, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        initView();
//        initListener();
        return rootView;
    }
    private void initData() {


        //        JSONObject jsonObject = null;
        //        try {
        //            jsonObject = new JSONObject(message);
        //            Gson gson = new Gson();
        //            weatherBean = gson.fromJson(jsonObject.toString(), WeatherBean.class);
        //        } catch (JSONException e) {
        //            //Log.d(TAG, "handleWeatherJson: 错误");
        //        }
        // ————————————————
        //版权声明：本文为CSDN博主「星蔚」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
        //原文链接：https://blog.csdn.net/qq_42391904/article/details/92839998

    }
    private void initView() {

    }


//    private void initView() {
//        //获取布局填充器,一会将tv.xml文件填充到标签内.
//        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
//
//        //流式布局
//        tagFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
//            @Override
//            public View getView(FlowLayout parent, int position, String s) {
//                TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv_item,
//                        tagFlowLayout, false);
//                tv.setText(s);
//                return tv;
//            }
//        });
//
//        //          为点击标签设置点击事件.
//        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent) {
//                Toast.makeText(getContext(), mVals[position], Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//
//    }


//    private void initListener() {
//        refreshLayout.setPrimaryColorsId(R.color.colorPrimaryDark, android.R.color.white);
//
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(1500);
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(1500/*,false*/);//传入false表示加载失败
//            }
//        });
//    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
