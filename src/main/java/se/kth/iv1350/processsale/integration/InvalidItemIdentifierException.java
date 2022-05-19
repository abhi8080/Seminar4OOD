package se.kth.iv1350.processsale.integration;

/**
 * Thrown when scanning an item identifier that does not exist in the inventory
 * catalog.
 */
public class InvalidItemIdentifierException extends Exception {

    /**
     * Creates a new instance.
     *
     * @param itemIdentifier The item identifier that is invalid.
     */
    public InvalidItemIdentifierException(int itemIdentifier) {
        super("Item with identifier: " + itemIdentifier + " does not exist in the inventory catalog");
    }

}
