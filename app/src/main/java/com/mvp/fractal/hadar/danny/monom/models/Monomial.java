package com.mvp.fractal.hadar.danny.monom.models;

import android.support.annotation.NonNull;

import com.mvp.fractal.hadar.danny.monom.exceptions.MalformedMonomialException;

import java.util.Locale;

public class Monomial implements Comparable<Monomial> {
    private static final int ZERO = 0;

    private double mCoefficient;
    private int mExponent;

    public Monomial(double coefficient, int exponent) throws MalformedMonomialException {
        if (!isCoefficientValid(coefficient) || !isExponentValid(exponent)) {
            throw new MalformedMonomialException();
        }
        mCoefficient = coefficient;
        mExponent = exponent;
    }

    public double evaluate(double x) {
        return mCoefficient * (Math.pow(x, mExponent));
    }

    public double getCoefficient() {
        return mCoefficient;
    }

    public int getExponent() {
        return mExponent;
    }

    private boolean isCoefficientValid(double coefficient) {
        return coefficient != ZERO;
    }

    private boolean isExponentValid(int exponent) {
        return exponent >= ZERO;
    }

    @Override
    public String toString() {
        StringBuilder resBuilder = new StringBuilder();
        // appending the coefficient (if the coeff is 1 it doesn't need be appended)
        if (mCoefficient != 1 || mExponent == 0) {
            if (mCoefficient == -1 && mExponent != 0) {
                resBuilder.append("-");
            } else {
                resBuilder.append(String.format(Locale.US, "%.1f", mCoefficient));
            }

        }
        if (mExponent != 0) {
            // appending the x (variable)
            resBuilder.append("x");
            if (mExponent != 1) {
                // appending the exponent
                resBuilder.append("^");
                resBuilder.append(mExponent);
            }
        }
        return resBuilder.toString();
    }

    @Override
    public int compareTo(@NonNull Monomial o) {
        return Integer.compare(this.mExponent, o.mExponent);
    }

    public void setCoefficient(double coefficient) {
        mCoefficient = coefficient;
    }
}