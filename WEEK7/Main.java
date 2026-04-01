import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Main {
    public static void main(String[] args) {
        // TASK 1: ALLOW THE USER TO SPECIFY THE FILE: "crime_data.csv"
        String filePath = requestFilePathname();
        // TASK : READ THE DATA FROM THE CSV
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // SKIP THE HEADER ROW
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int visits = Integer.parseInt(values[1]);
                int avgSpend = Integer.parseInt(values[2]);
                System.out.println("Visits: " + visits + "    Average Spending: " + avgSpend);
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }
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