package pabib.kata.fraction.repository;

import pabib.kata.fraction.core.Fraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryFractionRepository implements FractionRepository {

    private final List<Fraction> fractions;

    public InMemoryFractionRepository() {
        fractions = new ArrayList<>();
    }

    @Override
    public int add(Fraction fraction) {
        fractions.add(fraction);
        return fractions.size()-1;
    }

    @Override
    public boolean remove(int id) {
        if (id < 0 || fractions.size() <= id)
            return false;

        fractions.remove(id);
        return true;
    }

    @Override
    public List<Fraction> findAll() {
        return List.copyOf(fractions);
    }

    @Override
    public Optional<Fraction> find(int id) {
        if (id < 0 || fractions.size() <= id)
            return Optional.empty();

        return Optional.of(fractions.get(id));
    }

    @Override
    public boolean isEmpty() {
        return fractions.isEmpty();
    }
}
