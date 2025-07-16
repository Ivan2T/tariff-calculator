package ru.fastdelivery.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.dimension.Length;
import ru.fastdelivery.domain.delivery.dimension.OuterDimensions;
import ru.fastdelivery.domain.delivery.geo.Coordinates;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TariffCalculateUseCaseTest {

    private final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    private final DeliveryPriceConfigProvider configProvider = mock(DeliveryPriceConfigProvider.class);
    private final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    private final TariffCalculateUseCase tariffCalculateUseCase =
            new TariffCalculateUseCase(weightPriceProvider, configProvider);

    @Test
    @DisplayName("Расчёт только по весу (объём нулевой, расстояние ≤ min) -- успешно")
    void whenCalculateByWeight_thenSuccess() {
        // входные данные
        Price minimalPrice = new Price(BigDecimal.TEN, currency);
        Price pricePerKg   = new Price(BigDecimal.valueOf(100), currency);
        Pack  pack         = new Pack(new Weight(BigInteger.valueOf(1_200)),
                new OuterDimensions(new Length(1), new Length(1), new Length(1)));

        // моки
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(configProvider.pricePerCubicMeter()).thenReturn(BigDecimal.ZERO);
        when(configProvider.minimalDistanceKm()).thenReturn(450);

        Shipment shipment = new Shipment(List.of(pack), currency);

        Price result = tariffCalculateUseCase.calc(
                shipment,
                new Coordinates(55, 35),
                new Coordinates(55, 35)
        );

        assertThat(result.amount()).isEqualByComparingTo("120.00");
    }

    @Test
    @DisplayName("Получение минимальной цены")
    void whenMinimalPrice_thenSuccess() {
        Price minimal = new Price(BigDecimal.TEN, currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimal);

        assertThat(tariffCalculateUseCase.minimalPrice()).isEqualTo(minimal);
    }

    @Test
    @DisplayName("Расчёт с объёмом и расстоянием > minDistance")
    void whenCalculateWithVolumeAndDistance_thenCorrectTotal() {
        // данные тарифа/конфига
        Price     minimalPrice   = new Price(BigDecimal.valueOf(300), currency);
        Price     pricePerKg     = new Price(BigDecimal.valueOf(100), currency);
        BigDecimal pricePerM3    = BigDecimal.valueOf(12_000);
        int       minDistanceKm  = 450;

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(configProvider.pricePerCubicMeter()).thenReturn(pricePerM3);
        when(configProvider.minimalDistanceKm()).thenReturn(minDistanceKm);

        // груз
        Pack pack = new Pack(
                new Weight(BigInteger.valueOf(5_000)),
                new OuterDimensions(new Length(345), new Length(589), new Length(234))
        );
        Shipment shipment = new Shipment(List.of(pack), currency);

        Coordinates from = new Coordinates(55.7558, 37.6173);
        Coordinates to   = new Coordinates(59.9343, 30.3351);

        Price result = tariffCalculateUseCase.calc(shipment, from, to);

        /* ожидаемое:
           - объём округляется бизнес-логикой до 0.0525 м³  → 0.0525 * 12 000 = 630.00
           - база = 630  (т.к. > 5 кг × 100 = 500)
           - множитель = 633 км / 450 км ≈ 1.4067
           - итого 630 * 1.4067 = 886.221  → округление CEILING до 2-х знаков = 886.23
         */
        assertThat(result.amount()).isEqualByComparingTo("886.23");
    }
}