import java.util.Scanner;

public class ConnectFour {
	static int rows;
	static int columns;
	static char[][] board;
	
	static void setupBoard(int r, int c){
		board = new char[r][c];
		for(int i = 0; i < r; i++){
			for(int j = 0; j < c; j++){
				board[i][j] = ' ';
			}
		}
	}
	
	static void printBoard(char[][] board){
		for(int i = 0; i < rows; i++){
			System.out.print("| ");
			for(int j = 0; j < columns; j++){
				System.out.print(board[i][j] + "| ");
			}
			System.out.println();
		}
		for(int i = 0; i < columns; i++){
			System.out.print("---");
		}
		System.out.println();
	}
	
	static boolean putDisk(int column, boolean player){
		char c;
		column -= 1;
		if(board[0][column] != ' '){
			return false;
		}
		
		if(player){
			c = 'P';
		} else {
			c = 'c';
		}
		
		//Initial checks for all elements in the column
		int i;
		for(i = 0; i < rows; i++){
			if(board[i][column] != ' '){
				if(player){
					board[i-1][column] = 'P';
					return true;
				} else {
					board[i-1][column] = 'c';
					return true;
				}
			}
		}
		
		//If we've found no disks, then just place the disk at the bottom
		if(player){
			board[i-1][column] = 'P';
			return true;
		} else {
			board[i-1][column] = 'c';
			return true;
		}
	}
	
	public static char checkColumn(char[][] board){
		for(int j = 0; j < columns; j++){
			int count = 1;
			for(int i = 1; i < rows; i++){
				if(board[i][j] != ' ' && board[i][j] == board[i-1][j]){
					count++;
				} else {
					count = 1;
				}
				
				if(count >= 4){
					return board[i][j];
				}
			}
		}
		return ' ';
	}
	
	public static char checkRow(char[][] board){
		for(int i = 0; i < rows; i++){
			int count = 1;
			for(int j = 1; j < columns; j++){
				if(board[i][j] != ' ' && board[i][j] == board[i][j-1]){
					count++;
				} else {
					count = 1;
				}
				
				if(count >= 4){
					return board[i][j];
				}
			}
		}
		

		return ' ';
	}
	
	public static char checkDiagonals(char[][] board, boolean isPlayer){
		char player;
		
		if(isPlayer){
			player = 'P';
		} else {
			player = 'c';
		}
		
		//Checking upwards for diagonals 
	    for (int i=3; i <columns; i++){
	        for (int j=0; j< rows-3; j++){
	            if (board[i][j] == player && board[i-1][j+1] == player 
	            		&& board[i-2][j+2] == player && board[i-3][j+3] == player)
	                return player;
	        }
	    }
	    
	    //Checking downwards for diagonals
	    for (int i=3; i< columns; i++){
	        for (int j=3; j < rows; j++){
	            if (board[i][j] == player && board[i-1][j-1] == player 
	            		&& board[i-2][j-2] == player && board[i-3][j-3] == player)
	                return player;
	        }
	    }
        return ' ';
	}
	
	public static char checkWinner(char[][] board, boolean isPlayer){
		char winner = checkRow(board);
		if(winner != ' ')
			return winner;
		winner = checkColumn(board);
		if(winner != ' ')
			return winner;
		winner = checkDiagonals(board, isPlayer);
		if(winner != ' ')
			return winner;
		
		return ' ';
	
	}
	
	public static void play(){
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter width: ");
		int w = input.nextInt();
		while(w <= 4){
			System.out.println("Width must be a positive number and higher than 4!");
			System.out.print("Enter width: ");
			w = input.nextInt();
		}
		
		System.out.print("Enter height: ");
		int h = input.nextInt();
		while(h <= 4){
			System.out.println("Height must be a positive number and higher than 4!");
			System.out.print("Enter height: ");
			h = input.nextInt();
		}
		
		rows = w;
		columns = h;
		
		setupBoard(rows, columns);
		printBoard(board);

		boolean player = true;
		boolean isValid = false;
		
		while(true){
			if(player){
				System.out.println("Player turn:");
				System.out.print("Choose a column for your piece.");
				
				int turn = input.nextInt();
				if(turn < 1 || turn > columns){
					System.out.println("You must pick a valid column!");
					continue;
				}
				
				//Check for correct move
				//Skips entire loop if the column is full
				if(!putDisk(turn, player)){
					System.out.println("COLUMN IS FULL!");
					continue;
				}
				
				printBoard(board);
				
				char winner = checkWinner(board, player);
				if(winner != ' '){
					if(winner == 'P'){
						System.out.println("PLAYER WINS!");
						break;
					}
					if(winner == 'c'){
						System.out.println("COMPUTER WINS!");
						break;
					}
					if(winner == 'D'){
						System.out.println("IT IS A DRAW!");
						break;
					}
				}
				
				player = !player;
			} else {
				int randomMove = 1 + (int)(Math.random() * columns);
				
				while(!putDisk(randomMove, player)){
					randomMove = 1 + (int)(Math.random() * columns);
				}
				
				System.out.println("Computer placed disk at column: " + randomMove);
				
				printBoard(board);
				
				char winner = checkWinner(board, player);
				if(winner != ' '){
					if(winner == 'P'){
						System.out.println("PLAYER WINS!");
						break;
					}
					if(winner == 'c'){
						System.out.println("COMPUTER WINS!");
						break;
					}
					if(winner == 'D'){
						System.out.println("IT IS A DRAW!");
						break;
					}
				}
				
				player = !player;
			}
		}
	}

	public static void main(String[] args) {
		play();
		System.out.println("------------------------------");
		System.out.println("Game over! Thanks for playing!");
		System.out.println("------------------------------");
	}

}
