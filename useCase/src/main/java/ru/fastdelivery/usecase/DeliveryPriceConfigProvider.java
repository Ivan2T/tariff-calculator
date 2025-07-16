package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.price.Price;

import java.math.BigDecimal;

@FunctionalInterface
public interface DeliveryPriceConfigProvider {
    Price costPerKg();

    default Price minimalPrice() {
        throw new UnsupportedOperationException("Not implemented");
    }

    default BigDecimal pricePerCubicMeter() {
        throw new UnsupportedOperationException("Not implemented");
    }

    default int minimalDistanceKm() {
        throw new UnsupportedOperationException("Not implemented");
    }
}