package iterator;

import java.util.Hashtable;

public class CafeMenu implements Menu{
	Hashtable menuItems = new Hashtable();
	
	public CafeMenu(){
		addItem("1 coffee", "1 soy", true, 2.99);
		
		addItem("2 coffee", "2 soy", false, 2.99);
		
		addItem("3 coffee", "3 soy", true, 3.49);		
	}
	
	public void addItem(String name,String description,
			boolean vegetarian,double price){
		MenuItem menuItem = new MenuItem(name,description,vegetarian,price);
		menuItems.put(menuItem.getName(),menuItem);
	}

	@Override
	public Iterator createIterator() {
		// TODO Auto-generated method stub
		return (Iterator) menuItems.values().iterator();
	}

}
