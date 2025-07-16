package ru.fastdelivery.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Габариты упаковки в мм")
public record OuterDimensionsDto(
        @Schema(description = "Длина", example = "300") int length,
        @Schema(description = "Ширина", example = "400") int width,
        @Schema(description = "Высота", example = "500") int height
) {
}
