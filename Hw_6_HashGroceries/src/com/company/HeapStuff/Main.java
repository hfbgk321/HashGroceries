package com.company.HeapStuff;

public class Main {

    public static void main(String[] args) {
	// write your code here
        HashedGrocery test = new HashedGrocery();
        try {
            test.addItemCatalog("itemCatalog_1.txt");
            System.out.println(test.toString());
        }catch(Exception asd) {
            System.out.println(asd.getMessage());
        }
    }
}
