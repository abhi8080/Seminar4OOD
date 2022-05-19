package se.kth.iv1350.processsale.integration;

/**
 * This class creates all the systems used during the sale.
 */
public class SystemCreator {

    private InventorySystem inventorySystem;
    private AccountingSystem accountingSystem;

    /**
     * Creates a new instance
     */
    public SystemCreator() {
        this.inventorySystem = InventorySystem.getOnlyInstanceOfInventorySystem();
        this.accountingSystem = new AccountingSystem();

    }

    /**
     *
     * @return The object InventorySystem.
     */
    public InventorySystem getInventorySystem() {
        return inventorySystem;
    }

    /**
     *
     * @return The object AccountingSystem.
     */
    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

}
