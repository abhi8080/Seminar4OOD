package se.kth.iv1350.processsale.dto;

import se.kth.iv1350.processsale.model.Amount;

/**
 * Contains informaton about an item and the running total, which is returned
 * after scanning an item.
 */
public class ItemAndSaleInfoDTO {

    private final String name;
    private final String itemDescription;
    private final Amount price;
    private final Amount runningTotal;

    /**
     * Creates a new instance.
     *
     * @param name Name of the item.
     * @param price Price of an item.
     * @param itemDescription Item description of an item.
     * @param runningTotal The running total of the sale at some point.
     */
    public ItemAndSaleInfoDTO(String name, Amount price, String itemDescription, Amount runningTotal) {
        this.name = name;
        this.itemDescription = itemDescription;
        this.price = price;
        this.runningTotal = runningTotal;
    }

    /**
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Description of the item.
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     *
     * @return The price of the item.
     */
    public Amount getPrice() {
        return price;
    }

    /**
     *
     * @return The running total of the sale at some point.
     */
    public Amount getRunningTotal() {
        return runningTotal;
    }

    /**
     *
     * @return A string representation of some information about the item and
     * the sale.
     */
    @Override
    public String toString() {
        return "Name: " + this.name + "     Price: " + this.price + " SEK      Description: " + this.itemDescription + "     Running Total: " + this.runningTotal + " SEK";
    }

}
