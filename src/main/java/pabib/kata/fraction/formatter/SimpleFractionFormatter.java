package pabib.kata.fraction.formatter;

import pabib.kata.fraction.core.Fraction;

public class SimpleFractionFormatter implements FractionFormatter {

    @Override
    public String format(Fraction fraction) {
        return String.format("%s/%s", fraction.getNumerator(), fraction.getDenominator());
    }
}
