import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class MyMain {
    public static void main(String[] args) {
        String filePath = resolveFilePath(args);
        if (filePath.isEmpty()) {
            System.err.println("No CSV file selected.");
            return;
        }

        ArrayList<DataObject> trainingData = loadTrainingData(filePath);
        if (trainingData.isEmpty()) {
            System.err.println("No training data was loaded from: " + filePath);
            return;
        }

        KNNClassifier classifier = new KNNClassifier();
        int k = 3;

        printPrediction(classifier, trainingData, 4, 130, k);
        printPrediction(classifier, trainingData, 10, 45, k);
        printPrediction(classifier, trainingData, 6, 100, k);
    }

    private static ArrayList<DataObject> loadTrainingData(String filePath) {
        ArrayList<DataObject> trainingData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double visits = Double.parseDouble(values[1].trim());
                double averageSpend = Double.parseDouble(values[2].trim());
                String tier = values[3].trim();
                trainingData.add(new DataObject(visits, averageSpend, tier));
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        return trainingData;
    }

    private static void printPrediction(KNNClassifier classifier,
            ArrayList<DataObject> trainingData, double visits, double averageSpend, int k) {
        String result = classifier.predict(trainingData, visits, averageSpend, k);
        System.out.println("Customer with visits=" + visits + " and avgSpend=" + averageSpend
                + " is predicted as: " + result);
    }

    private static String resolveFilePath(String[] args) {
        if (args.length > 0 && !args[0].isBlank()) {
            return args[0];
        }

        File localCsv = new File("data_customer_loyalty-1.csv");
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
            File file = fileChooser.getSelectedFile();
            return file.toString();
        }
        return "";
    }
}
