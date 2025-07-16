package ru.fastdelivery.calc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.presentation.api.request.*;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CalculateControllerTest extends ControllerTest {

    final String baseCalculateApi = "/api/v1/calculate";

    @MockBean
    TariffCalculateUseCase useCase;

    @MockBean
    CurrencyFactory currencyFactory;

    Currency rub;

    @BeforeEach
    void setUp() {
        rub = new CurrencyFactory(code -> true).create("RUB");
        when(currencyFactory.create("RUB")).thenReturn(rub);
    }

    @Test
    @DisplayName("Валидные данные -> ответ 200")
    void whenValidInputData_thenReturn200() {
        var request = new CalculatePackagesRequest(
                List.of(new CargoPackage(BigInteger.TEN)),
                "RUB",
                new GeoPoint(55.0, 37.0),
                new GeoPoint(59.0, 30.0)
        );

        when(useCase.calc(any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        var body = response.getBody();
        assertNotNull(body);
        assertEquals(BigDecimal.valueOf(10).setScale(2), body.totalPrice().setScale(2));
    }

    @Test
    @DisplayName("Список упаковок null -> ответ 400")
    void whenNullPackages_thenReturn400() {
        var request = new CalculatePackagesRequest(null, "RUB", new GeoPoint(0, 0), new GeoPoint(0, 0));

        ResponseEntity<String> response =
                restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Расчёт с объёмом и расстоянием -> ответ 200")
    void whenValidAdvancedRequest_thenReturn200() {
        var advancedRequest = new CalculatePackagesAdvancedRequest(
                List.of(new CargoPackageAdvanced(
                        BigInteger.valueOf(5000),
                        new OuterDimensionsDto(345, 589, 234)
                )),
                "RUB",
                new GeoPoint(55.7558, 37.6173),
                new GeoPoint(59.9343, 30.3351)
        );

        when(useCase.calc(any(), any(), any()))
                .thenReturn(new Price(BigDecimal.valueOf(886.6), rub));
        when(useCase.minimalPrice())
                .thenReturn(new Price(BigDecimal.valueOf(300), rub));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi + "/advanced", advancedRequest, CalculatePackagesResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        var body = response.getBody();
        assertNotNull(body);
        assertEquals(BigDecimal.valueOf(886.6).setScale(2), body.totalPrice().setScale(2));
    }
}