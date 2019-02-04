package com.mvp.fractal.hadar.danny.monom.graph;

import com.mvp.fractal.hadar.danny.monom.BasePresenter;

public interface GraphContract {
    interface View {
        void displayErrorInput();

        void drawGraph(PolynomialPainter polynomialPainter);

        void hideSoftKeyBoard();

        void updateDrawing();
    }

    interface Presenter extends BasePresenter<View> {
        void onDrawButtonClicked(String input);

        void keyBoardStateChanged();

        void onClearButtonClicked();
    }
}
