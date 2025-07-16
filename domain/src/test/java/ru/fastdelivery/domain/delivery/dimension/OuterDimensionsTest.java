package ru.fastdelivery.domain.delivery.dimension;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OuterDimensionsTest {

    @Test
    void whenDimensionsGiven_thenVolumeCalculatedCorrectly() {
        var length = new Length(345);
        var width = new Length(589);
        var height = new Length(234);

        var dimensions = new OuterDimensions(length, width, height);
        var volume = dimensions.volumeCubicMeters();

        assertThat(volume).isEqualTo(0.0525);
    }
}