package com.mvp.fractal.hadar.danny.monom.graph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mvp.fractal.hadar.danny.monom.models.Polynomial;

public class PolynomialPainter {
    private static final int POLYNOMIAL_STROKE_WIDTH = 5;
    private static final int MAXIMUM_X_BORDER = 5;
    private static final int MINIMUM_X_BORDER = -5;

    private static PolynomialPainter sPolynomialPainter;

    private Polynomial mPolynomial;

    private int mLinesDrawnCounter;
    private int mLinesDrawnLimit;

    private PolynomialPainter() {}

    static PolynomialPainter getInstance() {
        if (sPolynomialPainter == null) {
            sPolynomialPainter = new PolynomialPainter();
        }
        return sPolynomialPainter;
    }

    void drawPolynomial(Canvas canvas, Paint mPaint, float widthScale, float heightScale, float horizontalTranslator, float verticalTranslator) {
        if (mPolynomial == null) {
            return;
        }

        mPaint.setStrokeWidth(POLYNOMIAL_STROKE_WIDTH);
        mPaint.setColor(Color.BLACK);

        float previousX = MINIMUM_X_BORDER * widthScale + horizontalTranslator;
        float previousY = (float) (-mPolynomial.evaluate(MINIMUM_X_BORDER) * heightScale + verticalTranslator);
        float currentX, currentY;

        for (float x = MINIMUM_X_BORDER + 0.25f; drawAnotherShape(x); x += 0.25, previousX = currentX, previousY = currentY) {
            currentX = x * widthScale + horizontalTranslator;
            currentY = (float) (-mPolynomial.evaluate(x) * heightScale + verticalTranslator);

            drawLine(canvas, mPaint, previousX, previousY, currentX, currentY);
        }
    }

    private void drawLine(Canvas canvas, Paint mPaint, float previousX, float previousY, float currentX, float currentY) {
        canvas.drawLine(previousX, previousY, currentX, currentY, mPaint);
        mLinesDrawnCounter++;
    }

    void setPolynomial(Polynomial polynomial) {
        mPolynomial = polynomial;
    }

    @Override
    public String toString() {
        return mPolynomial.toString();
    }

    void setLinesDrawnCounter(int linesDrawnCounter) {
        mLinesDrawnCounter = linesDrawnCounter;
    }

    void setLinesDrawnLimit(int linesDrawnLimit) {
        mLinesDrawnLimit = linesDrawnLimit;
    }

    int getLinesDrawnCounter() {
        return mLinesDrawnCounter;
    }

    void incrementLinesDrawnLimit() {
        mLinesDrawnLimit++;
    }

    private boolean drawAnotherShape(float x) {
        return mLinesDrawnCounter < mLinesDrawnLimit && x <= MAXIMUM_X_BORDER;
    }

    void clear() {
        mPolynomial.clear();
    }
}
