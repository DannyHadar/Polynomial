package com.mvp.fractal.hadar.danny.monom.models;

import android.text.TextUtils;

import java.util.Vector;

public class Polynomial {
    private Vector<Monomial> mMonomialsList;

    public Polynomial() {
        mMonomialsList = new Vector<>();
    }

    public double evaluate(double x) {
        double evaluateResult = 0;

        for (Monomial element : mMonomialsList) {
            evaluateResult += element.evaluate(x);
        }
        return evaluateResult;
    }

    void addMonomial(Monomial monomial) {
        if (mMonomialsList.isEmpty()) {
            mMonomialsList.add(monomial);
        } else {
            boolean listModified = false;

            double monomialCoefficient = monomial.getCoefficient();
            int monomialExponent = monomial.getExponent();

            double elementCoefficient;
            int elementExponent;
            double unitedCoefficient;

            for (int i = 0; i < mMonomialsList.size() && !listModified; i++) {
                Monomial element = mMonomialsList.get(i);

                elementCoefficient = element.getCoefficient();
                elementExponent = element.getExponent();

                if (monomialExponent == elementExponent) {
                    unitedCoefficient = monomialCoefficient + elementCoefficient;
                    if (unitedCoefficient == 0) {
                        // Removing Monomial
                        mMonomialsList.remove(i);
                    } else {
                        // Uniting Monomials
                        element.setCoefficient(unitedCoefficient);
                    }
                    listModified = true;
                } else if (monomialExponent > elementExponent) {
                    // Adding Monomial
                    mMonomialsList.add(i, monomial);
                    listModified = true;
                }
            }
            if (!listModified) {
                    // Adding Monomial
                mMonomialsList.add(monomial);
            }
        }
    }

    @Override
    public String toString() {
        if (mMonomialsList.isEmpty()) {
            return "";
        }

        return TextUtils.join("+", mMonomialsList).
                replace("+-", "-");
    }

    boolean isEmpty() {
        return mMonomialsList.isEmpty();
    }

    public void clear() {
        mMonomialsList.clear();
    }
}
