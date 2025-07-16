package ru.fastdelivery.domain.common.currency;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CurrencyFactory {
    private final CurrencyPropertiesProvider propertiesProvider;

    public CurrencyFactory(CurrencyPropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    public Currency create(String code) {
        if (code == null || !propertiesProvider.isAvailable(code)) {
            throw new IllegalArgumentException("Currency code contains not available value");
        }
        return new Currency(code);
    }
}