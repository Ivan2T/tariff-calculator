package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.dimension.Length;
import ru.fastdelivery.domain.delivery.dimension.OuterDimensions;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);
        var dimensions = new OuterDimensions(new Length(100), new Length(100), new Length(100));

        var packages = List.of(
                new Pack(weight1, dimensions),
                new Pack(weight2, dimensions)
        );

        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }

    @Test
    void whenSummarizingVolumeOfAllPackages_thenReturnSum() {
        var weight = new Weight(BigInteger.valueOf(1000));
        var dimensions = new OuterDimensions(new Length(345), new Length(589), new Length(234)); // округлится до 0.0525

        var packages = List.of(
                new Pack(weight, dimensions),
                new Pack(weight, dimensions)
        );

        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

        assertThat(shipment.totalVolume()).isEqualTo(0.105); // 0.0525 * 2
    }
}