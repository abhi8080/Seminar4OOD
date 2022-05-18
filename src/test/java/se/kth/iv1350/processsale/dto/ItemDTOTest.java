package se.kth.iv1350.processsale.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.processsale.model.Amount;

public class ItemDTOTest {

    @Test
    public void testEquals() {
        Object otherObject = new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter");
        ItemDTO instance = new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter");
        boolean expResult = true;
        boolean result = instance.equals(otherObject);
        assertEquals(expResult, result, "Two items with the same item identifier are not equal");
    }

    @Test
    public void testNotEquals() {
        Object otherObject = new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter");
        ItemDTO instance = new ItemDTO("Cheese", 431632620, new Amount(26), 0.25, "Italian");
        boolean expResult = false;
        boolean result = instance.equals(otherObject);
        assertEquals(expResult, result, "Two items with different item identifier are equal");
    }

}
