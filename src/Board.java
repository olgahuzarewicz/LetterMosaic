import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.EventListener;

import javax.swing.*;
import javax.swing.border.*;

public class Board{

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	public static int n;
    private JButton[][] boardSquares = new JButton[n][n];
    private JPanel board;

    Board(int i) {
    	n=i;
        initializeGui(n);
    }

    public final void initializeGui(int n) {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);

        board = new JPanel(new GridLayout(0, n));
        board.setBorder(new LineBorder(Color.BLACK));
        gui.add(board);

        // create the board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < boardSquares.length; ii++) {
            for (int jj = 0; jj < boardSquares.length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                b.setPreferredSize(new Dimension(40, 40));
                b.setBackground(Color.WHITE);
                boardSquares[ii][jj] = b;
                
            }
        }

        // fill the black non-pawn piece row
        for (int ii = 0; ii < n; ii++) {
            for (int jj = 0; jj < n; jj++) {
            	board.add(boardSquares[ii][jj]);
            }
        }
    }

    public final JComponent getChessBoard() {
        return board;
    }

    public final JComponent getGui() {
        return gui;
    }

    public void updateBoard(String[][] values){
    	for (int ii = 0; ii < n; ii++) {
            for (int jj = 0; jj < n; jj++) {
            	boardSquares[ii][jj].setText(values[ii][jj]);
            }
        }
    }
}