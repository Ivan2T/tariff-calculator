package ru.fastdelivery.domain.delivery.dimension;

/**
 * Длина в миллиметрах с округлением до 50
 */
public record Length(int millimeters) {
    private static final int MAX = 1500;
    private static final int MIN = 1;

    public Length {
        if (millimeters < MIN || millimeters > MAX) {
            throw new IllegalArgumentException("Length must be in range 1..1500");
        }
    }

    public int roundedTo50() {
        return ((millimeters + 49) / 50) * 50;
    }
}