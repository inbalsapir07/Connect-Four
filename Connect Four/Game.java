/**
 * The class Game represents the panel displayed to the users.
 * The class Game calculates and displays the updated panel in every turn.
 * 
 * @author (Inbal Sapir)
 * @version (December 11, 2020)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Game extends JPanel //class name
{
	// variables
	private final int ROWS= 6; // the number of rows in the board
	private final int COLUMNS=7; // the number of columns in the board
	private JButton[] buttons; // the columns' buttons
	private int[][] board; // the board of the game. 0 for empty cell, 1 for first rival's disc, 2 for second rival's disc.
	private boolean firstRivalTurn; // true if it's first rival's turn, false otherwise
	private JLabel lblText; // displaying whose turn it is currently
	private JButton clear; // clear button
	private JPanel south; // where the columns' buttons are located
	private GameBoard center; // where the board of the game is located
	private JPanel north; // where current turn label and clear button are located
	// constructor
	/**
	 * An empty constructor. Constructs a new Game by creating a north
	 * panel for a label that shows whose turn it is currently and for a clear button,
	 * a center panel for the game board, and a south panel for the columns buttons.
	 * The user with the red discs is first to play.
	 * Board is initialized to be clean of discs.
	 */
	public Game () 
	{
		// columns buttons panel
		buttons=new JButton[COLUMNS]; 
		Listener listener =new Listener();
		for (int i=0; i<buttons.length; i++) // initializing all the columns' buttons
		{
			buttons[i]=new JButton (""+(i+1));
			buttons[i].addMouseListener(listener);
		}
		south = new JPanel ();
		south.setLayout(new GridLayout (1,COLUMNS));
		for (int i=0; i<buttons.length; i++)
			south.add(buttons[i]);
		// game board panel
		board=new int [ROWS][COLUMNS];
		for (int i=0; i<board.length; i++) // the board is empty in the beginning of the game
			for (int j=0; j<board[0].length; j++)
				board [i][j] = 0;
		center = new GameBoard (board); // painting the board
		// current turn label and clear button panel
		lblText= new JLabel ("Red's turn to play");
		lblText.setForeground(Color.RED);
		clear= new JButton("clear");
		clear.addMouseListener(listener);
		north= new JPanel ();
		north.add(lblText);
		north.add(clear);
		// setting panels
		setLayout (new BorderLayout());
		add (center, BorderLayout.CENTER);
		add (south, BorderLayout.SOUTH);
		add (north, BorderLayout.NORTH);
		firstRivalTurn=true;
	}
	// methods
	/**
	 * The class Listener handles relevant mouseClicked events.
	 */
	private class Listener extends MouseAdapter
	{
		/**
		 * Handles the event which was invoked by clicking on a column's button or the clear button.
		 * if user clicked on column's button, a disc of his color is dropped to the column, 
		 * as long as the column is not full. The method also checks if game is over in every turn.
		 * if user clicked on clear button, clears the board.
		 * @param e the event
		 */
		public void mouseClicked (MouseEvent e)
		{
			if (((JButton)e.getSource()).getText()==("clear")) // if clear button was clicked
				clear();
			else // if one of the columns' buttons was clicked
			{
				int buttonPressed= Integer.parseInt(((JButton)e.getSource()).getText()); // the number of the column that was clicked
				int i=ROWS-1;
				int j=buttonPressed-1;
				if (board [0][j]!=0) // if the column that was clicked is full
					JOptionPane.showMessageDialog(null, "This column is full; Try another column", "Error", JOptionPane.ERROR_MESSAGE);
				else // if the column that was clicked is not full
				{
					while (board [i][j] != 0) // setting i to the lowest row without a disc in the column
						i--;
					if (firstRivalTurn) // entering red disc to the column
						board[i][j]=1;
					else // entering blue disc to the column
						board[i][j]=2;
					isGameOver(i, j); // calculating whether the game is over or not
					center.repaint(); // painting the new board
					if (center.getLineX1()!=-1) // the game is over
					{
						if (center.getLineX1()>=0) // if there is a winner
						{
							String winner= firstRivalTurn ? "Red" : "Blue";
							JOptionPane.showMessageDialog(null, "Congratulations! "+winner+" won the game");
						}
						if (center.getLineX1()==-2) // if there is no winner and the board is full
							JOptionPane.showMessageDialog(null, "The board is full");
						int playAgain= JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
						if (playAgain == 0) // play again
							clear();
						else // end game
							System.exit(0);
					}
					else // if the game continues
					{
						firstRivalTurn=!firstRivalTurn; // switching turns
						if (firstRivalTurn==true)
						{
							lblText.setText("Red's turn to play");
							lblText.setForeground(Color.RED);
						}
						else
						{
							lblText.setText("Blue's turn to play");
							lblText.setForeground(Color.BLUE);
						}
					}
				}
			}
		}
	}
	/**
	 * Clears the board off discs and Stars a new game.
	 */
	public void clear ()
	{
		for (int i=0; i<board.length; i++)
			for (int j=0; j<board[0].length; j++)
				board [i][j] = 0;
		lblText.setText("Red's turn to play");
		lblText.setForeground(Color.RED);
		firstRivalTurn=true;
		center = new GameBoard (board);
		add (center, BorderLayout.CENTER);
	}
	/**
	 * Get the row and column of the cell where a disc was dropped into in the last turn.
	 * Checks whether the last disc that was dropped creates a
	 * horizontal, vertical ore diagonal four in a row line.
	 * If not, checks if the board is full.
	 * @param i the row of the cell where a disc was dropped into in the last turn.
	 * @param j the column of the cell where a disc was dropped into in the last turn.
	 */
	public void isGameOver(int i, int j)
	{
		int k= firstRivalTurn? 1: 2; // if k=1, checking a possible victory of first rival; if k=2, checking a possible victory of second rival
		for (int n=0; n<4; n++) // looking for vertical 4 in a row
		{
			try 
			{
				if (board[i-3][j]==k && board[i-2][j]==k && board[i-1][j]==k && board[i][j]==k) // if there is a vertical 4 in a row
				{
					// setting the values of the victory line that marks the 4 in a row discs
					center.setLineX1(i-3);
					center.setLineY1(j);
					center.setLineX2(i);
					center.setLineY2(j);
					return;
				}
				else
					i++;
			}
			catch(IndexOutOfBoundsException e)
			{
				i++;
			}
		}
		i=i-4; // setting i to its value in the beginning of the method
		for (int n=0; n<4; n++) // looking for horizontal 4 in a row
		{
			try 
			{
				if (board[i][j-3]==k && board[i][j-2]==k && board[i][j-1]==k && board[i][j]==k) // if there is a horizontal 4 in a row
				{
					// setting the values of the victory line that marks the 4 in a row discs
					center.setLineX1(i);
					center.setLineY1(j-3);
					center.setLineX2(i);
					center.setLineY2(j);
					return;
				}
				else
					j++;
			}
			catch(IndexOutOfBoundsException e)
			{
				j++;
			}
		}
		j=j-4; // setting j to its value in the beginning of the method
		for (int n=0; n<4; n++) // looking for diagonal 4 in a row (down left to up right)
		{
			try 
			{
				if (board[i-3][j+3]==k && board[i-2][j+2]==k && board[i-1][j+1]==k && board[i][j]==k) // if there is a diagonal 4 in a row
				{
					// setting the values of the victory line that marks the 4 in a row discs
					center.setLineX1(i-3);
					center.setLineY1(j+3);
					center.setLineX2(i);
					center.setLineY2(j);
					return;
				}
				else
				{
					i++;
					j--;
				}
			}
			catch(IndexOutOfBoundsException e)
			{
				i++;
				j--;
			}
		}
		i=i-4; // setting i to its value in the beginning of the method
		j=j+4; // setting j to its value in the beginning of the method
		for (int n=0; n<4; n++) // looking for diagonal 4 in a row (up left to down right)
		{
			try 
			{
				if (board[i-3][j-3]==k && board[i-2][j-2]==k && board[i-1][j-1]==k && board[i][j]==k) // if there is a diagonal 4 in a row
				{
					// setting the values of the victory line that marks the 4 in a row discs
					center.setLineX1(i-3);
					center.setLineY1(j-3);
					center.setLineX2(i);
					center.setLineY2(j);
					return;
				}
				else
				{
					i++;
					j++;
				}
			}
			catch(IndexOutOfBoundsException e)
			{
				i++;
				j++;
			}
		}
		int n=0; // assistant integer to check whether the board is full
		while (board[0][n]!=0 && n<7)
		{
			n++;
			if (n==7) // if board is full
			{
				center.setLineX1(-2);
				break;
			}
		}
	}
}