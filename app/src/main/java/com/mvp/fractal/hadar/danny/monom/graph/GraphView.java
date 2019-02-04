package com.mvp.fractal.hadar.danny.monom.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GraphView extends View {
    private PolynomialPainter mPolynomialPainter;
    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    private float mWidthScale;
    private float mHeightScale;

    private float mHorizontalTranslator;
    private float mVerticalTranslator;

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        mWidthScale = mWidth / 10f;
        mHeightScale = mHeight / 10f;

        mHorizontalTranslator = mWidth / 2f;
        mVerticalTranslator = mHeight / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawAxis(canvas);

        if (mPolynomialPainter != null) {
            mPolynomialPainter.drawPolynomial(canvas, mPaint, mWidthScale, mHeightScale, mHorizontalTranslator, mVerticalTranslator);
        }
    }

    private void drawGrid(Canvas canvas) {
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(33);

        float x = 0;
        float y = 0;

        for (int i = 0; i <= 10; i++, x += mWidthScale, y += mHeightScale) {
            mPaint.setColor(Color.BLUE);
            // Horizontal lines
            canvas.drawLine(0, y, mWidth, y, mPaint);
            // Vertical lines
            canvas.drawLine(x, 0, x, mHeight, mPaint);

            if (i != 0 && i != 5 && i != 10) {
                drawNumbers(canvas, i, x, y);
            }
        }
    }

    private void drawAxis(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);
        // X axis
        canvas.drawLine(0, mHeight / 2f, mWidth, mHeight / 2f, mPaint);
        // Y axis
        canvas.drawLine(mWidth / 2f, 0, mWidth / 2f, mHeight, mPaint);
    }

    private void drawNumbers(Canvas canvas, int i, float x, float y) {
        mPaint.setColor(Color.BLACK);

        canvas.drawText(String.valueOf(-(i - 5)), mHorizontalTranslator, y, mPaint);
        canvas.drawText(String.valueOf(i - 5), x, mVerticalTranslator, mPaint);

    }

    public void setPolynomialPainter(PolynomialPainter polynomialPainter) {
        mPolynomialPainter = polynomialPainter;
    }
}
