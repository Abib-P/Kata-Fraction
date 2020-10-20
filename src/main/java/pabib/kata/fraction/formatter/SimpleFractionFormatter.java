package pabib.kata.fraction.formatter;

import pabib.kata.fraction.repository.FractionEntity;

public class SimpleFractionFormatter implements FractionFormatter {

    @Override
    public String format(FractionEntity fraction) {
        return String.format("%d) %s/%s", fraction.getId(), fraction.getNumerator(), fraction.getDenominator());
    }
}
