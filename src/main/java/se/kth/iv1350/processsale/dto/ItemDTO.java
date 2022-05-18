package se.kth.iv1350.processsale.dto;

import se.kth.iv1350.processsale.model.Amount;

/**
 * Represents an item in the retail store.
 */
public class ItemDTO {

    private final String name;
    private final Amount price;
    private final double VATRate;
    private final String itemDescription;
    private final int itemIdentifier;

    /**
     * Creates a new instance.
     *
     * @param name Name of the item.
     * @param itemIdentifier The identifer for the item.
     * @param price Price of the item, without including VAT.
     * @param VATRate Item's VAT rate.
     * @param itemDescription Item description of the item.
     */
    public ItemDTO(String name, int itemIdentifier, Amount price, double VATRate, String itemDescription) {
        this.name = name;
        this.itemIdentifier = itemIdentifier;
        this.price = price;
        this.VATRate = VATRate;
        this.itemDescription = itemDescription;
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
     * @return The price of the item, without including VAT.
     */
    public Amount getPrice() {
        return price;
    }

    /**
     *
     * @return Item's VAT rate.
     */
    public double getVATRate() {
        return VATRate;
    }

    /**
     *
     * @return Item description of the item.
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     *
     * @return The identifier for the item.
     */
    public int getItemIdentifier() {
        return this.itemIdentifier;
    }

    /**
     * Two items are the same if their item identifiers are equal.
     *
     * @param otherObject The <code>ItemDTO</code> to compare with this
     * <code>ItemDTO</code>.
     * @return <code>true</code> if the item identifier of the specified
     * <code>ItemDTO</code> is equal to the item identifier in this
     * <code>ItemDTO</code>, <code>false</code> if they are not.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null || !(otherObject instanceof ItemDTO)) {
            return false;
        }

        ItemDTO otherItem = (ItemDTO) otherObject;

        return this.itemIdentifier == otherItem.itemIdentifier;
    }

}
