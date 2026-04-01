struct State {
    state: [u32; 4],
}
impl State{
    fn new() -> Self {
        State{
           state: [1,3,5,7],
        }
    }

    fn act_on_state(&mut self, pile: u32, amnt: u32) -> bool {
        let mut pile_cnt = self.get_pile_count(pile);
        if amnt > pile_cnt { return false; }
        self.state[pile as usize] = pile_cnt - amnt;
        true
    }

    fn get_pile_count(&self, pile: u32) -> u32 {
        self.state[pile as usize]
    }

    fn is_win_state(&self,) -> bool {
        for i in 0..self.state.len() {
            if self.state[i] > 0 { return false; }
        }
        true
    }

    fn to_string(&self,) -> String {
        format!("The current state is: {:?}", self.state).to_string()
    }
}

fn main() {
    let mut state: State = State::new();

    let mut user_pile: u32;
    let mut user_amnt: u32;
    let mut turn = 0;

    loop {
        // TAKE USER INPUT FOR THEIR TURN AND VALIDATE THEIR INPUT
        user_pile = loop {
            println!("User {}, which pile do you choose? \nPiles: [0,1,2,3]\nAmnts: [{},{},{},{}]", if turn == 0 {1} else {2}, state.state[0],state.state[1],state.state[2],state.state[3]);
            let val = user_input();
            if val <= 3 && state.state[val as usize] != 0 {
                break val;
            }
            println!("Please enter a valid non empty pile number between 0 and 3.");
        };

        user_amnt = loop {
            let max = state.get_pile_count(user_pile);
            println!("User {}, how many stones would you like to take? (0-{})", if turn == 0 {1} else {2}, max);
            let val = user_input();
            if val <= max {
                break val;
            }
            println!("Please enter a number between 0 and {}.", max);
        };

        // UPDATE THE GAME STATE
        let result = state.act_on_state(user_pile, user_amnt);
        if result == false { println!("I did something wrong"); }
        let win = state.is_win_state();
        if win == true { println!("Player {}, WINS!!!", if turn == 0 {1} else {2}); break; }
        turn = (turn + 1) % 2;
    }

}

fn user_input() -> u32 {
    use std::io::{self, Write};

    loop {
        print!("> ");
        io::stdout().flush().expect("Failed to flush stdout");

        let mut buf = String::new();
        if io::stdin().read_line(&mut buf).is_err() {
            println!("Failed to read input. Please try again.");
            continue;
        }

        let trimmed = buf.trim();
        match trimmed.parse::<u32>() {
            Ok(v) => return v,
            Err(_) => {
                println!("Please enter a valid non-negative whole number (u32).");
            }
        }
    }
}
