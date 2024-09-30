package org.jsp.task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryManagementSystem {
 
	private static final String FILE_PATH = "inventory.txt";
	private List<InventoryItem> inventory = new ArrayList<>();
	public static void main(String[] args) {
		InventoryManagementSystem system = new InventoryManagementSystem();
		system.menu();

	}
	public void menu() {
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			System.out.println("\n=== Inventory Management System ===");
			System.out.println("1. Add new Item");
			System.out.println("2. Update Existing Item");
			System.out.println("3. View Inventory");
			System.out.println("4. save & Exit");
			System.out.print("Choose an Option : ");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			case 1:
				addItem(sc);
				break;
			case 2:
				updateItem(sc);
				break;
			case 3:
				viewInventory();
				break;
			case 4:
				saveInventory();
				System.out.println("Inventory Save.\nExit Successfully");
				break;
			default:
				System.out.println("Invalid choice. please Enter valid input ");
			}
			
		}while(choice != 4);
	}
	
	//add item
	private void addItem(Scanner sc) {
		System.out.print("Enter Item Name : ");
		String name = sc.nextLine();
		System.out.print("Enter Quantity");
		int quantity = sc.nextInt();
		System.out.print("Enter Price");
		double price = sc.nextDouble();
		inventory.add(new InventoryItem(name, quantity, price));
		System.out.println("Item Added Successfully..");
	}
	
	//update Item
	private void updateItem(Scanner sc) {
		System.out.print("Enter the item name to update : ");
		String name = sc.nextLine();
		boolean found = false;
		for(InventoryItem item : inventory) {
			if(item.itemName.equalsIgnoreCase(name)) {
				System.out.print("Enter New Quantity : ");
				int quantity = sc.nextInt();
				System.out.print("Enter new price : ");
				double price = sc.nextDouble();
				item.quantity = quantity;
				item.price = price;
				System.out.println("Item updated Succesfully.");
				found = true;
				break;
			}
		}
		if(!found) {
			System.out.println("Item Not Found.");
		}
	}
	//view inventory
	private void viewInventory() {
		if(inventory.isEmpty()) {
			System.out.println("Inventory is Empty.");
		}else {
			System.out.println("\n-- Inventory Items --");
			for(InventoryItem item : inventory) {
				System.out.println(item);
			}
		}
	}
	
	//save inventory
	private void saveInventory() {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
			for(InventoryItem item : inventory) {
				writer.write(item.toString());
				writer.newLine();
			}
		}catch(IOException e) {
			System.out.println("Error saving Inventory DATA");
			e.printStackTrace();
		}
	}
	//load Inventory
	private void loadInventory() {
		File file = new File(FILE_PATH);
		if(file.exists()) {
			try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
				String line;
				while((line = reader.readLine()) != null) {
					inventory.add(InventoryItem.fromString(line));
				}
			}catch(IOException e) {
				System.out.println("Error loading inventory DATA ");
				e.printStackTrace();
			}
		}
	}

}

class InventoryItem{
	String itemName;
	int quantity;
	double price;
	public InventoryItem(String itemName,int quantity,double price) {
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
	}
	@Override
	public String toString() {
		return "ItemName : "+itemName+", Quantity : "+quantity+" , Price : "+price;
	}
	public static InventoryItem fromString(String data) {
		String [] splitData = data.split(",");
		return new InventoryItem(splitData[0], Integer.parseInt(splitData[1]), Double.parseDouble(splitData[2]));
	}
}
