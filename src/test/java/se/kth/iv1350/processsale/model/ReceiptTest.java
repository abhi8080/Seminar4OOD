package se.kth.iv1350.processsale.model;

import se.kth.iv1350.processsale.dto.ItemDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {

    @Test
    public void testCreateReceipt() {
        Sale sale = new Sale();
        sale.registerItemInSale(new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter"));
        Amount amountPaidByCustomer = new Amount(20);
        sale.paymentForSale(amountPaidByCustomer);
        Receipt instance = new Receipt(sale);
        String receipt = instance.createReceipt();

        assertTrue(receipt.contains(sale.getDateAndTimeOfSale().toString()), "Wrong sale time specified on the receipt");
        assertTrue(receipt.contains("Amount paid: 20 SEK"), "Wrong amount paid specified on the receipt");
        assertTrue(receipt.contains("Total cost: 11 SEK"), "Wrong total cost on the receipt");
        assertTrue(receipt.contains("Change: 9 SEK"), "Wrong change specified on the receipt");
        assertTrue(receipt.contains("Orange Juice"), "Item bought in sale is not on the receipt");
        assertTrue(receipt.contains("VAT for entire sale: 1.2 SEK"), "VAT for entire sale is not correct on the receipt");

    }

}
