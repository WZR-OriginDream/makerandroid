package com.lipiao.makerandroid.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

//https://blog.csdn.net/centralperk/article/details/22088499 参考博客

/**
 * 自定义View 一个不可滑动的ListView 用于在 ScrollView内嵌套ListView时禁止ListView的滚动
 */
public class NoScrollListView extends ListView {

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
