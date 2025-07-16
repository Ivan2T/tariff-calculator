package ru.fastdelivery.domain.delivery.geo;

/**
 * Расстояние между координатами
 */
public record Distance(double kilometers) {

    public Distance {
        if (kilometers < 0) {
            throw new IllegalArgumentException("Distance can't be negative");
        }
    }

    public int segmentsOf(double segmentKm) {
        return (int) Math.ceil(kilometers / segmentKm);
    }
}