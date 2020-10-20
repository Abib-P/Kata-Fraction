package pabib.kata.fraction.formatter;

import pabib.kata.fraction.repository.FractionEntity;

public interface FractionFormatter {
    String format(FractionEntity fraction);
}
