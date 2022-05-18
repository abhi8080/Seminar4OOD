package se.kth.iv1350.processsale.integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountingSystemTest {

    @Test
    public void testEquals() {
        AccountingSystem instance = new AccountingSystem();
        boolean expResult = true;
        boolean result = instance.equals(new AccountingSystem());
        assertEquals(expResult, result, "Two AccountingSystem instances are not equal");
    }

    @Test
    public void testNotEqualsNull() {
        AccountingSystem instance = new AccountingSystem();
        boolean expResult = false;
        boolean result = instance.equals(null);
        assertEquals(expResult, result, "AccountingSystem instance equal to null");
    }

}
