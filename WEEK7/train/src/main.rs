fn main() {
    // Build a small BN: Rain -> Maintenance -> Train -> Lecture (with Rain also influencing Train & Lecture)
    // Node ordering: 0=Rain, 1=Maintenance, 2=Train, 3=Lecture

    let rain = Node {
        name: "Rain",
        n_states: 3, // 0=None, 1=Light, 2=Heavy
        parents: vec![],
        cpt: vec![0.7, 0.2, 0.1], // prior
    };

    // Maintenance depends on Rain: states 0=NotInProgress, 1=InProgress
    // CPT layout: for each Rain state (0..2) provide probabilities for Maintenance states [P(0), P(1)]
    let maintenance = Node {
        name: "Maintenance",
        n_states: 2,
        parents: vec![0],
        cpt: vec![
            0.9, 0.1, // Rain=None
            0.5, 0.5, // Rain=Light
            1.0, 0.0, // Rain=Heavy -> maintenance not in progress
        ],
    };

    // Train depends on Maintenance (index 1) and Rain (index 0).
    // States: 0=OnTime, 1=Late
    // CPT ordering: maintenance (slow), rain (fast). For each parent assignment provide [P(on_time), P(late)]
    let train = Node {
        name: "Train",
        n_states: 2,
        parents: vec![1, 0],
        cpt: vec![
            0.9, 0.1, // maint=0, rain=0
            0.8, 0.2, // maint=0, rain=1
            0.4, 0.6, // maint=0, rain=2 (heavy)
            0.0, 1.0, // maint=1, rain=0
            0.0, 1.0, // maint=1, rain=1
            0.0, 1.0, // maint=1, rain=2
        ],
    };

    // Lecture depends on Train (index 2) and Rain (index 0).
    // States: 0=MadeIt, 1=Missed
    // Rule (deterministic in CPT): if train is late -> missed. If train on time and rain heavy -> missed. Otherwise made it.
    let lecture = Node {
        name: "Lecture",
        n_states: 2,
        parents: vec![2, 0],
        cpt: vec![
            1.0, 0.0, // train=0(on time), rain=0 -> made
            1.0, 0.0, // train=0, rain=1 -> made
            0.0, 1.0, // train=0, rain=2 -> missed
            0.0, 1.0, // train=1(late), rain=0 -> missed
            0.0, 1.0, // train=1, rain=1 -> missed
            0.0, 1.0, // train=1, rain=2 -> missed
        ],
    };

    let network = vec![rain, maintenance, train, lecture];

    // No evidence: compute P(Lecture = MadeIt)
    let n_vars = network.len();
    let evidence: Vec<Option<usize>> = vec![None; n_vars];

    let p_made = query_prob(&network, 3, 0, &evidence);
    println!("P(Lecture=Made) = {:.5}", p_made);

    // Example conditional: P(Lecture=Made | Train=OnTime)
    let mut evidence2 = evidence.clone();
    evidence2[2] = Some(0); // Train = OnTime
    let p_made_given_on_time = query_prob(&network, 3, 0, &evidence2);
    println!("P(Lecture=Made | Train=OnTime) = {:.5}", p_made_given_on_time);
}

struct Node {
    name: &'static str,
    n_states: usize,
    parents: Vec<usize>, // indices into network Vec
    cpt: Vec<f64>,       // length = product(parent_states) * n_states
}

// Compute CPT index given a full assignment.
fn cpt_index(node: &Node, network: &[Node], node_index: usize, assignment: &[usize]) -> usize {
    // parent_index is mixed-radix where the last parent in the `parents` vec changes fastest
    let mut parent_index = 0usize;
    let mut multiplier = 1usize;
    for &p in node.parents.iter().rev() {
        parent_index += assignment[p] * multiplier;
        multiplier *= network[p].n_states;
    }
    parent_index * node.n_states + assignment[node_index]
}

fn get_prob(node_index: usize, network: &[Node], assignment: &[usize]) -> f64 {
    let node = &network[node_index];
    let idx = cpt_index(node, network, node_index, assignment);
    node.cpt[idx]
}

fn joint_probability(network: &[Node], assignment: &[usize]) -> f64 {
    let mut p = 1.0f64;
    for i in 0..network.len() {
        p *= get_prob(i, network, assignment);
    }
    p
}

fn sum_over(network: &[Node], evidence: &Vec<Option<usize>>) -> f64 {
    let n = network.len();
    let mut assignment = vec![0usize; n];
    fn recurse(network: &[Node], evidence: &Vec<Option<usize>>, idx: usize, assignment: &mut [usize], acc: &mut f64) {
        if idx == network.len() {
            *acc += joint_probability(network, assignment);
            return;
        }
        if let Some(v) = evidence[idx] {
            assignment[idx] = v;
            recurse(network, evidence, idx + 1, assignment, acc);
        } else {
            for val in 0..network[idx].n_states {
                assignment[idx] = val;
                recurse(network, evidence, idx + 1, assignment, acc);
            }
        }
    }
    let mut sum = 0.0f64;
    recurse(network, evidence, 0, &mut assignment, &mut sum);
    sum
}

fn query_prob(network: &[Node], query_var: usize, query_value: usize, evidence: &Vec<Option<usize>>) -> f64 {
    let mut ev_with_query = evidence.clone();
    ev_with_query[query_var] = Some(query_value);
    let num = sum_over(network, &ev_with_query);
    let den = sum_over(network, evidence);
    if den == 0.0 {
        0.0
    } else {
        num / den
    }
}


P(LR,NT,DT) = P(NT|LR)P(LR)P(DT|NT)