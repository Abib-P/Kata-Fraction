package pabib.kata.fraction.core;

import java.io.Serializable;
import java.util.Objects;

public class Fraction implements Serializable {

    private final int numerator;
    private final int denominator;

    public Fraction(final int numerator, final int denominator) {
        if (denominator == 0)
            throw new IllegalArgumentException("denominator cannot be equal to zero");

        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction addition(final Fraction fraction) {
        final int newNumerator = this.numerator * fraction.denominator + fraction.numerator * this.denominator;
        final int newDenominator = this.denominator * fraction.denominator;

        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction subtract(final Fraction fraction) {
        final int newNumerator = this.numerator * fraction.denominator - fraction.numerator * this.denominator;
        final int newDenominator = this.denominator * fraction.denominator;

        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction multiply(final Fraction fraction) {
        final int newNumerator = this.numerator * fraction.numerator;
        final int newDenominator = this.denominator * fraction.denominator;

        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction divide(final Fraction fraction) {
        final int newNumerator = this.numerator * fraction.denominator;
        final int newDenominator = this.denominator * fraction.numerator;

        return new Fraction(newNumerator, newDenominator);
    }

    public int getNumerator() { return numerator; }

    public int getDenominator() { return denominator; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return numerator == fraction.numerator &&
                denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}
