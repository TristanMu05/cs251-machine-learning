use std::fs::File;
use std::io::{BufRead, BufReader};

const DATA_PATH: &str = "src/crime_data.csv";

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let data = load_data(DATA_PATH)?;
    let clsfy = Classifier::new(data);

    let test1 = clsfy.predict(34.052, -118.243);
    println!("test 1 results: {}, this should be high", test1);

    let test2 = clsfy.predict(34.062, -118.251);
    println!("test 2 results: {}, this should be low", test2);

    Ok(())
}

fn load_data(path: &str) -> Result<Vec<IncidentDataPoints>, Box<dyn std::error::Error>> {
    let file = File::open(path)?;
    let reader = BufReader::new(file);
    let mut data: Vec<IncidentDataPoints> = Vec::new();

    for (line_idx, line_result) in reader.lines().enumerate().skip(1) {
        let line = match line_result {
            Ok(value) => value,
            Err(err) => {
                eprintln!("Skipping unreadable line {}: {}", line_idx + 1, err);
                continue;
            }
        };

        let parts: Vec<&str> = line.split(',').collect();
        if parts.len() != 4 {
            eprintln!("Skipping malformed line {}: {}", line_idx + 1, line);
            continue;
        }

        let lat = match parts[1].trim().parse::<f32>() {
            Ok(value) => value,
            Err(_) => {
                eprintln!("Skipping line {} with invalid latitude: {}", line_idx + 1, line);
                continue;
            }
        };

        let lon = match parts[2].trim().parse::<f32>() {
            Ok(value) => value,
            Err(_) => {
                eprintln!("Skipping line {} with invalid longitude: {}", line_idx + 1, line);
                continue;
            }
        };

        // Ignore impossible coordinates so outliers caused by data entry issues
        // do not dominate the class averages.
        if !(-90.0..=90.0).contains(&lat) || !(-180.0..=180.0).contains(&lon) {
            eprintln!("Skipping line {} with out-of-range coordinates: {}", line_idx + 1, line);
            continue;
        }

        let label = parts[3].trim();
        if label != "High" && label != "Low" {
            eprintln!("Skipping line {} with unknown label: {}", line_idx + 1, line);
            continue;
        }

        data.push(IncidentDataPoints::new(lat, lon, label.to_string()));
    }

    Ok(data)
}

struct Classifier {
    dataset: Vec<IncidentDataPoints>,
}

impl Classifier {
    fn new(dataset: Vec<IncidentDataPoints>) -> Self {
        Classifier { dataset }
    }

    fn predict(&self, test_lat: f32, test_lon: f32) -> String {
        let mut high_sum_lat = 0.0;
        let mut high_sum_lon = 0.0;
        let mut high_count = 0;
        let mut low_sum_lat = 0.0;
        let mut low_sum_lon = 0.0;
        let mut low_count = 0;

        for point in &self.dataset {
            match point.label.as_str() {
                "High" => {
                    high_sum_lat += point.lat;
                    high_sum_lon += point.lon;
                    high_count += 1;
                }
                "Low" => {
                    low_sum_lat += point.lat;
                    low_sum_lon += point.lon;
                    low_count += 1;
                }
                _ => {}
            }
        }

        if high_count == 0 || low_count == 0 {
            return "Unknown".to_string();
        }

        let high_avg_lat = high_sum_lat / high_count as f32;
        let high_avg_lon = high_sum_lon / high_count as f32;
        let low_avg_lat = low_sum_lat / low_count as f32;
        let low_avg_lon = low_sum_lon / low_count as f32;

        let high_centroid = IncidentDataPoints::new(high_avg_lat, high_avg_lon, "High".to_string());
        let low_centroid = IncidentDataPoints::new(low_avg_lat, low_avg_lon, "Low".to_string());

        let high_dist = high_centroid.distance_to(test_lat, test_lon);
        let low_dist = low_centroid.distance_to(test_lat, test_lon);

        if high_dist < low_dist {
            "High".to_string()
        } else {
            "Low".to_string()
        }
    }
}

#[derive(Debug)]
struct IncidentDataPoints {
    lat: f32,
    lon: f32,
    label: String,
}

impl IncidentDataPoints {
    fn new(lat: f32, lon: f32, label: String) -> Self {
        IncidentDataPoints { lat, lon, label }
    }

    fn distance_to(&self, target_lat: f32, target_lon: f32) -> f32 {
        let dif_lat = self.lat - target_lat;
        let dif_lon = self.lon - target_lon;
        (dif_lat * dif_lat + dif_lon * dif_lon).sqrt()
    }

}
