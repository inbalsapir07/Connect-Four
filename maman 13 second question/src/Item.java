/**
* The class Item represents an item in the menu of the restaurant.
 * 
 * @author (Inbal Sapir)
 * @version (December 14, 2020)
 */
public class Item 
{
	// variables
	private String description; // the description of the item
	private String type; // the type of the item
	private double price; // the price of the item
	// constructor
	/**
	 * Gets description of an item, type item and price. 
	 * Constructs an item of the restaurant's menu.
	 * @param d the description of the item
	 * @param t the type of the item
	 * @param p the price of the item
	 */
	public Item (String d, String t, double p)
	{
		description= new String(d);
		type= new String(t);
		price=p;
	}
	// methods
	/**
	 * Sets the description of the item
	 * @param d the description to set
	 */
	public void setDescription (String d)
	{
		description=new String (d);
	}
	/**
     * Sets the type of the item
	 * @param t the type to set
	 */
	public void setType (String t)
	{
		type=new String (t);
	}
	/**
	 * Sets the price of the item
	 * @param p the price to set
	 */
	public void setPrice (double p)
	{
		price=p;
	}
	/**
	 * Gets the description of the item
	 * @return the description of the item
	 */
	public String getDescription ()
	{
		return (new String (description));
	}
	/**
	 * Gets the type of the item
	 * @return the type of the item
	 */
	public String getType ()
	{
		return (new String (type));
	}
	/**
	 * Gets the price of the item
	 * @return the price of the item
	 */
	public double getPrice ()
	{
		return price;
	}
	/**
	 * returns a String that represents this item
     * @return a String that represents this item
	 */
	public String toString()
	{
		return (description+" | "+type+" | "+price+" Shekels");
	}
}
