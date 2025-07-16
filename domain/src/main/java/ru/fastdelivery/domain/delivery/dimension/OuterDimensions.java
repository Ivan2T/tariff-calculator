package ru.fastdelivery.domain.delivery.dimension;

/**
 * Габариты упаковки
 */
public record OuterDimensions(Length length, Length width, Length height) {

    public double volumeCubicMeters() {
        int l = length.roundedTo50();
        int w = width.roundedTo50();
        int h = height.roundedTo50();

        return round4(l * w * h / 1_000_000_000.0);
    }

    private double round4(double val) {
        return Math.round(val * 10_000.0) / 10_000.0;
    }
}