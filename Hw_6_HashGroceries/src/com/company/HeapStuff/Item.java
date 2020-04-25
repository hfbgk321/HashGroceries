package com.company.HeapStuff;

import java.io.Serializable;

/**
 * @author Jacky Chen
 * email: Jacky.Chen.6@stonybrook.edu
 * SBU ID: 112704638
 * CSE 214 HW # 6
 * Recitation Section 0 3
 */

/**
 * Serializable Item class
 */
public class Item implements Serializable {
    private String itemCode; // key to hashtable
    private String name; // name of item
    private int qtyInStore; // qty of item in store
    private int averageSalesPerDay; //average sales of item per day
    private int onOrder; //How many have already been ordered for restocking
    private int arrivalDay; // arrival day of order
    private double price; // price of item

    /**
     * Empty constructor
     */
    public Item(){
    }

    /**
     * Customized contructor for item
     * @param itemCode item code
     * @param name name of item
     * @param avgSales average sales of item
     * @param qtyInStore quantity of item in store
     * @param price price of item
     * @param amtOnOrder amount of item currently in order
     */
    public Item(String itemCode, String name, int avgSales, int qtyInStore,double price, int amtOnOrder){
        this.itemCode = itemCode;
        this.name = name;
        this.averageSalesPerDay = avgSales;
        this.qtyInStore = qtyInStore;
        this.price = price;
        this.onOrder = amtOnOrder;
    }

    /**
     * Customizable constructor for Item
     * @param itemCode item code
     * @param itemName name of item
     * @param quant quantity of item in store
     * @param avg avg sale of item per day
     * @param price price of item
     */
    public Item(String itemCode, String itemName, int quant, int avg, double price){
        this.itemCode = itemCode;
        this.name = itemName;
        this.qtyInStore = quant;
        this.averageSalesPerDay = avg;
        this.price = price;
    }

    /**
     * ToString method for item
     * @returnstring representation of item
     */
    public String toString(){
        String item = String.format("%-12s%-20s%-9d%-10d%-12.2f%-12d%d",itemCode,name,qtyInStore,averageSalesPerDay,price,onOrder,arrivalDay);
        return item;
    }

    /**
     * getter method for item code
     * @return item code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * setter method for item code
     * @param itemCode new itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * getter method for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter method for name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method for quantity of item in store
     * @return quantity of item in store
     */
    public int getQtyInStore() {
        return qtyInStore;
    }

    /**
     * Setter method for quantity of item in store
     * @param qtyInStore quantity of item in store
     */
    public void setQtyInStore(int qtyInStore) {
        this.qtyInStore = qtyInStore;
    }

    /**
     * getter method for average sales per day
     * @return average sales per day
     */
    public int getAverageSalesPerDay() {
        return averageSalesPerDay;
    }

    /**
     * setter method for average sales per day
     * @param averageSalesPerDay new averageSalesPerDay
     */
    public void setAverageSalesPerDay(int averageSalesPerDay) {
        this.averageSalesPerDay = averageSalesPerDay;
    }

    /**
     * getter method for on order
     * @return on order
     */
    public int getOnOrder() {
        return onOrder;
    }

    /**
     * setter method for on order
     * @param onOrder new on order
     */
    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }

    /**
     * getter method for arrival day
     * @return arrival day
     */
    public int getArrivalDay() {
        return arrivalDay;
    }

    /**
     * setter method for arrival day
     * @param arrivalDay new arrival day
     */
    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    /**
     * getter method for price
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * setter method for price
     * @param price new price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
