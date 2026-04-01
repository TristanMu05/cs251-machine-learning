struct StateActionPair {
    mut piles: [u32;4],
    mut pileID: u32,
    mut count: u32,
}

impl StateActionPair {
    fn new() -> &Self {
        StateActionPair {
            mut piles: [u32;4],
            mut pileID: u32,
            mut count: u32,
        }
    }

    fn equals(&self) -> bool {
        
    }
}