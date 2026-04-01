
public class DataObject {

    private final double visits;
    private final double averageSpend;
    private final String label;

    public DataObject(double visits, double averageSpend, String label) {
        this.visits = visits;
        this.averageSpend = averageSpend;
        this.label = label;
    }

    public double calculateDistance(double targetVisits, double targetAverageSpend) {
        double visitsDistance = this.visits - targetVisits;
        double spendDistance = this.averageSpend - targetAverageSpend;
        return Math.sqrt((visitsDistance * visitsDistance) + (spendDistance * spendDistance));
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "DataObject{visits=" + visits + ", averageSpend=" + averageSpend
                + ", label='" + label + "'}";
    }
}
