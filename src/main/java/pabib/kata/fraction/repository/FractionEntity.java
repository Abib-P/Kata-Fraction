package pabib.kata.fraction.repository;

import pabib.kata.fraction.core.Fraction;

import java.io.Serializable;

public class FractionEntity implements Serializable {
    private final int id;
    private final Fraction fraction;

    public FractionEntity(int id, Fraction fraction) {
        this.id = id;
        this.fraction = fraction;
    }

    public int getNumerator() {
        return fraction.getNumerator();
    }

    public int getDenominator() {
        return fraction.getDenominator();
    }

    public int getId() {
        return id;
    }
}
