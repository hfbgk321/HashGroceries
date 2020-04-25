package com.company.HeapStuff;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Hashtable;
import java.util.Set;
/**
 * @author Jacky Chen
 * email: Jacky.Chen.6@stonybrook.edu
 * SBU ID: 112704638
 * CSE 214 HW # 6
 * Recitation Section 0 3
 */

/**
 * HashGrocery class for storing items
 */
public class HashedGrocery implements  Serializable{
    private int businessDay = 1;// business day counter
    private Hashtable<String,Item> hashtable = new Hashtable<>();// hashtable for storage

    /**
     * empty constructor
     */
    public HashedGrocery(){}

    /**
     * Method to add item to hashtable
     * @param item item you wish to add
     * @throws sameItemCode if item you wish to add is already in the hashtable
     */
    public void addItem(Item item) throws  sameItemCode{
            if (hashtable.containsKey(item.getItemCode())){
                throw new sameItemCode();
            }else {
                System.out.println(item.getItemCode() + ": " + item.getName() + " is added to the inventory.");
                hashtable.put(item.getItemCode(), item);
            }
        }


    /**
     * method to adjust item's quantity
     * @param item item you want to adjust
     * @param adjustByQuantity quantity you want to change it to
     */
    public void updateItem(Item item, int adjustByQuantity){
        item.setQtyInStore(adjustByQuantity);
    }

    /**
     * method to transfer item from json file into hashtable
     * @param filename name of file you want to extract from
     * @throws FileNotFoundException if file doesnt exist
     * @throws IOException in case of IO issues
     * @throws sameItemCode item in file already exists in hashtable
     * @throws ParseException item is unparseble
     */
    public void addItemCatalog(String filename) throws FileNotFoundException , IOException,sameItemCode,ParseException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        JSONParser parser = new JSONParser();
        JSONArray objs = (JSONArray) parser.parse(isr);// objs is a JSONArray which contains all JSONObjects found in the InputStream
        for(int x = 0; x<objs.size();x++){
            JSONObject obj = (JSONObject) objs.get(x);
            String itemCode = (String) obj.get("itemCode");
            String itemName = (String) obj.get("itemName");
            int avgSales = Integer.parseInt((String) obj.get("avgSales"));
            int qtyInStore = Integer.parseInt((String) obj.get("qtyInStore"));
            double price = Double.parseDouble((String) obj.get("price"));
            int amtOnOrder = Integer.parseInt((String) obj.get("amtOnOrder"));
            Item temp = new Item(itemCode,itemName,avgSales,qtyInStore,price,amtOnOrder);
            if(this.hashtable.containsKey(itemCode))
                System.out.println(itemCode+": Cannot add item as item code already exists.");
            else {
                this.addItem((temp));
            }
        }
    }

    /**
     * Method to process sales in a file and update the hashtable
     * @param filename file you wish to process
     * @throws FileNotFoundException if file doesnt exist
     * @throws IOException IO Issues
     * @throws ParseException unparseable
     */
    public void processSales(String filename) throws FileNotFoundException,IOException,ParseException{
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        JSONParser parser = new JSONParser();
        JSONArray objs = (JSONArray) parser.parse(isr);

        for(int x = 0; x < objs.size();x++){
            JSONObject obj = (JSONObject) objs.get(x);
            String itemCode = (String)obj.get("itemCode");
            int qtySold = Integer.parseInt((String) obj.get("qtySold"));
            if(hashtable.containsKey(itemCode)){
                Item curr = hashtable.get(itemCode);
                if(curr.getQtyInStore() < qtySold){
                    if(curr.getOnOrder() == 0) {
                        if (curr.getQtyInStore() < (3 * curr.getAverageSalesPerDay())) {
                            curr.setOnOrder(2 * curr.getAverageSalesPerDay());
                            curr.setArrivalDay(businessDay + 3);
                        }
                    }
                    System.out.println(itemCode+": Not enough stock for sale. Not updated.");
                    continue;
                }
                curr.setQtyInStore(curr.getQtyInStore()-qtySold);
                if(curr.getOnOrder() >0){
                    System.out.println(itemCode+": "+qtySold+" units of "+curr.getName()+" " +
                            "are sold.");
                }
                else if(curr.getQtyInStore() < (3*curr.getAverageSalesPerDay())){
                    System.out.println(itemCode+": "+qtySold+" units of "+curr.getName()+" " +
                            "are sold. Order has been placed for "+
                            (2*curr.getAverageSalesPerDay())+ " more units.");
                    curr.setOnOrder(2*curr.getAverageSalesPerDay());
                    curr.setArrivalDay(businessDay+3);
                }else{
                    System.out.println(itemCode+": "+qtySold+" units of "+curr.getName()+" " +
                            "are sold.");
                }
            }else{
                System.out.println(itemCode+": Cannot buy as it is not in the grocery store");
            }
        }
    }

    /**
     * method to update the business day
     */
    public void nextBusinessDay(){
        boolean ordersCame = false;
        businessDay+=1;
        System.out.println("Business Day "+businessDay+".");
        System.out.println();
        Set<String> keys = hashtable.keySet();
        for(String key: keys){
            Item current = hashtable.get(key);
            if(current.getArrivalDay() == businessDay){
                ordersCame = true;
                break;
            }
        }
        if(ordersCame) {
            System.out.println("Orders have arrived for: ");
            System.out.println();
            for (String key : keys) {
                Item current = hashtable.get(key);
                if (current.getArrivalDay() == businessDay) {
                    System.out.println(current.getItemCode() + ": " + current.getOnOrder() + " units of " + current.getName());
                    current.setQtyInStore(current.getQtyInStore() + current.getOnOrder());
                    current.setOnOrder(0);
                    current.setArrivalDay(0);
                }
            }
            System.out.println();
        }else{
            System.out.println("No orders have arrived.");
            System.out.println();
        }
    }

    /**
     * tostring method
     * @return string representation of hashGrocery
     */
    public String toString(){
        Set<String> keys = hashtable.keySet();
        String title = String.format("%s%7s%19s%12s%9s%11s%15s","Item code",
                "Name","Qty","AvgSales","Price","OnOrder","ArrOnBusDay")+
                "\n----------------------------------------------------------------------------------\n";
        for(String x: keys){
            title+=hashtable.get(x).toString()+"\n";
        }
        return title;
    }

    /**
     * getter method for business day
     * @return business day
     */
    public int getBusinessDay() {
        return businessDay;
    }

    /**
     * setter method for business day
     * @param businessDay new business day
     */
    public void setBusinessDay(int businessDay) {
        this.businessDay = businessDay;
    }
    public Hashtable<String,Item> getHashtable(){
        return this.hashtable;
    }
}

/**
 * exception class for sameItemCode
 */
class sameItemCode extends Exception{
    /**
     * constructor.
     */
    public sameItemCode(){
        super("Item with the same item code already exists in the hashtable");
    }
}
