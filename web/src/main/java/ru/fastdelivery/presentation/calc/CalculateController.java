package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.dimension.Length;
import ru.fastdelivery.domain.delivery.dimension.OuterDimensions;
import ru.fastdelivery.domain.delivery.geo.Coordinates;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
public class CalculateController {

    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;

    @PostMapping("/advanced")
    @Operation(summary = "Расчет стоимости с учетом расстояния и объема")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculateAdvanced(
            @Valid @RequestBody CalculatePackagesRequest request) {

        var packs = request.packages().stream()
                .map(pkg -> new Pack(
                        new Weight(pkg.weight()),
                        pkg.dimensions() // используем dimensions() для упаковки
                ))
                .toList();

        var shipment = new Shipment(packs, currencyFactory.create(request.currencyCode()));

        // Используем методы lat() и lon() для доступа к координатам
        var from = new Coordinates(request.from().lat(), request.from().lon());
        var to = new Coordinates(request.to().lat(), request.to().lon());

        var total = tariffCalculateUseCase.calc(shipment, from, to);
        var minimal = tariffCalculateUseCase.minimalPrice();

        return new CalculatePackagesResponse(total, minimal);
    }
}