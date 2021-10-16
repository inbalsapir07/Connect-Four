/**
 * The class Menu represents a the menu of the restaurant
 * 
 * @author (Inbal Sapir)
 * @version (December 14, 2020)
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Menu extends JPanel
{
	// variables 
	private Scanner input = new Scanner (new File ("menu.txt"));
	private Formatter output;
	private ArrayList <Item> items; // the list of the items in the menu
	private JLabel drinksLabel; // drinks title
	private JLabel appetizerLabel; // appetizer title
	private JLabel mainCourseLabel; // main course title
	private JLabel dessertLabel; // dessert title
	private JLabel [] typesLabels;  // titles for each type
	private int [] types; // number of items from each type. (type[0] is the number of drinks items, etc) 
	private int numberOfTypes=4; // number of item types
	private final String [] TITLES= { "Dish", "Price", "Amount"}; // the names of the titles in the items chart
	private JLabel[][] titles; // the titles in the items chart
	private JCheckBox[] checkBoxes;
	private JComboBox[] comboBoxes;
	private final String[] AMOUNT= {"1","2","3","4","5","6","7","8","9","10"}; // the amount of dishes each user can order from one item
	private JButton orderButton; // order button
	private JPanel [] panels; // panels of the menu
	private int numberOfPanels=numberOfTypes*2+1; // each item type has two panels, plus order button has one panel 
	private Order order; // the user's order
	// constructor
	/**
	 * An empty constructor. Constructs a new Menu object 
	 * which is composed of different panels that represent the different categories' titles,
	 * charts that shows details about the items and allows the user to choose from them,
	 * and an order button at the bottom of the menu.
	 * @throws IOException
	 */
	public Menu () throws IOException
	{
		// reading the file
		items= new ArrayList <Item>(); 
		while (input.hasNext())
		{
			String description=input.nextLine();
			String type=input.nextLine();
			double price=Double.parseDouble(input.nextLine());
			items.add(new Item(description, type, price));
		}
		input.close();
		// creating the panels of the menu
		panels = new JPanel [numberOfPanels];
		for (int i=0; i<panels.length; i++)
			panels[i]=new JPanel();
		// creating titles for each type
		drinksLabel= new JLabel ("Drinks");; 
		appetizerLabel= new JLabel ("Appetizer");; 
		mainCourseLabel= new JLabel ("Main Course");;
		dessertLabel= new JLabel ("Dessert");;
		typesLabels= new JLabel [numberOfTypes];
		typesLabels[0]= drinksLabel;
		typesLabels[1]= appetizerLabel;
		typesLabels[2]= mainCourseLabel;
		typesLabels[3]= dessertLabel;
		for (int i=0; i<numberOfTypes; i++)
		{
			typesLabels[i].setFont(new Font ("Arial", Font.BOLD, 24));
			typesLabels[i].setForeground(new Color (100,50,200));
		}
		for (int i=0; i<numberOfTypes; i++) 
			panels[i*2].add(typesLabels[i]);
		// creating items charts for each type
		types= new int [numberOfTypes];
		for (int i=0; i<numberOfTypes; i++) // initializing types array
			types[i]=0;
		for (int i=0; i<items.size(); i++) // counting how many items exist in each type
			for (int j=0; j<numberOfTypes; j++)
				if (items.get(i).getType().equals(typesLabels[j].getText()))
					types[j]++;
		titles= new JLabel [numberOfTypes][TITLES.length];
		for (int i=0; i<numberOfTypes; i++) // setting the "dish", "price", "amount" titles in the top of every panel that presents items
		{
			panels[i*2+1].setLayout(new GridLayout(types[i]+1,TITLES.length));
			for (int j=0; j<TITLES.length; j++)
			{
				titles[i][j]= new JLabel (TITLES[j]);
				titles[i][j].setFont(new Font ("Arial", Font.ITALIC, 16));
				panels[i*2+1].add(titles[i][j]);
			}
		}
		checkBoxes=new JCheckBox[items.size()];
		comboBoxes=new JComboBox[items.size()];
		Listener listener =new Listener();
		for (int i=0; i<items.size(); i++) // adding items to the table
		{
			checkBoxes[i]=new JCheckBox(items.get(i).getDescription());
			checkBoxes[i].addMouseListener(listener);
			comboBoxes[i]= new JComboBox (AMOUNT);
			comboBoxes[i].setEnabled(false);
			comboBoxes[i].setMaximumRowCount(5);
			comboBoxes[i].addActionListener(new ComboListenerer());
			for (int j=0; j<numberOfTypes; j++)
				if (items.get(i).getType().equals(typesLabels[j].getText()))
				{
					panels[j*2+1].add(checkBoxes[i]);
					panels[j*2+1].add(new JLabel (""+items.get(i).getPrice()));
					panels[j*2+1].add(comboBoxes[i]);
				}
		}
		// adding order button
		orderButton = new JButton ("Order");
		orderButton.addMouseListener(listener);
		panels[numberOfPanels-1].add(orderButton);
		// adding the panels to the menu panel
		setLayout (new GridLayout(numberOfPanels,1));
		for (int i=0; i<panels.length; i++)
			add (panels[i]);
		// creating new order
		order=new Order(items);
		JOptionPane.showMessageDialog(null, "Create Your Order");
	}
	// methods
	/**
	 * The class Listener handles relevant mouseClicked events.
	 */
	private class Listener extends MouseAdapter
	{
		/**
		 * Handles the event which was invoked by user clicking on a check-box or the order button.
		 * If user selected a dish, enables the possibility to update the amount, and adds it to order.
		 * If user unselected a dish, disables the amount option, and removes it from order.
		 * if user clicked the order button, calculates the order, presents the order to user,
		 * and let the user choose to approve, update or cancel order.
		 * @param e the event
		 */
		public void mouseClicked (MouseEvent e)
		{
			if (e.getSource() instanceof JCheckBox) // if user selected or unselected a dish
			{
				for (int i=0; i<items.size(); i++) // enable/disable the option to choose the amount and update the order
					if (((JCheckBox)e.getSource()).getText().equals(items.get(i).getDescription()))
					{
						if (checkBoxes[i].isSelected()) // if user selected a dish
						{
							comboBoxes[i].setEnabled(true);
							order.setAmounts(i, comboBoxes[i].getSelectedIndex()+1);
						}
						else // if user unselected a dish
						{
							checkBoxes[i].setSelected(false);
							comboBoxes[i].setSelectedIndex(0);
							comboBoxes[i].setEnabled(false);
							order.setAmounts(i, 0);
						}
						break;
					}
			}
			else // if user clicked "order"
			{
				Object[] options = {"Approve Order", "Update Order", "Cancel Order"};
				int response= JOptionPane.showOptionDialog(null, ""+order+"\nWould you like to approve?", "Your Order", JOptionPane.YES_NO_OPTION, 
						      JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (response == 0) // if user wants to approve order
				{
					String name= JOptionPane.showInputDialog (null, "Enter your name:");
					while (name==null)
						name= JOptionPane.showInputDialog (null, "Enter your name:");
					String id= JOptionPane.showInputDialog (null, "Enter your ID number:");
					while (id==null)
						id= JOptionPane.showInputDialog (null, "Enter your ID number:");
					name= name+id;
					try 
					{
						File file = new File(name+".txt");
						file.createNewFile();
						output= new Formatter (name+".txt");
						output.format ("%s", ""+order);
						output.close();
					}
					catch (IOException ex) 
					{
					      System.out.println("An error occurred");
					}
					if (JOptionPane.showConfirmDialog (null, "Would you like to order again?", "Order Again", JOptionPane.YES_NO_OPTION)==0) 
						resetOrder();
					else
						System.exit(0);
				}
				if (response == 2) // if user wants to cancel order
					resetOrder();
			}
		}
	}
	/**
	 * The class ComboListener handles relevant actionPerformed events.
	 */
	private class ComboListenerer implements ActionListener
	{
		/**
	     * Handles the event which was invoked by user updating the amount of dishes he wants
	     * to order from a certain item, by updating the user's order.
		 * @param e the event
		 */
		public void actionPerformed (ActionEvent e) // if user updates the amount of dishes
		{      
			for(int i=0; i<comboBoxes.length; i++)
				if(((JComboBox)e.getSource())==comboBoxes[i])						
					order.setAmounts(i, comboBoxes[i].getSelectedIndex()+1);
		}
	}
	/**
	 * Resets the user's order.
	 */
	public void resetOrder()
	{
		for (int i=0; i<items.size(); i++) // if user wants to clear the order or start a new order
		{
			comboBoxes[i].setSelectedIndex(0);
			comboBoxes[i].setEnabled(false);
			checkBoxes[i].setSelected(false);
			order.setAmounts(i, 0);	
		}
		JOptionPane.showMessageDialog(null, "Create Your Order");
	}
}
