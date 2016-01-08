package iterator;

import java.util.ArrayList;

public class PancakeHouseMenu {
	ArrayList menuItems;
	
	public PancakeHouseMenu(){
		menuItems = new ArrayList();
		
		addItem("1 Pancake Breakfast", "1 Pancake with egg", true, 2.99);
		
		addItem("2 Pancake Breakfast", "2 Pancake with egg", false, 2.99);
		
		addItem("3 Pancake Breakfast", "3 Pancake with egg", true, 3.49);
		
		addItem("4 Pancake Breakfast", "4 Pancake with egg", true, 3.59);
	}
	
	public void addItem(String name,String description,
			boolean vegetarian,double price){
		MenuItem menuItem = new MenuItem(name,description,vegetarian,price);
		menuItems.add(menuItem);
	}
	
//	public ArrayList getMenuItems(){
//		return menuItems;
//	}
	
	public Iterator createIterator(){
		return new PancakeHouseIterator(menuItems);
	}

}
