import java.util.ArrayList;
/**
 * The class Order represents the user's order.
 * 
 * @author (Inbal Sapir)
 * @version (December 14, 2020)
 */
public class Order 
{
	// variables
	private ArrayList <Item> items; // list of menu's items
	private int[] amounts; // the amount of dishes ordered from the different items
	// constructor
	/**
	 * Gets the list of the restaurant's menu's items as reference.
	 * Constructs a new order by setting all items to 0 dishes. 
	 * @param items the list of the restaurant's menu's items
	 */
	public Order (ArrayList <Item> items)
	{
		this.items=items;
		amounts= new int[items.size()];
		for (int i=0; i<amounts.length; i++)
			amounts[i]=0;
	}
	// methods
	/**
	 * Gets an index and an amount.
	 * Setting the amount of dishes ordered from the item in reference index to be as many as the reference amount.
	 * @param index reference index
	 * @param amount reference amount
	 */
	public void setAmounts (int index, int amount)
	{
		amounts[index]=amount;
	}
	/**
	 * returns a String that represents this order 
     * @return a String that represents this order
	 */
	public String toString ()
	{
		String st=""; // the description of the order
		double price=0; // the total price of the order
		for (int i=0; i<items.size(); i++)
			if (amounts[i]!=0)
			{
				st=st+"\n"+items.get(i).getDescription()+", "+amounts[i]+" dishes, "+(items.get(i).getPrice())*amounts[i]+" shekels";
				price=price+(items.get(i).getPrice()*amounts[i]);
			}
		return ("Your Order: "+st+"\n Toal: "+price);
	}
}
