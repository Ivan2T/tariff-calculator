package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Габариты упаковки")
public record OuterDimensionsDto(
        @Schema(description = "Длина, мм") int length,
        @Schema(description = "Ширина, мм") int width,
        @Schema(description = "Высота, мм") int height
) {
}