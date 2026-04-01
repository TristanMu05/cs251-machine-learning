
public class IncidentDataPoint {

    //DATA MEMBERS
    private double lat; // longitude

    private double lon; // latitude

    public String label; // labeled: "High" or "Low"

    //CONSTRUCTOR
    public IncidentDataPoint(double lat, double lon, String label) {

        this.lat = lat;

        this.lon = lon;

        this.label = label;

    }

    // SETTERS AND GETTERS
    public double getLat() {

        return lat;

    }

    public void setLat(double lat) {

        this.lat = lat;

    }

    public double getLon() {

        return lon;

    }

    public void setLon(double lon) {

        this.lon = lon;

    }

    public String getLabel() {

        return label;

    }

    public void setLabel(String label) {

        this.label = label;

    }

    public double distanceTo(double targetLat, double targetLon) {

        // Calculate Euclidean distance to a target set of coordinates.
        double latDistance = this.lat - targetLat;

        double lonDistance = this.lon - targetLon;

        return Math.sqrt(latDistance * latDistance + lonDistance * lonDistance);

    }

    public String toString() {

        return label + " for longitude " + lon + " and latitude " + lat;

    }

}
