package part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CrimeKNNClassifier {

    private static class Neighbor {
        private final CrimeDataObject node;
        private final double distance;

        Neighbor(CrimeDataObject node, double distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public String predict(ArrayList<CrimeDataObject> trainingData,
            double testLat, double testLon, int k) {
        ArrayList<Neighbor> neighbors = new ArrayList<>();
        for (CrimeDataObject dataObject : trainingData) {
            neighbors.add(new Neighbor(dataObject,
                    dataObject.calculateDistance(testLat, testLon)));
        }

        neighbors.sort(Comparator.comparingDouble(n -> n.distance));

        System.out.println("Top " + k + " nearest neighbors:");
        for (int i = 0; i < Math.min(k, neighbors.size()); i++) {
            Neighbor n = neighbors.get(i);
            System.out.printf("  Neighbor %d (Distance: %.5f) -> %s%n",
                    i + 1, n.distance, n.node.getRiskLevel());
        }

        Map<String, Integer> tallies = new HashMap<>();
        for (int i = 0; i < Math.min(k, neighbors.size()); i++) {
            String label = neighbors.get(i).node.getRiskLevel();
            tallies.put(label, tallies.getOrDefault(label, 0) + 1);
        }

        return Collections.max(tallies.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
