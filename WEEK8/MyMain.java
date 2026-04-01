
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class MyMain {

    public static void main(String[] args) {
// TASK 1: ALLOW THE USER TO SPECIFY THE FILE: "crime_data.csv"
        String filePath = requestFilePathname();
// TASK 2: STORE ALL THE TRAINING DATA NOTES IN AN ARRAYLIST
        ArrayList<IncidentDataPoint> trainingData = new ArrayList<>();
// TASK 3: READ THE DATA FROM THE CSV INTO THE ARRAYLIST.
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
// SKIP THE HEADER ROW
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double latitude = Double.parseDouble(values[1]);
                double longitude = Double.parseDouble(values[2]);
                String riskLevel = values[3];
                IncidentDataPoint dataPoint = new IncidentDataPoint(latitude, longitude, riskLevel);
                trainingData.add(dataPoint);
                System.out.println(dataPoint);
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }
// TASK 4: Pass the training dataset to the Classifier
        NNClassifier classifier = new NNClassifier(trainingData);
// TASK 5:. CONSTRUCT TEST DATA POINTS
// TEST DATA POINT 1: HIGH
        double testLat = 34.056;
        double testLon = -118.244;
// TEST DATA POINT 2: LOW
// double testLat = 34.056;
// double testLon = -118.25;
// TEST DATA POINT 3: ??
// double testLat = 34.057;
// double testLon = -118.248;
// TASK 6: PREDICT USING THE TEST DATA POINTS
        String result = classifier.predict(testLat, testLon);
        System.out.println("Test HIGH Location: (" + testLat + ", " + testLon + ")");
        System.out.println("Predicted Crime Risk: " + result);
    }

    public static String requestFilePathname() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File filename = jfc.getSelectedFile();
            return filename.toString();
        }
        return "";
    }
}
