package ru.fastdelivery.properties.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.DeliveryPriceConfigProvider;  // импортировать интерфейс
import ru.fastdelivery.usecase.WeightPriceProvider;  // импортировать интерфейс

import java.math.BigDecimal;

@Component
public class PricesRublesProperties implements WeightPriceProvider, DeliveryPriceConfigProvider {

    private BigDecimal perKg;
    private BigDecimal minimal;
    private BigDecimal perCubicMeter;
    private int minDistance;

    @Autowired
    private CurrencyFactory currencyFactory;

    // Реализация метода для расчета стоимости за кг
    @Override
    public Price costPerKg() {
        return new Price(perKg, currencyFactory.create("RUB"));
    }

    // Реализация метода для минимальной цены
    @Override
    public Price minimalPrice() {
        return new Price(minimal, currencyFactory.create("RUB"));
    }

    // Реализация метода для стоимости за кубический метр
    @Override
    public BigDecimal pricePerCubicMeter() {
        return perCubicMeter;
    }

    // Реализация метода для минимального расстояния в километрах
    @Override
    public int minimalDistanceKm() {
        return minDistance;
    }
}