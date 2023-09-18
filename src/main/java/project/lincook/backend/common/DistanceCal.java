package project.lincook.backend.common;

public class DistanceCal {

    public static double distance(double myLat, double myLong, double martLat, double martLong) {

        // github actions test code
        double theta = myLong - martLong;
        double dist = Math.sin(deg2rad(myLat)) * Math.sin(deg2rad(martLat)) + Math.cos(deg2rad(myLat)) * Math.cos(deg2rad(martLat)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;

        return Math.floor(dist * 100) / 100.0;
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
