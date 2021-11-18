import javax.swing.JFrame;
/**
 * The class ConnectFour representd the game with the same name.
 * Question 1, maman 13.
 * 
 * @author (Inbal Sapir)
 * @version (December 11, 2020)
 */
public class ConnectFour
{
	/**
	 * The main method of the game connect four.
	 * Each user have discs from a certain color.
	 * Every user drops a disc to the standing board in his turn, aiming to compose 
	 * an horizontal, vertical or diagonal line composed of his discs.
	 * The game is over when a user wins the game or when the board if full.
	 */
	public static void main(String[] args) 
	{
		JFrame frame= new JFrame ("Game Board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		Game game = new Game ();
		frame.add(game);
		frame.setVisible(true);
	}
}
