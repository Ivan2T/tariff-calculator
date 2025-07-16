package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.WeightPriceProvider;
import ru.fastdelivery.usecase.DeliveryPriceConfigProvider; // Правильный импорт
import ru.fastdelivery.properties.provider.PricesRublesProperties;
import ru.fastdelivery.properties.provider.GeoProperties;

import java.math.BigDecimal;

@Configuration
public class Beans {

    @Bean
    public DeliveryPriceConfigProvider deliveryPriceConfigProvider(
            PricesRublesProperties pricesRublesProperties,
            GeoProperties geoProperties
    ) {
        // Здесь мы создаем DeliveryPriceConfigProvider и передаем необходимые зависимости
        return new DeliveryPriceConfigProvider() {
            @Override
            public Price costPerKg() {
                return pricesRublesProperties.costPerKg();
            }

            @Override
            public Price minimalPrice() {
                return pricesRublesProperties.minimalPrice();
            }

            @Override
            public BigDecimal pricePerCubicMeter() {
                return pricesRublesProperties.pricePerCubicMeter();
            }

            @Override
            public int minimalDistanceKm() {
                return geoProperties.getMinDistance(); // Используем minDistance из GeoProperties
            }
        };
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(
            WeightPriceProvider weightPriceProvider,
            DeliveryPriceConfigProvider deliveryPriceConfigProvider
    ) {
        // Здесь создаем TariffCalculateUseCase и передаем уже готовые зависимости
        return new TariffCalculateUseCase(weightPriceProvider, deliveryPriceConfigProvider);
    }
}