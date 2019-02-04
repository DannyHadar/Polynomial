package com.mvp.fractal.hadar.danny.monom;

public interface BasePresenter<V> {
    void takeView(V view);

    void dropView();
}
