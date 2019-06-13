import java.util.Random;
/**
 * Eight Queens Problem - Using Climbing hill method
 * ITCS 3153 - Programming Assignment 1
 * @author Tarun Ravada
 *
 */
public class NQueens {

	/*
	 * NOTES ABOUT BOARD
	 * Board is stored as a 1D array, where the index of the array represents the column number
	 * and the value at the index represents the row number of the queen.
	 * (x,y) coordinates of queen would be (i,arr[i])
	 */
	
	// current state of the board
	public static int current_board[];
	// current heuristic value
	public static int current_h;
	// board size (update this for different board size
	public static int board_size = 8;

	/**
	 * Main method, simply calls the run method
	 * @param args command line args
	 */
	public static void main(String[] args) {
		run();
	}

	
	/**
	 * Runs the game through state changes and restarts
	 * Prints current state and current h value after every state change
	 * Keeps track of no of restarts and state changes 
	 */
	public static void run() {
		
		int lower_h,state_changes=0,restarts = 0;
		
		// repeat until solution is found
		while(true) {
			// init board upon restart or start
			init_board();
			do{
				if(current_h == 0) {
					System.out.println("Current State");
					print_board();
					System.out.println("Solution Found!");
					System.out.println("State Changes: "+state_changes);
					System.out.println("Restarts: "+restarts);
					return;
				}
				
				System.out.println("Current h: "+current_h);
				System.out.println("Current State");
				print_board();
				
				// see if any better states were found
				lower_h = next_state();
				System.out.println("Neighbours found with lower h: "+lower_h);
				if(lower_h>0)
					System.out.println("Setting new state\n");
				else
					System.out.println("RESTART\n");
				
				state_changes++;
			
			}while(lower_h>0); // if better states were found repeat else restart
			
			restarts++;
		}
	}

	
	/**
	 * Initialized the game board by placing queens at random locations
	 * Size of the game board is determined by class const board_size
	 */
	public static void init_board() {
		current_board = new int[board_size];
		Random rand = new Random();
		for(int i = 0; i<current_board.length; i++) {
			current_board[i] = rand.nextInt(board_size);
		}

		current_h = get_h(current_board);

	}

	
	/**
	 * Prints current state of the game board to console
	 */
	public static void print_board() {
		for(int j = 0;j<board_size;j++) {
			for(int i = 0; i<board_size; i++) {
				if(current_board[i] == j)
					System.out.print("1");
				else 
					System.out.print("0");
				if(i!=board_size-1)
					System.out.print(",");
			}
			System.out.println();
		}
	}

	/**
	 * Calculates the heuristic value of the passed in board state
	 * @param board_state state whose h value is calculated
	 * @return h value
	 */
	public static int get_h(int board_state[]) {
		int h=0;
		for( int i = 0; i<board_size; i++) {
			for(int j = i+1; j<board_size;j++) {

				// two queens in the same column
				if(board_state[i] == board_state[j])
					h++;
				
				// checks if two queens are on diagonal of an imaginary square
				if(Math.abs(board_state[i]-board_state[j]) == Math.abs(i-j))
					h++;
			}
		}
		return h;
	}

	/**
	 * Finds the next best board state that will results in the lowest h value
	 * If no state is found board will maintain current state
	 * @return returns number of lower h value states found
	 */
	public static int next_state() {
		
		int best_state[] = current_board.clone();
		int possible_state[];
		int best_h = current_h;
		
		// no of neighbors found with lower h than current state
		int lower_h=0;

		for(int i = 0; i<board_size;i++) {
			// place here to reset possible_state before moving to next column
			possible_state = current_board.clone(); 

			// iterate through every possible position for the queen in this column
			for(int j = 0; j < board_size; j++) {
				
				// set location of queen
				possible_state[i] = j;
				
				int h = get_h(possible_state);
				
				// if returned h value is lower we found a better state
				if(h < current_h)
					lower_h++;
				
				// if returned h value is better than current h value, update state and h value
				if(h < best_h) {
					best_state = possible_state.clone(); 
					best_h = h;
				}
			}
		}
		
		// once done checking for all columns update current_board with best value found
		// if no best value is found then current_board will be updated to be current_board
		current_board = best_state.clone();
		current_h = best_h;
		return lower_h;
	}
}
