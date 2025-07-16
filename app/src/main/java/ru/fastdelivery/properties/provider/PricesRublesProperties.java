package ru.fastdelivery.properties.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.DeliveryPriceConfigProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

import java.math.BigDecimal;

@Component
public class PricesRublesProperties implements WeightPriceProvider, DeliveryPriceConfigProvider {

    private BigDecimal perKg;
    private BigDecimal minimal;
    private BigDecimal perCubicMeter;
    private int minDistance;

    @Autowired
    private CurrencyFactory currencyFactory;

    @Override
    public Price costPerKg() {
        return new Price(perKg, currencyFactory.create("RUB"));
    }

    @Override
    public Price minimalPrice() {
        return new Price(minimal, currencyFactory.create("RUB"));
    }

    @Override
    public BigDecimal pricePerCubicMeter() {
        return perCubicMeter;
    }

    @Override
    public int minimalDistanceKm() {
        return minDistance;
    }

    public void setCurrencyFactory(CurrencyFactory currencyFactory) {
        this.currencyFactory = currencyFactory;
    }

    public CurrencyFactory getCurrencyFactory() {
        return currencyFactory;
    }

    public void setPerKg(BigDecimal perKg) {
        this.perKg = perKg;
    }

    public BigDecimal getPerKg() {
        return perKg;
    }

    public void setMinimal(BigDecimal minimal) {
        this.minimal = minimal;
    }

    public BigDecimal getMinimal() {
        return minimal;
    }
}