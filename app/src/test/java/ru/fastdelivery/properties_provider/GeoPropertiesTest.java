package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.properties.provider.GeoProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoPropertiesTest {

    @Test
    void whenValidLatitudeAndLongitude_thenValid() {
        GeoProperties geo = new GeoProperties();
        GeoProperties.Range lat = new GeoProperties.Range();
        lat.setMin(45);
        lat.setMax(65);

        GeoProperties.Range lon = new GeoProperties.Range();
        lon.setMin(30);
        lon.setMax(96);

        geo.setLatitude(lat);
        geo.setLongitude(lon);

        assertEquals(45, geo.getLatitude().getMin());
        assertEquals(96, geo.getLongitude().getMax());
    }
}