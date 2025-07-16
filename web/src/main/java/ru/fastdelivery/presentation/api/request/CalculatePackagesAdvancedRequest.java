package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Расширенный запрос с габаритами и координатами")
public record CalculatePackagesAdvancedRequest(

        @NotNull @NotEmpty
        List<CargoPackageAdvanced> packages,

        @NotNull
        String currencyCode,

        @NotNull
        GeoPoint from,

        @NotNull
        GeoPoint to
) {
}