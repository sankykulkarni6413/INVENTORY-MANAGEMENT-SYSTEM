
import java.sql.*;
import java.util.Scanner;

public class Inventory
{
      private Connection connection;
      private ResultSet results;
      private Statement statement;
      private final String url = "jdbc:oracle:thin:@localhost:1521:xe";

      public Inventory()
      {
           try
           {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(url, "system", "Sanket123");
                statement = connection.createStatement();
           }
           catch (Exception e)
           {
                System.out.println("Application internal error.");
           }
      }
      public String login(int user_id, String password)
      {
           String user_name = "";
           String query = "SELECT user_name FROM users WHERE user_id = " + user_id + " AND password = '" + password + "'";
           try
           {
                results = statement.executeQuery(query);
                results.next();
                user_name = results.getString(1);
           }
           catch (Exception e)
           {
                System.out.println("User does not exists.");
           }
           return user_name;
      }
      public static void main(String[] args)
      {
           Scanner stdin = new Scanner(System.in);
           String user_name = "";

           System.out.println("\tWELCOME TO INVENTORY MANAGEMENT SYSTEM");
           System.out.println("______________________________________________________");

           Inventory store = new Inventory();
           int choice;
           while (true)
           {
                System.out.println("1. Admin 2. User:");
                choice = stdin.nextInt();
                if (choice == 1 || choice == 2)
                     break;
                else
                     System.out.println("\nInvalid choice");
           }
           while (true)
           {
                System.out.println("Login to system to continue:");

                System.out.print("User id: ");
                int user_id = stdin.nextInt();
                System.out.print("Password: ");
                String password = stdin.next();
                user_name = store.login(user_id, password);

                if (user_name.equals("") == false)
                     break;
                else
		     System.out.println("\n Invalid username or password");
           }

           if (choice == 1)
           {
               System.out.println("Welcome, " + user_name + ". You're logged in as Admin");
               Admin admin = new Admin();
               System.out.println("______________________________________________________");
               int user_choice = 0;
               while (true)
               {
                    
                    System.out.println("[1] View products [2] Add Product [3] Increase stock [4] Decrease stock [5] Logout");
                    user_choice = stdin.nextInt();
                    switch(user_choice)
                    {
                         case 1:
                              admin.view();
                              break;
                         case 2:
                              System.out.print("Enter product ID: ");
                              int product_id = stdin.nextInt();
                              System.out.print("Enter product name: ");
                              String product_name = stdin.next();
                              System.out.print("Enter product price (Rs.): ");
                              float product_price  = stdin.nextFloat();
                              System.out.print("Enter product current stock: ");
                              int product_av = stdin.nextInt();
                              if (product_av < 1)
                                   System.out.println("Invalid Product stock. Aborting addition of product...");
                              else
                                   admin.add(product_id, product_name, product_price, product_av);
                              break;
                         case 3:
                              System.out.print("Enter product ID: ");
                              product_id = stdin.nextInt();
                              System.out.print("Enter number to increse by: ");
                              int by = stdin.nextInt();
                              if (by < 1)
                                   System.out.println("The value of increment must be greater than 0. Aborting increment..");
                              else
				   admin.increase(product_id, by);                           
                              break;
                         case 4:
                              System.out.print("Enter product ID: ");
                              product_id = stdin.nextInt();
                              System.out.print("Enter number to decrease by: ");
                              by = stdin.nextInt();
                              if (by < 1)
                                   System.out.println("The value of decrement must be greater than 0. Aborting decrement..");
                              else
				   admin.decrease(product_id, by);                           
                              break;
                         case 5:
                              System.out.println("Logging out...");
                              break;
                         default:
                              System.out.println("Invalid choice.");
                              break;
                    }
                    if (user_choice == 5)
                         break;
                }
                System.out.println("Thank you " + user_name + ", Visit again!");
           }
           else
           {
	       System.out.println("Welcome, " + user_name + ". You're logged in as Customer");
               User user = new User();
               System.out.println("______________________________________________________");
               System.out.println("Choose from following list of products:");
               user.view();
               int user_choice = 0;
               while (true)
               {
                    
                    System.out.println("[1] View products [2] View cart [3] Buy product [4] Get Bill [5] Empty cart [6] Logout");
                    user_choice = stdin.nextInt();
                    switch(user_choice)
                    {
                         case 1:
                              user.view();
                              break;
                         case 2:
                              user.displayList();
                              break;
                         case 3:
                              System.out.print("Enter product id: ");
                              int product_id = stdin.nextInt();
                              System.out.print("Enter product amount (fix value): ");
                              int product_amount = stdin.nextInt();
                              user.add(product_id, product_amount);
                              break;
                         case 4:
                              System.out.println(user_name + "'s bill");
                              System.out.println("------------------------------------");
                              user.displayList();
                              System.out.println("Total price: Rs. " + user.total());
                              break;
                         case 5:
                              user.emptyCart();
                              break;
                         case 6:
                              System.out.println("Logging out...");
                              break;
                         default:
                              System.out.println("Invalid choice.");
                              break;
                    }
                    if (user_choice == 6)
                         break;
               }
               System.out.println("Thank you " + user_name + ", Visit again!");
           }
      }
}