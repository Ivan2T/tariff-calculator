package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.fastdelivery.domain.delivery.dimension.OuterDimensions;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,

        @Schema(description = "Габариты упаковки", example = "{\"length\": 100, \"width\": 50, \"height\": 30}")
        OuterDimensions dimensions
) {
}