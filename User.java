
import java.sql.*;

public class User extends Common
{
        private Connection connection;
        private LinkedList list;
        private Statement statement;
        private ResultSet results;
        private final String url = "jdbc:oracle:thin:@localhost:1521:xe";

        public User()
        {
             try {
                  Class.forName("oracle.jdbc.driver.OracleDriver");
                  connection = DriverManager.getConnection(url, "system", "Sanket123");
                  statement = connection.createStatement();
                  list = new LinkedList();
             }
             catch (Exception e)
             {
                  System.out.println("We cannot initiate user");
             }
        }
        public void add(int product_id, String product_name, float product_price, int product_av){}
	public void add(int product_id, int amount)
	{
                String product_name = "";
	        float price = 0.0f;
		if (isAvailable(product_id) == 0)
                {
                     System.out.println("The product you are requesting is out of stock.");
                }
                else 
                {
                     String query = "SELECT product_name, product_price FROM inventory WHERE product_id = " + product_id;
                     try
                     {
                          results = statement.executeQuery(query);
                          results.next();
                          product_name = results.getString(1);
                          price = results.getFloat(2);
                          list.insert(product_id, product_name, price, amount);
                          System.out.println(amount + " x " + product_name + " added to cart");
                     }
                     catch (Exception e)
                     {
                          System.out.println("Products details not available");
                     }

                }
	}
	public float total()
	{
                int[][] cart = list.getCart();
                for(int[] item: cart)
                {
                     decrease(item[0], item[1]);
                }
		return list.getTotal();
	}
        public void displayList()
        {
                list.display();
        }
	public void emptyCart()
	{
		list.empty();
                System.out.println("Cart emptied");
	}
        private int isAvailable(int product_id)
        {
             int count = 0;
             String query;
             try 
             {
	          query = "SELECT COUNT(*) FROM inventory WHERE product_id = " + product_id;
                  results = statement.executeQuery(query);
                  results.next();
                  count = results.getInt(1);
             }
             catch (Exception e)
             {
                  System.out.println("Internal problem occured"); 
             }
             return count;
        }
	private void decrease(int product_id, int by) 	// focus on product_av
	{
	     if (isAvailable(product_id) == 0)
             {
             }
             else
             {
                  String query = "UPDATE inventory SET product_av = product_av - " + by + " WHERE product_id = " + product_id;
                  try {
                       int rows = statement.executeUpdate(query);
                  }
 	 	  catch (Exception e)
                  {
                       System.out.println("Internal problem occured"); 
                  }
             }		
	}
        public static void main(String[] args)
        {
             User usr = new User();
             usr.view();
        }
}