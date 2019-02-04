package com.mvp.fractal.hadar.danny.monom.graph;

import android.os.AsyncTask;
import android.util.Log;

import com.mvp.fractal.hadar.danny.monom.AbsPresenter;
import com.mvp.fractal.hadar.danny.monom.exceptions.MalformedPolynomialException;
import com.mvp.fractal.hadar.danny.monom.models.Polynomial;
import com.mvp.fractal.hadar.danny.monom.models.PolynomialParser;

public class GraphPresenter extends AbsPresenter<GraphContract.View> implements GraphContract.Presenter {
    public static final int MAXIMUM_LINES_PER_GRAPH = 40;

    private PolynomialPainter mPolynomialPainter;
    private volatile boolean mKeepDrawing;
    private DrawingTask mTask;

    @Override
    public void onDrawButtonClicked(String input) {
        mView.hideSoftKeyBoard();
        try {
            Polynomial polynomial = PolynomialParser.parsePolynomial(input);
            mPolynomialPainter = PolynomialPainter.getInstance();
            mPolynomialPainter.setPolynomial(polynomial);
            mView.drawGraph(mPolynomialPainter);
            startDrawing();
        } catch (MalformedPolynomialException e) {
            mPolynomialPainter.clear();
            mView.displayErrorInput();
        }
    }

    @Override
    public void keyBoardStateChanged() {
        if (mPolynomialPainter != null){
            startDrawing();
        }
    }

    @Override
    public void onClearButtonClicked() {
        mPolynomialPainter.clear();
        mView.updateDrawing();
    }

    @Override
    public void takeView(GraphContract.View view) {
        attach(view);
    }

    @Override
    public void dropView() {
        detach();
        if (mTask != null) {
            mTask.cancel(true);
        }
    }

    private void addShape() {
        mPolynomialPainter.setLinesDrawnCounter(0);
        mPolynomialPainter.incrementLinesDrawnLimit();

        mView.updateDrawing();
    }

    private void startDrawing() {
        mTask = new DrawingTask();
        mTask.execute();
    }
    private class DrawingTask extends AsyncTask<Void, Void, Void> {

        private static final int SLEEP_BETWEEN_DRAWING = 33;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mPolynomialPainter.setLinesDrawnLimit(0);
            mKeepDrawing = true;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                while (mKeepDrawing && !isCancelled()) {
                    publishProgress();
                    Thread.sleep(SLEEP_BETWEEN_DRAWING);
                    mKeepDrawing = mPolynomialPainter.getLinesDrawnCounter() != MAXIMUM_LINES_PER_GRAPH;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            addShape();
        }

    }
}
