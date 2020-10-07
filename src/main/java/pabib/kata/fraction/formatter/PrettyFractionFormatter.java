package pabib.kata.fraction.formatter;

import pabib.kata.fraction.core.Fraction;

import static java.lang.Integer.max;

public class PrettyFractionFormatter implements FractionFormatter {

    @Override
    public String format(Fraction fraction) {
        int numeratorLength = String.valueOf(fraction.getNumerator()).length();
        int denominatorLength = String.valueOf(fraction.getDenominator()).length();

        return new StringBuilder()
                .append(" ".repeat(max(0, denominatorLength - numeratorLength)))
                .append(fraction.getNumerator())
                .append("\n")
                .append("-".repeat(max(numeratorLength, denominatorLength)))
                .append("\n")
                .append(" ".repeat(max(0, numeratorLength - denominatorLength)))
                .append(fraction.getDenominator())
                .toString();
    }
}
