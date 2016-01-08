package iterator;

public class Waitress {
	Menu pancakeHouseMenu;
	Menu dinerMenu;
	
	public Waitress(Menu pancakeHouseMenu,Menu dinerMenu){
		this.pancakeHouseMenu=pancakeHouseMenu;
		this.dinerMenu=dinerMenu;
	}

	public void printMenu(){
		Iterator pancakeIterator = pancakeHouseMenu.createIterator();
		Iterator dinerIterator = dinerMenu.createIterator();
		System.out.println("Diner is :");
		printMenu(dinerIterator);
		System.out.println("Lunch is :");
		printMenu(pancakeIterator);
	}

	private void printMenu(Iterator iterator) {
		while(iterator.hasNext()){
			MenuItem menuItem = (MenuItem) iterator.next();
			System.out.println(menuItem.getName()+", ");
			System.out.println(menuItem.getPrice()+" -- ");
			System.out.println(menuItem.getDescription());
		}		
	}
}
