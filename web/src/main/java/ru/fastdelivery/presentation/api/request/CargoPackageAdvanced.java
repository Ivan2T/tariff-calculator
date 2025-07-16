package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

@Schema(description = "Пакет груза с габаритами")
public record CargoPackageAdvanced(
        @Schema(description = "Вес (граммы)", example = "5000")
        BigInteger weight,

        @Schema(description = "Габариты упаковки")
        OuterDimensionsDto dimensions
) {
}