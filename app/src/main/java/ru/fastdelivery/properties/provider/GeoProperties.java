package ru.fastdelivery.properties.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("geo")
@Getter
@Setter
public class GeoProperties {
    public int minDistance;
    private Range latitude;
    private Range longitude;

    @Getter
    @Setter
    public static class Range {
        private double min;
        private double max;
    }
}