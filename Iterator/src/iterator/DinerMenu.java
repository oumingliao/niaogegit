package iterator;

import java.util.ArrayList;

public class DinerMenu {
	static final int MAX_ITEMS=6;
	int numberOfItems=0;
	MenuItem[] menuItems;
	
	public DinerMenu(){
		menuItems = new MenuItem[MAX_ITEMS];
		
		addItem("1 Diner", "1 tomato", true, 2.99);
		
		addItem("2 Diner", "2 tomato", false, 2.99);
		
		addItem("3 Diner", "3 tomato", true, 3.49);
		
		addItem("4 Diner", "4 tomato", true, 3.59);
	}
	
	public void addItem(String name,String description,
			boolean vegetarian,double price){
		MenuItem menuItem = new MenuItem(name,description,vegetarian,price);
		if(numberOfItems >= MAX_ITEMS){
			System.out.println("Sorry! Out of Numbers!!!");
		}
		else{
			menuItems[numberOfItems]=menuItem;
			numberOfItems = numberOfItems + 1;
		}
		
	}
	
//	public MenuItem[] getMenuItems(){
//		return menuItems;
//	}
	
	public Iterator createIterator(){
		return new DinerMenuIterator(menuItems);
	}

}
