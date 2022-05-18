package se.kth.iv1350.processsale.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.processsale.model.Amount;

public class ItemAndSaleInfoDTOTest {

    @Test
    public void testToString() {
        ItemAndSaleInfoDTO instance = new ItemAndSaleInfoDTO("Orange Juice", new Amount(10), "1 liter", new Amount(11));
        String result = instance.toString();
        assertTrue(result.contains("Orange Juice"), "Name of item is wrong or not shown");
        assertTrue(result.contains("Price: 10 SEK"), "Price of item is wrong or not shown");
        assertTrue(result.contains("1 liter"), "Description of item is wrong or not shown");
        assertTrue(result.contains("Running Total: 11 SEK"), "Running total is wrong or not shown");
    }
}
