struct QAgent {
    q_table: HashMap<([u32;4], (usize, u32)), f64>,
    alpha: f64,
    gamma: f64,
    epsilon: f64,
}

fn legal_actions(&[u32; 4]) -> Vec<(usize, u32)> {
    for i in 0..4 {
        
    }
}