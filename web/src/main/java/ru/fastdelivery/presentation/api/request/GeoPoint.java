package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Координаты для расчёта расстояния")
public record GeoPoint(
        @Schema(description = "Широта", example = "55.7558")
        double lat,

        @Schema(description = "Долгота", example = "37.6173")
        double lon
) {}