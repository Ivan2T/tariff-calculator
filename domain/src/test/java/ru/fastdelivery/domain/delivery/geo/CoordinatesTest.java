package ru.fastdelivery.domain.delivery.geo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoordinatesTest {

    @Test
    void whenTwoCoordinatesGiven_thenCorrectDistanceCalculated() {
        var from = new Coordinates(55.7558, 37.6173); // Москва
        var to = new Coordinates(59.9343, 30.3351);   // Санкт-Петербург

        double distance = from.distanceTo(to);

        assertThat(distance).isGreaterThan(600);
        assertThat(distance).isLessThan(800); // примерно 633 км
    }
}