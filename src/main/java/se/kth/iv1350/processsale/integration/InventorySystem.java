package se.kth.iv1350.processsale.integration;

import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.Amount;
import java.util.ArrayList;
import se.kth.iv1350.processsale.model.Sale;

/**
 *
 * Contains all calls to an external inventory system.
 */
public class InventorySystem {

    private ArrayList<ItemDTO> items = new ArrayList<ItemDTO>();
    private static final InventorySystem INVENTORY_SYSTEM = new InventorySystem();

    private InventorySystem() {
        items.add(new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter"));
        items.add(new ItemDTO("Milk", 121936821, new Amount(8), 0.06, "Organic"));
        items.add(new ItemDTO("Bread", 531632821, new Amount(17), 0.25, "Fresh"));
        items.add(new ItemDTO("Cheese", 431632620, new Amount(26), 0.25, "Italian"));
        items.add(new ItemDTO("Onions", 171172841, new Amount(14), 0.12, "Red"));
    }

    static InventorySystem getOnlyInstanceOfInventorySystem() {
        return INVENTORY_SYSTEM;
    }

    ArrayList<ItemDTO> getItems() {
        return items;
    }

    /**
     * Retreives some information about an item from the inventory system.
     *
     * @param identifier The identifier for the item.
     * @return The item.
     * @throws InvalidItemIdentifierException if an item could not be found
     * using the specified item identifier.
     * @throws InventorySystemException if the database call failed.
     */
    public ItemDTO retrieveItem(int identifier) throws InvalidItemIdentifierException {
        for (ItemDTO item : items) {
            if (identifier == item.getItemIdentifier()) {
                if (item.getItemIdentifier() == 431632620) {
                    throw new InventorySystemException("Database server failure");
                }
                return item;
            }
        }
        throw new InvalidItemIdentifierException(identifier);
    }

    /**
     * Updates the inventory.
     *
     * @param saleInformation The sale information needed to update the
     * inventory.
     */
    public void updateInventory(Sale saleInformation) {
    }

    /**
     * Checks if an object is an instance of <code>InventorySystem</code>.
     *
     * @param otherObject The object to compare with this
     * <code>InventorySystem</code>.
     * @return  <code>true</code> if the specified object is an instance of
     * <code>InventorySystem</code>, <code>false</code> if it is not.
     */
    @Override
    public boolean equals(Object otherObject) {
        return otherObject instanceof InventorySystem;
    }
}
