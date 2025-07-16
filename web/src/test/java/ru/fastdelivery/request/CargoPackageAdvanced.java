package ru.fastdelivery.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

@Schema(description = "Упаковка с весом и габаритами")
public record CargoPackageAdvanced(
        @Schema(description = "Вес упаковки в граммах", example = "4500") BigInteger weight,
        @Schema(description = "Габариты упаковки") OuterDimensionsDto dimensions
) {
}
