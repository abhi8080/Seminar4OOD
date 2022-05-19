package se.kth.iv1350.processsale.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    @Test
    public void testRegisterConstructor() {
        Register instance = new Register();
        Amount expResult = new Amount(245);
        Amount result = instance.getBalance();
        assertEquals(expResult.getAmount(), result.getAmount(), "Cash register does not have 245 SEK at sale initiation");
    }

    @Test
    public void testStoreAmount() {
        Register instance = new Register();
        Amount amountPaidByCustomer = new Amount(15);
        instance.storeAmount(amountPaidByCustomer);
        Amount expResult = new Amount(260);
        Amount result = instance.getBalance();
        assertEquals(expResult.getAmount(), result.getAmount(), "Balance in the cash register is not updated correctly");
    }

}
