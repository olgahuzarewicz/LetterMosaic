import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LetterMosaicForwardCheck {
	public static void main(String[] args) throws InterruptedException {
			
			System.out.println("Enter size: ");
			Scanner s = new Scanner(System.in);
			int size = s.nextInt();
			
			LetterMosaicFC lm = new LetterMosaicFC(size);
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


class LetterMosaicFC{
	private String[][] board;
	private static final String UNASSIGNED = "0";
	ArrayList<String> letters = new ArrayList<String>();
	Map<Point, ArrayList<String>> allowedLetters = new HashMap<>();
	public Board cb;
	
	public LetterMosaicFC(int size)
	{
		board = new String[size][size];
		letters.add("x");
		letters.add("y");
	}

	public void display(int size) {
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public boolean solve(int size) throws InterruptedException {
		for(int row=0;row<size;row++){
			for(int col=0;col<size;col++){
				if(board[row][col]==UNASSIGNED){
					Point p = new Point(row, col);
					ArrayList<String> l = allowedLetters.get(p);
					if(!l.isEmpty()){						
						Random rand = new Random();
						int i = rand.nextInt(l.size());
						board[row][col]=l.get(i);
						
						Thread.sleep(80);
						cb.updateBoard(board);
	
						if(solve(size)){
							return true;
						}
						
						l.remove(board[row][col]);
						board[row][col] = UNASSIGNED;						
						fillNext(size, row, col);
					}
					else{
						return false;
					}
				}
				checkRequirements(row, col, size);
			}
		}
		return true;
	}

	private void checkRequirements(int row, int col, int size) {
		if((row>0 || col>0) && row<size && col<size-1){
			int r=0;
			int c=0;
			if(col==size){
				c=c+1;
			}
			else{
				c=col+1;
				r=row;
			}
			Point p = new Point(r, c);
			ArrayList<String> l = allowedLetters.get(p); 
			if(!l.isEmpty()){
				if(c>1 && board[r][c-2].equals(board[r][c-1])){
					l.remove(board[r][c-1]);		
				}				
				if(r>1 && board[r-2][c].equals(board[r-1][c])){
					l.remove(board[r-1][c]);		
				}
			}
		}
	}

	public void initialize(int size){
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				board[i][j]="0";
				
				Point p = new Point(i, j);
				ArrayList<String> al = new ArrayList<>();
				
				for(String l : letters) {
				    al.add(new String(l));
				}
				allowedLetters.put(p, al);
			}
		}
	}	
	
	private void fillNext(int size, int row, int col) {
		if((row>0 || col>0) && row<size && col<size-1){
			int r=0;
			int c=0;
			if(col==size){
				c=c+1;
			}
			else{
				c=col+1;
				r=row;
			}
			while (c<size){
				while (r<size){
					Point p = new Point(c, r);
					ArrayList<String> al = new ArrayList<>();
					
					for(String l : letters) {
					    al.add(new String(l));
					}
					allowedLetters.get(p).clear();
					allowedLetters.put(p, al);
					r++;
				}
				r=0;
				c++;
			}	
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