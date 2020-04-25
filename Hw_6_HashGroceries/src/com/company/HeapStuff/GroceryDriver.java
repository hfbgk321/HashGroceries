package com.company.HeapStuff;

import java.io.*;
import java.util.Scanner;


/**
 * @author Jacky Chen
 * email: Jacky.Chen.6@stonybrook.edu
 * SBU ID: 112704638
 * CSE 214 HW # 6
 * Recitation Section 0 3
 */

/**
 * GroceryDriver Class
 */
public class GroceryDriver {
    /**
     * Main Method
     * @param args args
     */
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        HashedGrocery yourGrocery = new HashedGrocery();
        try{
            FileInputStream file = new FileInputStream("Grocery.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            yourGrocery = (HashedGrocery) fin.readObject();
            fin.close();
            System.out.println("HashGrocery is loaded from Grocery.obj.");
            System.out.println();
            System.out.println("Business Day "+yourGrocery.getBusinessDay()+".");
            System.out.println();
            printMenu();
        }catch(Exception vfg) {
            System.out.println("Grocery.obj doesn not exist. Creating new HashedGrocery object...");
            System.out.println();
            System.out.println("Business Day 1.");
            System.out.println();
            printMenu();
        }

        boolean notfinished = true;
        do{
            System.out.print("Enter option: ");
            String choice = input.nextLine().toLowerCase();
            System.out.println();
            switch(choice){
                case "l":
                    try {
                        System.out.print("Enter file to load: ");
                        String file = input.nextLine();
                        System.out.println();
                        yourGrocery.addItemCatalog(file);
                        System.out.println();
                        printMenu();
                    }catch(Exception xyz){
                        System.out.println("The file cannot be found.");
                        System.out.println();
                    }
                    break;
                case "a":
                    try {
                        System.out.print("Enter item code: ");
                        String itemCode = input.nextLine();
                        System.out.print("Enter item name: ");
                        String itemName = input.nextLine();
                        System.out.print("Enter Quantity in store: ");
                        int quant = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter Average sales per day: ");
                        int avg = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter price: ");
                        double price = input.nextDouble();
                        input.nextLine();
                        Item thing = new Item(itemCode, itemName, quant, avg, price);
                        System.out.println();
                        if(yourGrocery.getHashtable().containsKey(thing.getItemCode())){
                            System.out.println(thing.getItemCode()+": Cannot add item as item code already exists.");
                        }else {
                            yourGrocery.addItem(thing);
                        }
                        System.out.println();
                        printMenu();
                    }catch(Exception fcs){
                        System.out.println(fcs.getMessage());
                    }
                    break;
                case "b":
                    System.out.print("Enter filename: ");
                    String file = input.nextLine();
                    System.out.println();
                    try{
                        yourGrocery.processSales(file);
                        System.out.println();
                        printMenu();
                    }catch(Exception pro){
                        System.out.println("The file cannot be found.");
                        System.out.println();
                    }
                    break;
                case "c":
                    System.out.println();
                    System.out.println(yourGrocery.toString());
                    System.out.println();
                    printMenu();
                    break;
                case "n":
                    System.out.println("Advancing business day...");
                    yourGrocery.nextBusinessDay();
                    printMenu();
                    break;
                case "q":
                    try {
                        System.out.println("HashedGrocery is saved in Grocery.obj.");
                        System.out.println();
                        System.out.println("Program terminating normally...");
                        notfinished = false;
                        FileOutputStream file2 = new FileOutputStream("Grocery.obj");
                        ObjectOutputStream fout = new ObjectOutputStream(file2);
                        fout.writeObject(yourGrocery); // Here "objToWrite" is the object to serialize
                        fout.close();
                    }catch(IOException ghj){
                        System.out.println(ghj.getMessage());
                    }
                    break;
                default:
                    System.out.println("Please choose an option listed in the menu.");
                    System.out.println();
                    break;
            }
        }while(notfinished);
    }

    /**
     * Method to print out menu
     */
    public static void printMenu(){
        System.out.println("Menu: ");
        System.out.println();
        System.out.println("(L) Load item catalog    \n" +
                "(A) Add items              \n" +
                "(B) Process Sales      \n" +
                "(C) Display all items\n" +
                "(N) Move to next business day  \n" +
                "(Q) Quit   \n");
    }
}
