package com.lipiao.makerandroid.Base;


/**
 * MVP模式重构项目 （暂时不写了，还不会）
 * View层接口基类
 * @param <T>
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
}
