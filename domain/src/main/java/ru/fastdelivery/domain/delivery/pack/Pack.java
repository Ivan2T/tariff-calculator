package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.dimension.OuterDimensions;

import java.math.BigInteger;

/**
 * Упаковка груза, содержащая вес и габариты
 */
public record Pack(Weight weight, OuterDimensions dimensions) {

    private static final Weight MAX_WEIGHT = new Weight(BigInteger.valueOf(150_000));

    public Pack {
        if (weight.greaterThan(MAX_WEIGHT)) {
            throw new IllegalArgumentException("Package can't be more than " + MAX_WEIGHT.weightGrams() + " grams");
        }
    }

    /**
     * Объём упаковки в м³ с точностью до 4 знаков
     */
    public double volumeCubicMeters() {
        return dimensions.volumeCubicMeters();
    }
}