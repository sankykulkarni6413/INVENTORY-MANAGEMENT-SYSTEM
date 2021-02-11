
import java.sql.*;

public class Admin extends Common
{
        private Connection connection;
        private Statement statement;
        private ResultSet results;
        private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
        public Admin()
        {
             try
             {
                  Class.forName("oracle.jdbc.driver.OracleDriver");
                  connection = DriverManager.getConnection(url, "system", "Sanket123");
	          statement = connection.createStatement();
                  
             }
             catch (Exception e)
             {
                  System.out.println("We cannot initiate admin");
             }
        }
	public void add(int product_id, String product_name, float product_price, int product_av)
	{
             String query;
             if (isAvailable(product_id) > 0)
             {
                  System.out.println("The product with this ID is present");
             }
             else
             {
                  try 
                  {
	               query = "INSERT INTO inventory VALUES(" + product_id + ", '" + product_name + "'," + product_price + "," + product_av + ")";
                       int rows = statement.executeUpdate(query);
                       System.out.println(rows + " product inserted");
                  }
                  catch (Exception e)
                  {
                       System.out.println("Internal problem occured"); 
                  }
             }
	}
	public void increase(int product_id, int by) 	// focus on product_av
	{
	     if (isAvailable(product_id) == 0)
             {
                  System.out.println("The product with this ID is not present. Please add");
             }
             else
             {
                  String query = "UPDATE inventory SET product_av = product_av + " + by + " WHERE product_id = " + product_id;
                  try {
                       int rows = statement.executeUpdate(query);
                  }
 	 	  catch (Exception e) 
                  {
                       System.out.println("Internal problem occured"); 
                  }
                  System.out.println(product_id + "'s availibility incremented by " + by);
             }
	}
	public void decrease(int product_id, int by) 	// focus on product_av
	{
	     if (isAvailable(product_id) == 0)
             {
                  System.out.println("The product with this ID is not present. Please add");
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
                  System.out.println(product_id + "'s availibility decremented by " + by);
             }		
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
        public static void main(String[] args)
        {
	     /*
              * This method is for testing purpose
	      * Every method of this class is function-intensive. Only perform function when you're sure
	      * Use with only Admin object. 'Common' object is not possible.
             */
        }
}
