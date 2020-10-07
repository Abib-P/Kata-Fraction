package pabib.kata.fraction.repository;

import pabib.kata.fraction.core.Fraction;

import java.util.List;
import java.util.Optional;

public interface FractionRepository {

    Fraction add(Fraction fraction);

    boolean remove(int id);

    List<Fraction> findAll();

    Optional<Fraction> find(int id);
}
