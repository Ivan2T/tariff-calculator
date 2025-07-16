package ru.fastdelivery.domain.common.currency;

import java.util.HashSet;
import java.util.Set;

public class DefaultCurrencyPropertiesProvider implements CurrencyPropertiesProvider {
    private final Set<String> allowedCodes;

    public DefaultCurrencyPropertiesProvider() {
        allowedCodes = new HashSet<>();
        allowedCodes.add("RUB");
        allowedCodes.add("USD");
        allowedCodes.add("EUR");
    }

    @Override
    public boolean isAvailable(String code) {
        return allowedCodes.contains(code);
    }
}