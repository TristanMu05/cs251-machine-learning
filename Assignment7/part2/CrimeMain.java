package part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class CrimeMain {

    public static void main(String[] args) {
        String filePath = resolveFilePath(args);
        if (filePath.isEmpty()) {
            System.err.println("No CSV file selected.");
            return;
        }

        ArrayList<CrimeDataObject> trainingData = loadTrainingData(filePath);
        if (trainingData.isEmpty()) {
            System.err.println("No training data was loaded from: " + filePath);
            return;
        }

        System.out.println("Loaded " + trainingData.size() + " incidents from: " + filePath);
        System.out.println();

        CrimeKNNClassifier classifier = new CrimeKNNClassifier();
        int k = 3;
        double testLat = 34.057;
        double testLon = -118.248;

        System.out.println("Test location: Latitude=" + testLat + ", Longitude=" + testLon);
        System.out.println("k = " + k);
        System.out.println();

        String prediction = classifier.predict(trainingData, testLat, testLon, k);
        System.out.println("Predicted crime risk: " + prediction);
    }

    private static ArrayList<CrimeDataObject> loadTrainingData(String filePath) {
        ArrayList<CrimeDataObject> trainingData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header
            String line;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");
                if (values.length < 4) continue;

                try {
                    double latitude  = Double.parseDouble(values[1].trim());
                    double longitude = Double.parseDouble(values[2].trim());
                    String riskLevel = values[3].trim();
                    trainingData.add(new CrimeDataObject(latitude, longitude, riskLevel));
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed row " + lineNumber
                            + ": " + line + " (" + e.getMessage() + ")");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        return trainingData;
    }

    private static String resolveFilePath(String[] args) {
        if (args.length > 0 && !args[0].isBlank()) {
            return args[0];
        }

        // Try the default filename in the current directory
        File localCsv = new File("crime_data (1).csv");
        if (localCsv.exists()) {
            return localCsv.getAbsolutePath();
        }

        return requestFilePathname();
    }

    public static String requestFilePathname() {
        JFileChooser fileChooser = new JFileChooser(
                FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().toString();
        }
        return "";
    }
}
