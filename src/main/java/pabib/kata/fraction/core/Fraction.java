package pabib.kata.fraction.core;

public class Fraction {

    private final int numerator;
    private final int denominator;

    public Fraction(final int numerator, final int denominator) {
        if (denominator == 0)
            throw new IllegalArgumentException("denominator cannot be equal to zero");

        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction addition(final Fraction fraction) {
        final int numerator = this.numerator * fraction.denominator + fraction.numerator * this.denominator;
        final int denominator = this.denominator * fraction.denominator;

        return new Fraction(numerator, denominator);
    }

    public Fraction subtract(final Fraction fraction) {
        final int numerator = this.numerator * fraction.denominator - fraction.numerator * this.denominator;
        final int denominator = this.denominator * fraction.denominator;

        return new Fraction(numerator, denominator);
    }

    public Fraction multiply(final Fraction fraction) {
        final int numerator = this.numerator * fraction.numerator;
        final int denominator = this.denominator * fraction.denominator;

        return new Fraction(numerator, denominator);
    }

    public Fraction divide(final Fraction fraction) {
        final int numerator = this.numerator * fraction.denominator;
        final int denominator = this.denominator * fraction.numerator;

        return new Fraction(numerator, denominator);
    }

    public int getNumerator() { return numerator; }

    public int getDenominator() { return denominator; }
}
