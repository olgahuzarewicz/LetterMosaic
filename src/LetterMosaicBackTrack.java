import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Inspired by
 * http://codepumpkin.com/sudoku-solver-using-backtracking/
 */

public class LetterMosaicBackTrack {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Enter size: ");
		Scanner s = new Scanner(System.in);

		int size = s.nextInt();
		
		LetterMosaicBT lm = new LetterMosaicBT(size);
		lm.initialize(size);
		lm.initializeGUI(size);
		
		Timer time = new Timer();
		time.start();
		if(!lm.solve(size)){
			System.out.println("Unsuccessful");
		}
		s.close();
		time.stop();
		lm.display(size);
		
		time.show();
	}
}

class LetterMosaicBT{
	private String[][] board;
	private static final String UNASSIGNED = "0";
	ArrayList<String> letters = new ArrayList<String>();
	public Board cb;
	
	public LetterMosaicBT(int size)	{
		board = new String[size][size];
		letters.add("x");
		letters.add("y");
	}
	
	public LetterMosaicBT(String board[][])	{
		this.board= board;
	}
	
	public boolean solve(int size) throws InterruptedException{
		for(int row=0;row<size;row++){
			for(int col=0;col<size;col++){
				
				if(board[row][col]==UNASSIGNED){
					
					for(String l:letters){
						
						if(isAllowed(row, col, l, size)){
							board[row][col] = l;
							
							Thread.sleep(80);
							cb.updateBoard(board);
							
							if(solve(size)){
								
								return true;
							}
							else{
								board[row][col] = UNASSIGNED;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean allowedInRow(int row, int col, String letter, int size){
		if(row>=2){
			if(board[row-2][col].equals(letter) && board[row-1][col].equals(letter)){
				return false;
			}
		}
		return true;
	}
	
	private boolean allowedInCol(int row, int col, String letter, int size){
		if(col>=2){
			if(board[row][col-2].equals(letter) && board[row][col-1].equals(letter)){
				return false;
			}
		}
		return true;
	}
	
	private boolean repeatedRow(int row, int col, int size)	{
		int counter=0;
		if(row>0 && col==(size-1)){
			for(int r=0;r<row;r++){ 
				for(int c=0;c<size;c++){
					if(board[r][c].equals(board[row][c])){
						counter++;
					}
				}
			if(counter==col){
				return false;
			}
			counter=0;
			}
		}
		return true;
	}
	
	private boolean repeatedColumn(int row, int col, int size)	{
		int counter=0;
		if(row==(size-1) && col>1){
			for(int c=0;c<col;c++){
				for(int r=0;r<size;r++){ 
					if(board[r][c].equals(board[r][col])){
						counter++;
					}
				}
			if(counter==col){
				return false;
			}
			counter=0;
			}
		}
		return true;
	}
	
	private boolean isAllowed(int row, int col, String l, int size)	{
		return (allowedInRow(row, col, l, size) && allowedInCol(row, col, l, size) && repeatedRow(row, col, size) && repeatedColumn(row, col, size));
	}
	
	public void initialize(int size){
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				board[i][j]="0";
			}
		}
	}
	
	public void display(int size){
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void initializeGUI(int size) {
		Runnable r = new Runnable() {
	        @Override
	        public void run() {
            	Board.n=size;
            	cb = new Board(size);
                JFrame f = new JFrame("Letter Mosaic");
                f.setVisible(false);
                f.getContentPane().add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
	        }
		};
	SwingUtilities.invokeLater(r);	
	}
}