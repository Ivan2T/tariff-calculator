package ru.fastdelivery.usecase;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public abstract class DeliveryPriceConfigProviderImpl implements DeliveryPriceConfigProvider {

    private BigDecimal pricePerCubicMeter = new BigDecimal("12000");
    private int minimalDistanceKm = 450;

    @Override
    public BigDecimal pricePerCubicMeter() {
        return pricePerCubicMeter;
    }

    @Override
    public int minimalDistanceKm() {
        return minimalDistanceKm;
    }
}