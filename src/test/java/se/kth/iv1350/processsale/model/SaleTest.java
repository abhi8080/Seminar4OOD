package se.kth.iv1350.processsale.model;

import se.kth.iv1350.processsale.dto.ItemDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.processsale.dto.ItemAndSaleInfoDTO;

public class SaleTest {

    @Test
    public void testRegisterItemInSaleIfFirstItem() {
        Sale instance = new Sale();
        ItemDTO item = new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter");
        ItemAndSaleInfoDTO expResult = new ItemAndSaleInfoDTO("Orange Juice", new Amount(10), "1 liter", new Amount(11));
        ItemAndSaleInfoDTO result = instance.registerItemInSale(item);
        assertTrue(expResult.getName().equals(result.getName()), "Name is wrong");
        assertTrue(expResult.getItemDescription().equals(result.getItemDescription()), "Item description is wrong");
        assertTrue(expResult.getPrice().getAmount() == result.getPrice().getAmount(), "Price is wrong");
        assertTrue(expResult.getRunningTotal().getAmount() == result.getRunningTotal().getAmount(),
                "Running total is wrong");
    }

    @Test
    public void testRegisterItemInSaleIfNotFirstItem() {
        ItemDTO firstItem = new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter");
        Sale instance = new Sale();
        instance.registerItemInSale(firstItem);
        ItemDTO secondItem = new ItemDTO("Bread", 531632821, new Amount(17), 0.25, "Fresh");
        ItemAndSaleInfoDTO expResult = new ItemAndSaleInfoDTO("Bread", new Amount(17), "Fresh", new Amount(32));
        ItemAndSaleInfoDTO result = instance.registerItemInSale(secondItem);
        assertTrue(expResult.getName().equals(result.getName()), "Name is wrong");
        assertTrue(expResult.getItemDescription().equals(result.getItemDescription()), "Item description is wrong");
        assertTrue(expResult.getPrice().getAmount() == result.getPrice().getAmount(), "Price is wrong");
        assertTrue(expResult.getRunningTotal().getAmount() == result.getRunningTotal().getAmount(),
                "Running total is wrong");
    }

    @Test
    public void testRegisterItemInSaleIfItemIdentifierAlreadyEntered() {
        Sale instance = new Sale();
        instance.registerItemInSale(new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter"));
        instance.registerItemInSale(new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter"));
        int expResult = 2;
        int result = instance.getQuantityOfItems().get(0);
        assertEquals(expResult, result, "Quantity of item is not updated correctly");
    }

    @Test
    public void testPaymentForSale() {
        Sale instance = new Sale();
        instance.setTotalCost(new Amount(65));
        Amount amountPaidByCustomer = new Amount(95);
        instance.paymentForSale(amountPaidByCustomer);
        Amount expResultAmountPaid = new Amount(95);
        Amount expResultChange = new Amount(30);
        assertEquals(expResultAmountPaid.getAmount(), instance.getAmountPaid().getAmount(), "Amount paid is not correct");
        assertEquals(expResultChange.getAmount(), instance.getChange().getAmount(), "Change is not correct");

    }

    @Test
    public void testNewReceipt() {
        Sale instance = new Sale();
        instance.registerItemInSale(new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter"));
        Amount amountPaidByCustomer = new Amount(20);
        instance.paymentForSale(amountPaidByCustomer);
        String receipt = instance.newReceipt();

        assertTrue(receipt.contains(instance.getDateAndTimeOfSale().toString()), "Wrong sale time specified on the receipt");
        assertTrue(receipt.contains("Amount paid: 20 SEK"), "Wrong amount paid specified on the receipt");
        assertTrue(receipt.contains("Total cost: 11 SEK"), "Wrong total cost on the receipt");
        assertTrue(receipt.contains("Change: 9 SEK"), "Wrong change specified on the receipt");
        assertTrue(receipt.contains("Orange Juice"), "Item bought in sale is not on the receipt");
        assertTrue(receipt.contains("VAT for entire sale: 1.2 SEK"), "VAT for entire sale is not correct on the receipt");
    }

}
