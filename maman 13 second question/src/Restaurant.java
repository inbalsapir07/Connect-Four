/**
 * The class Restaurant displaying the menu of the restaurant.
 * and allows to create an order.
 * Question 2, maman 13.
 * 
 * @author (Inbal Sapir)
 * @version (December 14, 2020)
 */
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;

public class Restaurant 
{
	/**
	 *  The main method of the the restaurant program.
	 *  The class Restaurant displaying the menu of the restaurant
     *  and allows to create an order.
	 */
	public static void main(String[] args) 
	{
		try 
		{
			JFrame frame= new JFrame ("Menu");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(900,800);
			Menu menu = new Menu ();
			frame.add(menu);
			frame.setVisible(true);
		}
		catch (IOException e) 
		{
			System.out.println("Error in reading the file");
		}
	}
}
