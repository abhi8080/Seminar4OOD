package se.kth.iv1350.processsale.integration;

import static org.junit.jupiter.api.Assertions.*;

public class SystemCreatorTest {

    public void testSystemCreatorConstructor() {
        SystemCreator instance = new SystemCreator();
        AccountingSystem accountingSystem = new AccountingSystem();
        InventorySystem inventorySystem = InventorySystem.getOnlyInstanceOfInventorySystem();
        assertTrue(instance.getAccountingSystem().equals(accountingSystem), "New instance of AccountingSystem is not equal to an AccountingSystem object");
        assertTrue(instance.getInventorySystem().equals(inventorySystem), "New instance of InventorySystem is not equal to an InventorySystem object");
    }

}
