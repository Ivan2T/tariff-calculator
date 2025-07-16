package ru.fastdelivery.domain.delivery.geo;

public record Coordinates(double latitude, double longitude) {

    public Coordinates {
        if (latitude < 45 || latitude > 65) {
            throw new IllegalArgumentException("Latitude must be between 45 and 65");
        }
        if (longitude < 30 || longitude > 96) {
            throw new IllegalArgumentException("Longitude must be between 30 and 96");
        }
    }

    public double distanceTo(Coordinates to) {
        double R = 6371.0;
        double lat1 = Math.toRadians(latitude);
        double lat2 = Math.toRadians(to.latitude);
        double dLat = Math.toRadians(to.latitude - latitude);
        double dLon = Math.toRadians(to.longitude - longitude);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}