package ru.fastdelivery.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Данные для расчета стоимости с учётом объёма и расстояния")
public record CalculatePackagesAdvancedRequest(
        @NotNull @NotEmpty List<CargoPackageAdvanced> packages,
        @NotNull String currencyCode,
        @NotNull GeoPoint from,
        @NotNull GeoPoint to
) {
}
