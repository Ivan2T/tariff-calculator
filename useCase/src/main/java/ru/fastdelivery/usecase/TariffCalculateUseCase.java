package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.geo.Coordinates;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {

    private final WeightPriceProvider weightPriceProvider;
    private final DeliveryPriceConfigProvider deliveryPriceConfigProvider;


    public Price calc(Shipment shipment, Coordinates from, Coordinates to) {
        var weightKg = shipment.weightAllPackages().kilograms();
        var volumeM3 = BigDecimal.valueOf(shipment.totalVolume());

        var priceByWeight = weightPriceProvider.costPerKg().multiply(weightKg);
        var priceByVolume = new Price(
                volumeM3.multiply(deliveryPriceConfigProvider.pricePerCubicMeter()),
                weightPriceProvider.costPerKg().currency()
        );

        var basePrice = priceByWeight.max(priceByVolume);
        var minimalPrice = weightPriceProvider.minimalPrice();
        var appliedBase = basePrice.max(minimalPrice);

        double distanceKm = from.distanceTo(to);
        if (distanceKm <= deliveryPriceConfigProvider.minimalDistanceKm()) {
            return appliedBase;
        }

        BigDecimal multiplier = BigDecimal.valueOf(distanceKm)
                .divide(BigDecimal.valueOf(deliveryPriceConfigProvider.minimalDistanceKm()), 10, RoundingMode.HALF_UP);

        return new Price(
                appliedBase.amount().multiply(multiplier).setScale(2, RoundingMode.CEILING),
                appliedBase.currency()
        );
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}