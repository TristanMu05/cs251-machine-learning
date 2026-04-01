package part2;

public class CrimeDataObject {

    private final double latitude;
    private final double longitude;
    private final String riskLevel;

    public CrimeDataObject(double latitude, double longitude, String riskLevel) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.riskLevel = riskLevel;
    }

    public double calculateDistance(double targetLat, double targetLon) {
        double latDiff = this.latitude - targetLat;
        double lonDiff = this.longitude - targetLon;
        return Math.sqrt((latDiff * latDiff) + (lonDiff * lonDiff));
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    @Override
    public String toString() {
        return "CrimeDataObject{latitude=" + latitude + ", longitude=" + longitude
                + ", riskLevel='" + riskLevel + "'}";
    }
}
