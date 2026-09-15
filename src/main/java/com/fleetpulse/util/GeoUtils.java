package com.fleetpulse.util;

public class GeoUtils {
    private static final double EARTH_RADIUS_KM = 6371.0;

    private GeoUtils() {}

    public static double calculateEstimatedRoadDistanceInKm(double latOrigin, double lonOrigin, double latDestination, double lonDestination) {
        double latOriginRad = Math.toRadians(latOrigin);
        double lonOriginRad = Math.toRadians(lonOrigin);
        double latDestinationRad = Math.toRadians(latDestination);
        double lonDestinationRad = Math.toRadians(lonDestination);

        double deltaLat = latOriginRad - latDestinationRad;
        double deltaLon = lonOriginRad - lonDestinationRad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
        + Math.cos(latOriginRad) * Math.cos(latDestinationRad) 
        * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS_KM * c) * 1.3;
    }
}
