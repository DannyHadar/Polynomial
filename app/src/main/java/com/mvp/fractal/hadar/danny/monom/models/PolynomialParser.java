package com.mvp.fractal.hadar.danny.monom.models;

import android.text.TextUtils;

import com.mvp.fractal.hadar.danny.monom.exceptions.MalformedMonomialException;
import com.mvp.fractal.hadar.danny.monom.exceptions.MalformedPolynomialException;

public class PolynomialParser {
    private static final String MINUS = "-";
    private static final char CARET = '^';

    public static Polynomial parsePolynomial(String polynomialString) throws MalformedPolynomialException {
        polynomialString = polynomialString.replace(" ", "").replace(MINUS, "+-");
        String[] monomialStrings = polynomialString.split("[+]");

        Polynomial polynomial = new Polynomial();

        for (String element : monomialStrings) {
            try {
                if (!TextUtils.isEmpty(element)) {
                    polynomial.addMonomial(parseMonomial(element));
                }
            } catch (MalformedMonomialException e) {
                throw new MalformedPolynomialException();
            }
        }
        if (polynomial.isEmpty()) {
            throw new MalformedPolynomialException();
        }
        return polynomial;
    }

    private static Monomial parseMonomial(String s) throws MalformedMonomialException {
        if (TextUtils.isEmpty(s)) {
            throw new MalformedMonomialException();
        }

        String[] split = s.split("[xX]");
        Monomial monomial;

        try {
            switch (split.length) {
                case 0:
                    monomial = new Monomial(1, 1);
                    break;
                case 1:
                    monomial = oneWordSplit(s, split[0]);
                    break;
                case 2:
                    monomial = twoWordSplit(split[0], split[1]);
                    break;
                default:
                    throw new MalformedMonomialException();
            }
        } catch (NumberFormatException e) {
            throw new MalformedMonomialException();
        }
        return monomial;
    }

    private static Monomial oneWordSplit(String originMonomialString, String firstPart) throws MalformedMonomialException {
        double coefficient;
        int exponent;

        if (originMonomialString.equals(firstPart)) {
            coefficient = Double.parseDouble(firstPart);
            exponent = 0;
        } else if (firstPart.equals("-")) {
            coefficient = -1;
            exponent = 1;
        } else {
            coefficient = Double.parseDouble(firstPart);
            exponent = 1;
        }

        return new Monomial(coefficient, exponent);
    }

    private static Monomial twoWordSplit(String firstPart, String secondPart) throws MalformedMonomialException {
        double coefficient;
        int exponent;

        if (TextUtils.isEmpty(firstPart)) {
            coefficient = 1;
        } else if (firstPart.equals(MINUS)) {
            coefficient = -1;
        } else {
            coefficient = Double.parseDouble(firstPart);
        }

        if (TextUtils.isEmpty(secondPart)) {
            exponent = 1;
        } else {
            if (secondPart.charAt(0) != CARET) {
                throw new MalformedMonomialException();
            }
            exponent = Integer.parseInt(secondPart.substring(1));
        }
        return new Monomial(coefficient, exponent);
    }
}
