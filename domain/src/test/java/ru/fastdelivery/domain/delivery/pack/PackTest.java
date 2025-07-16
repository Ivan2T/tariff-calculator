package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.dimension.Length;
import ru.fastdelivery.domain.delivery.dimension.OuterDimensions;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenWeightMoreThanMaxAllowed_thenThrowException() {
        var tooHeavyWeight = new Weight(BigInteger.valueOf(150_001));
        var validDimensions = new OuterDimensions(
                new Length(300),
                new Length(400),
                new Length(500)
        );

        assertThatThrownBy(() -> new Pack(tooHeavyWeight, validDimensions))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Package can't be more than");
    }

    @Test
    void whenValidWeightAndDimensions_thenPackCreatedCorrectly() {
        var weight = new Weight(BigInteger.valueOf(10_000));
        var dimensions = new OuterDimensions(
                new Length(345),
                new Length(589),
                new Length(234)
        );

        var pack = new Pack(weight, dimensions);

        assertThat(pack.weight()).isEqualTo(weight);
        assertThat(pack.volumeCubicMeters()).isEqualTo(0.0525);
    }
}