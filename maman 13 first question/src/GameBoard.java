import java.awt.*;
/**
 * The class GameBoard represents the game board.
 * 
 * @author (Inbal Sapir)
 * @version (December 11, 2020)
 */
import javax.swing.*;
public class GameBoard extends JPanel
{
	// variables
	private int[][] board; // the board of the game
	private int rows; // the number of rows in the board
	private int columns; // the number of columns in the board
	// in a case of a victory, the four following variables represent the values of the victory line that marks the 4 in a row discs.
	// if the board is full, value is -2. if the game continues, value is -1.
	private int lineX1; 
	private int lineY1;
	private int lineX2;
	private int lineY2;
	// constructor
	/**
	 * Getting a reference array of integers that represents
	 * the current state of the board in every turn. 
	 * Initializes the number of rows and columns of the Board.
	 * Initializes the value of the victory line mark to default values.
	 * @param board the reference representation of the board
	 */
	public GameBoard (int [][] board)
	{
		this.board= board;
		rows=board.length;
		columns=board[0].length;
		lineX1=-1;
		lineY1=-1;
		lineX2=-1;
		lineY2=-1;
		repaint();
	}
	// methods
	/**
	 *  Paints the grid of the board.
	 *  Paints the current state of the board.
	 *  If there is a winner, marks the four in a row discs with a victory line.
	 *  @param g
	 */
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		// painting the grid
		for (int i=0; i<=rows; i++)
			g.drawLine(0, height*i/rows, width, height*i/rows);
		for (int i=0; i<=columns; i++)
			g.drawLine(width*i/columns, 0, width*i/columns, height);
		// painting the discs in the board
		for (int i=0; i<rows; i++)
			for (int j=0; j<columns; j++)
			{
				if (board[i][j]==1)
				{
					g.setColor(Color.RED);
					g.fillOval (width*j/columns, height*i/rows, width/columns, height/rows);
				}
				if (board[i][j]==2)
				{
					g.setColor(Color.BLUE);
					g.fillOval (width*j/columns, height*i/rows, width/columns, height/rows);
				}
			}
		if (lineX1>=0) // if there is a winner, drawing a victory black line on the 4 in a row discs
		{
			Graphics2D g2 = (Graphics2D) g;
		    g2.setStroke(new BasicStroke(3));
		    g2.setColor(Color.BLACK);
			g.drawLine(((width*lineY1)/columns)+(width/(columns*2)), ((height*lineX1)/rows)+(height/(rows*2)),((width*lineY2)/columns)+(width/(columns*2)), ((height*lineX2)/rows)+(height/(rows*2)));
		}
					
	}
	/**
	 * Sets the x value of the first point of the victory line.
	 * @param x1  the x value of the first point of the victory line
	 */
	public void setLineX1 (int x1)
	{
		lineX1=x1;
	}
	/**
     * Sets the y value of the first point of the victory line.
	 * @param y1  the x value of the first point of the victory line
	 */
	public void setLineY1 (int y1)
	{
		lineY1=y1;
	}
	/**
     * Sets the x value of the second point of the victory line.
	 * @param x2  the x value of the second point of the victory line
	 */
	public void setLineX2 (int x2)
	{
		lineX2=x2;
	}
	/**
     * Sets the y value of the second point of the victory line.
	 * @param y2  the x value of the second point of the victory line
	 */
	public void setLineY2 (int y2)
	{
		lineY2=y2;
	}
	/**
	 * Gets the x value of the first point of the victory line.
	 * @return the x value of the first point of the victory line
	 */
	public int getLineX1 ()
	{
		return lineX1;
	}
	/**
	 * Gets the y value of the first point of the victory line.
	 * @return the y value of the first point of the victory line
	 */
	public int getLineY1 ()
	{
		return lineY1;
	}
	/**
	 * Gets the x value of the second point of the victory line.
	 * @return the x value of the second point of the victory line
	 */
	public int getLineX2 ()
	{
		return lineX2;
	}
	/**
	 * Gets the y value of the second point of the victory line.
	 * @return the y value of the second point of the victory line
	 */
	public int getLineY2 ()
	{
		return lineY2;
	}
}
