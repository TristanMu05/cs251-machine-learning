import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class KNNClassifier {
    private static class Neighbor {
        private final DataObject node;
        private final double distance;

        Neighbor(DataObject node, double distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public String predict(ArrayList<DataObject> trainingData, double testVisits,
            double testAverageSpend, int k) {
        ArrayList<Neighbor> neighbors = new ArrayList<>();
        for (DataObject dataObject : trainingData) {
            neighbors.add(new Neighbor(dataObject,
                    dataObject.calculateDistance(testVisits, testAverageSpend)));
        }

        neighbors.sort(Comparator.comparingDouble(neighbor -> neighbor.distance));

        Map<String, Integer> tallies = new HashMap<>();
        for (int i = 0; i < Math.min(k, neighbors.size()); i++) {
            String label = neighbors.get(i).node.getLabel();
            tallies.put(label, tallies.getOrDefault(label, 0) + 1);
        }

        return Collections.max(tallies.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
