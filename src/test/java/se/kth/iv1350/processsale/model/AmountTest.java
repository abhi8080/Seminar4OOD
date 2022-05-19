package se.kth.iv1350.processsale.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AmountTest {

    @Test
    public void testToString() {
        Amount instance = new Amount(5);
        String expResult = "5";
        String result = instance.toString();
        assertEquals(expResult, result, "Wrong string representation");
    }

}
