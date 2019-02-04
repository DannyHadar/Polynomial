package com.mvp.fractal.hadar.danny.monom;

import java.util.Objects;

public abstract class AbsPresenter<V> {
    protected V mView;

    protected void attach(V view) {
        mView = Objects.requireNonNull(view);
    }

    protected void detach() {
        mView = null;
    }
}
