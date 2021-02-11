

public class LinkedList
{
	private class Node
	{
		int product_id;
		String product_name;
		float price;
		int amount;
		Node next;
	}
	private Node head;
	public LinkedList()
	{
		head = null;
	}
	public void display()
	{
		Node temp = new Node();
	        temp = head;
		while (temp != null)
		{
			System.out.println(temp.product_name + " Rs." + temp.price + " x " + temp.amount);
 			temp = temp.next;
		}
	}
	public void insert(int product_id, String product_name, float price, int amount)
	{
		Node item = new Node();
		item.product_id = product_id;
		item.product_name = product_name;
		item.price = price;
		item.amount = amount;
	
		if (head == null)
			head = item;
		else
		{
			Node temp = new Node();
			temp = head;
			while (temp.next != null)
				temp = temp.next;
			temp.next = item;
		}
	}
        public int[][] getCart()
        {
		Node temp = new Node();
	        temp = head;
                int[][] cart;
                cart = new int[10][2];
                int i = 0;
		while (temp != null)
		{
                        cart[i][0] = temp.product_id;
                        cart[i][1] = temp.amount;
                        i++;
                        temp = temp.next;
		}
                return cart;
        }
	public float getTotal()
	{
		Node temp = new Node();
		temp = head;
		float total = 0.0f;
		while (temp != null)
		{
			total += temp.price * temp.amount;
			temp = temp.next;
		}
		return total;
	}
	public void empty()
	{
		head = null;
	}
	public static void main(String[] args)
	{
		// For testing purpose only
		LinkedList product = new LinkedList();
	}
}
