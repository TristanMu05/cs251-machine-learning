#[derive(Debug, Clone)]
struct DataPoint {
    attributes: Vec<f32>,
    label: i32,
}

impl DataPoint {
    fn new(attributes: Vec<f32>, label: i32) -> Self {
        Self { attributes, label }
    }
}

#[derive(Debug)]
struct Perceptron {
    weights: Vec<f32>,
    bias: f32,
    learning_rate: f32,
}

impl Perceptron {
    fn new(n_attributes: usize, learning_rate: f32) -> Self {
        Self {
            weights: vec![0.0; n_attributes],
            bias: 0.0,
            learning_rate,
        }
    }

    fn predict(&self, input: &[f32]) -> i32 {
        assert_eq!(
            input.len(),
            self.weights.len(),
            "input length must match number of weights"
        );

        let activation = self
            .weights
            .iter()
            .zip(input.iter())
            .fold(self.bias, |acc, (weight, value)| acc + weight * value);

        if activation >= 0.0 { 1 } else { -1 }
    }

    fn train(&mut self, data_points: &[DataPoint], max_iterations: usize) -> usize {
        for epoch in 0..max_iterations {
            let mut any_update = false;

            for point in data_points {
                let prediction = self.predict(&point.attributes);
                if prediction != point.label {
                    for (weight, feature) in self.weights.iter_mut().zip(point.attributes.iter()) {
                        *weight += self.learning_rate * point.label as f32 * feature;
                    }
                    self.bias += self.learning_rate * point.label as f32;
                    any_update = true;
                }
            }

            if !any_update {
                return epoch + 1;
            }
        }

        max_iterations
    }
}

fn label_to_text(label: i32) -> &'static str {
    if label == 1 { "spam" } else { "not spam" }
}

fn main() {
    let dataset = vec![
        DataPoint::new(vec![3.0, 2.0], 1),  // likely spam
        DataPoint::new(vec![0.0, 1.0], -1), // likely not spam
        DataPoint::new(vec![1.0, 0.0], -1), // likely not spam
        DataPoint::new(vec![2.0, 3.0], 1),  // likely spam
    ];

    let n_attributes = 2;
    let learning_rate = 0.1;
    let max_iterations = 100;

    let mut perceptron = Perceptron::new(n_attributes, learning_rate);
    let epochs_used = perceptron.train(&dataset, max_iterations);

    println!("Training finished in {epochs_used} epoch(s)");
    println!("Weights: {:?}, Bias: {}", perceptron.weights, perceptron.bias);

    let samples = vec![
        vec![2.5, 2.0],
        vec![0.2, 0.8],
        vec![1.8, 2.6],
        vec![0.4, 0.3],
    ];

    for sample in samples {
        let prediction = perceptron.predict(&sample);
        println!(
            "Email features {:?} => {}",
            sample,
            label_to_text(prediction)
        );
    }
}

#[cfg(test)]
mod tests {
    use super::{DataPoint, Perceptron};

    #[test]
    fn perceptron_learns_simple_dataset() {
        let data = vec![
            DataPoint::new(vec![3.0, 2.0], 1),
            DataPoint::new(vec![0.0, 1.0], -1),
            DataPoint::new(vec![1.0, 0.0], -1),
            DataPoint::new(vec![2.0, 3.0], 1),
        ];

        let mut perceptron = Perceptron::new(2, 0.1);
        perceptron.train(&data, 100);

        for point in data {
            assert_eq!(perceptron.predict(&point.attributes), point.label);
        }
    }
}
