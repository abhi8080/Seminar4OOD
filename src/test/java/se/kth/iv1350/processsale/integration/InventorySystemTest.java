package se.kth.iv1350.processsale.integration;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.processsale.model.Amount;

import se.kth.iv1350.processsale.dto.ItemDTO;

public class InventorySystemTest {

    @Test
    public void testInventorySystemConstructor() {
        InventorySystem instance = InventorySystem.getOnlyInstanceOfInventorySystem();
        ItemDTO item = new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter");
        ArrayList<ItemDTO> items = new ArrayList<>();
        items.add(item);
        boolean expResult = true;
        boolean result = items.get(0).equals(instance.getItems().get(0));
        assertEquals(expResult, result, "First added item in inventory is not in the inventory or is at the wrong place ");

    }

    @Test
    public void testRetrieveItem() {
        InventorySystem instance = InventorySystem.getOnlyInstanceOfInventorySystem();
        ItemDTO result = null;
        try {
            result = instance.retrieveItem(934632865);
        } catch (Exception e) {
            fail("Got exception");
            e.printStackTrace();
        }
        ItemDTO expResult = new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter");
        assertTrue(expResult.getName().equals(result.getName()), "Name is wrong");
        assertEquals(expResult.getItemIdentifier(), result.getItemIdentifier(), "Item identifier is wrong");
        assertEquals(expResult.getPrice().getAmount(), result.getPrice().getAmount(), "Price is wrong");
        assertEquals(expResult.getVATRate(), result.getVATRate(), "VAT rate is wrong");
        assertTrue(expResult.getItemDescription().equals(result.getItemDescription()), "Item description is wrong");
    }

    @Test
    public void testRetrieveItemIfInvalidIdentifier() {
        InventorySystem instance = InventorySystem.getOnlyInstanceOfInventorySystem();
        ItemDTO item = new ItemDTO("Does not exist", 111111111, new Amount(0), 0, "Blue");
        try {
            instance.retrieveItem(item.getItemIdentifier());
            fail("Found an item that does not exist in the inventory catalog");
        } catch (InvalidItemIdentifierException exc) {
            assertTrue(exc.getMessage().contains("" + item.getItemIdentifier()), "Wrong exception message, does not contain the invalid identifier.");
        } catch (InventorySystemException exc) {
            fail("Wrong exception was thrown");
            exc.printStackTrace();
        }
    }

    public void testRetrieveItemIfUnableToScanItem() {
        InventorySystem instance = InventorySystem.getOnlyInstanceOfInventorySystem();
        try {
            instance.retrieveItem(431632620);
            fail("Item identifer did not cause a database failure");
        } catch (InventorySystemException exc) {
            assertTrue(exc.getMessage().contains("Database server failure"), "Wrong exception message: " + exc.getMessage());
        } catch (InvalidItemIdentifierException exc) {
            fail("Wrong exception was thrown");
            exc.printStackTrace();
        }
    }

    @Test
    public void testEquals() {
        InventorySystem instance = InventorySystem.getOnlyInstanceOfInventorySystem();
        boolean expResult = true;
        boolean result = instance.equals(InventorySystem.getOnlyInstanceOfInventorySystem());
        assertEquals(expResult, result, "Two InventorySystem instances are not equal");
    }

    @Test
    public void testNotEqualsNull() {
        InventorySystem instance = InventorySystem.getOnlyInstanceOfInventorySystem();
        boolean expResult = false;
        boolean result = instance.equals(null);
        assertEquals(expResult, result, "InventorySystem instance equal to null");
    }

}
