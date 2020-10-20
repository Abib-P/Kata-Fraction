package pabib.kata.fraction.repository;

import pabib.kata.fraction.core.Fraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryFractionRepository implements FractionRepository {

    private final List<Fraction> fractions;

    public InMemoryFractionRepository(List<Fraction> fractions) {
        this.fractions = fractions;
    }

    public static InMemoryFractionRepository createDefault() {
        return new InMemoryFractionRepository(new ArrayList<>());
    }

    @Override
    public int add(Fraction fraction) {
        fractions.add(fraction);
        return fractions.size() - 1;
    }

    @Override
    public boolean remove(int id) {
        if (id < 0 || fractions.size() <= id)
            return false;

        fractions.remove(id);
        return true;
    }

    @Override
    public List<FractionEntity> findAll() {
        List<FractionEntity> returnedFractionEntity = new ArrayList<>();
        for (int i = 0; i < fractions.size(); i++) {
            returnedFractionEntity.add(new FractionEntity(i, fractions.get(i)));
        }
        return returnedFractionEntity;
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
