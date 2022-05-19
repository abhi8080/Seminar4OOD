package se.kth.iv1350.processsale.controller;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.kth.iv1350.processsale.dto.ItemAndSaleInfoDTO;
import se.kth.iv1350.processsale.integration.Printer;
import se.kth.iv1350.processsale.integration.SystemCreator;
import se.kth.iv1350.processsale.model.Amount;
import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.integration.InvalidItemIdentifierException;
import se.kth.iv1350.processsale.integration.InventorySystemException;
import se.kth.iv1350.processsale.model.Register;

public class ControllerTest {

    @Test
    public void testControllerConstructor() {
        Printer printer = new Printer();
        SystemCreator systemCreator = new SystemCreator();
        Controller instance = new Controller(systemCreator, printer);
        assertTrue(instance.getAccountingSystem().equals(systemCreator.getAccountingSystem()), "Two AccountingSystem instances are not equal");
        assertTrue(instance.getInventorySystem().equals(systemCreator.getInventorySystem()), "Two InventorySystem instances are not equal");
        assertTrue(instance.getCashRegister().equals(new Register()), "Two Register instances are not equal");
    }

    @Test
    public void testStartSale() {
        Printer printer = new Printer();
        SystemCreator systemCreator = new SystemCreator();
        Controller instance = new Controller(systemCreator, printer);
        instance.startSale();
        LocalDateTime saleStartTime = LocalDateTime.now();
        Amount expTotalCost = new Amount(0);
        Amount totalCost = instance.getSale().getTotalCost();
        Amount expAmountPaid = new Amount(0);
        Amount amountPaid = instance.getSale().getAmountPaid();
        Amount expChange = new Amount(0);
        Amount change = instance.getSale().getChange();

        assertTrue(saleStartTime.getMinute() == instance.getSale().getDateAndTimeOfSale().getMinute() && saleStartTime.getHour() == instance.getSale().getDateAndTimeOfSale().getHour(), "The time the sale started is wrong");
        assertEquals(expTotalCost.getAmount(), totalCost.getAmount(), "Total cost of the sale is incorrect at sale initiaion");
        assertEquals(expAmountPaid.getAmount(), amountPaid.getAmount(), "Amount paid is not zero at sale initiation");
        assertEquals(expChange.getAmount(), change.getAmount(), "Change is not zero at sale initiation");

    }

    @Test
    public void testEndSaleIfOnlyOneItemPurchased() {
        Printer printer = new Printer();
        SystemCreator systemCreator = new SystemCreator();
        Controller instance = new Controller(systemCreator, printer);
        instance.startSale();
        instance.getSale().registerItemInSale(new ItemDTO("Orange Juice", 934632865, new Amount(19), 0.12, "1 liter"));

        Amount expResult = new Amount(21);
        Amount result = instance.endSale();
        assertEquals(expResult.getAmount(), result.getAmount(), "Total cost is wrong");
    }

    @Test
    public void testEndSaleIfMultipleItemsPurchased() {
        Printer printer = new Printer();
        SystemCreator systemCreator = new SystemCreator();
        Controller instance = new Controller(systemCreator, printer);
        instance.startSale();
        instance.getSale().registerItemInSale(new ItemDTO("Orange Juice", 934632865, new Amount(19), 0.12, "1 liter"));
        instance.getSale().registerItemInSale(new ItemDTO("Cheese", 431632620, new Amount(26), 0.25, "Italian"));
        instance.getSale().registerItemInSale(new ItemDTO("Onions", 171172841, new Amount(14), 0.12, "Red"));

        Amount expResult = new Amount(68);
        Amount result = instance.endSale();
        assertEquals(expResult.getAmount(), result.getAmount(), "Total cost is wrong");
    }

    @Test
    public void testScanItem() {
        SystemCreator systemCreator = new SystemCreator();
        Printer printer = new Printer();
        Controller instance = new Controller(systemCreator, printer);
        instance.startSale();
        ItemAndSaleInfoDTO expResult = new ItemAndSaleInfoDTO("Orange Juice", new Amount(10), "1 liter", new Amount(11));
        ItemAndSaleInfoDTO result = null;
        try {

            result = instance.scanItem(934632865);
        } catch (Exception exception) {
            fail("Got exception");
            exception.printStackTrace();
        }
        assertTrue(expResult.getName().equals(result.getName()), "Name is wrong");
        assertTrue(expResult.getItemDescription().equals(result.getItemDescription()), " Item description is wrong");
        assertEquals(expResult.getPrice().getAmount(), result.getPrice().getAmount(), " Price is wrong");
        assertEquals(expResult.getRunningTotal().getAmount(), result.getRunningTotal().getAmount(), " Running total is wrong");

    }

    @Test
    public void testScanItemIfItemIdentifierInvalid() {
        SystemCreator systemCreator = new SystemCreator();
        Printer printer = new Printer();
        Controller instance = new Controller(systemCreator, printer);
        int invalidItemIdentifier = 111111111;
        try {
            instance.scanItem(invalidItemIdentifier);
            fail("Found an item that does not exist in the inventory catalog");
        } catch (InvalidItemIdentifierException exception) {
            assertTrue(exception.getMessage().contains("" + invalidItemIdentifier), "Wrong exception message, does not contain the invalid identifier.");
            exception.printStackTrace();
        } catch (OperationFailedException exception) {
            fail("Wrong exception thrown");
            exception.printStackTrace();
        }
    }

    @Test
    public void testScanItemIfUnableToScanItem() {
        SystemCreator systemCreator = new SystemCreator();
        Printer printer = new Printer();
        Controller instance = new Controller(systemCreator, printer);
        int itemIdentifierThatCausesDatabaseFailure = 431632620;
        try {
            instance.scanItem(itemIdentifierThatCausesDatabaseFailure);
            fail("Item identifer did not cause a database failure");
        } catch (OperationFailedException exception) {
            assertTrue(exception.getCause() instanceof InventorySystemException, "Wrong root cause, expected InventorySystemException but got " + exception.getCause().
                    getClass().getCanonicalName());
            assertTrue(exception.getMessage().contains("Could not scan the item"), "Wrong exception message: " + exception.getMessage());
        } catch (InvalidItemIdentifierException exception) {
            fail("Wrong exception was thrown");
            exception.printStackTrace();
        }
    }

    @Test
    public void testPay() {
        Amount amountPaidByCustomer = new Amount(48);
        SystemCreator systemCreator = new SystemCreator();
        Printer printer = new Printer();
        Controller instance = new Controller(systemCreator, printer);
        instance.startSale();

        instance.getSale().registerItemInSale(new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter"));

        instance.pay(amountPaidByCustomer);

        Amount expResultForStoreAmount = new Amount(293);
        Amount resultForStoreAmount = instance.getCashRegister().getBalance();

        Amount expResultAmountPaid = amountPaidByCustomer;
        Amount resultAmountPaid = instance.getSale().getAmountPaid();

        Amount expResultTotalChange = new Amount(37);
        Amount resultTotalChange = instance.getSale().getChange();

        String receipt = instance.getSale().newReceipt();
        assertTrue(receipt.contains(instance.getSale().getDateAndTimeOfSale().toString()), "Wrong sale time specified on the receipt");
        assertTrue(receipt.contains("Amount paid: 48 SEK"), "Wrong amount paid specified on the receipt");
        assertTrue(receipt.contains("Total cost: 11 SEK"), "Wrong total cost on the receipt");
        assertTrue(receipt.contains("Change: 37 SEK"), "Wrong change specified on the receipt");
        assertTrue(receipt.contains("Orange Juice"), "Item bought in sale is not on the receipt");
        assertTrue(receipt.contains("VAT for entire sale: 1.2 SEK"), "VAT for entire sale is not correct on the receipt");

        assertEquals(expResultForStoreAmount.getAmount(), resultForStoreAmount.getAmount(), "Balance is not updated correctly in the register");
        assertEquals(expResultAmountPaid.getAmount(), resultAmountPaid.getAmount(), "Amount paid is not correct");
        assertEquals(expResultTotalChange.getAmount(), resultTotalChange.getAmount(), "Change is not correct");
    }

    public void testPayIfAmountPaidLessThanTotalCost() {
        Amount amountPaidByCustomer = new Amount(3);
        SystemCreator systemCreator = new SystemCreator();
        Printer printer = new Printer();
        Controller instance = new Controller(systemCreator, printer);
        instance.startSale();

        instance.getSale().registerItemInSale(new ItemDTO("Orange Juice", 934632865, new Amount(10), 0.12, "1 liter"));

        instance.pay(amountPaidByCustomer);

        Amount expResultForStoreAmount = new Amount(248);
        Amount resultForStoreAmount = instance.getCashRegister().getBalance();

        Amount expResultAmountPaid = amountPaidByCustomer;
        Amount resultAmountPaid = instance.getSale().getAmountPaid();

        Amount expResultTotalChange = new Amount(-8);
        Amount resultTotalChange = instance.getSale().getChange();

        String receipt = instance.getSale().newReceipt();
        assertTrue(receipt.contains(instance.getSale().getDateAndTimeOfSale().toString()), "Wrong sale time specified on the receipt");
        assertTrue(receipt.contains("Amount paid: 3 SEK"), "Wrong amount paid specified on the receipt");
        assertTrue(receipt.contains("Total cost: 11 SEK"), "Wrong total cost on the receipt");
        assertTrue(receipt.contains("Change: -8 SEK"), "Wrong change specified on the receipt");
        assertTrue(receipt.contains("Orange Juice"), "Item bought in sale is not on the receipt");
        assertTrue(receipt.contains("VAT for entire sale: 1.2 SEK"), "VAT for entire sale is not correct on the receipt");

        assertEquals(expResultForStoreAmount.getAmount(), resultForStoreAmount.getAmount(), "Balance is not updated correctly in the register");
        assertEquals(expResultAmountPaid.getAmount(), resultAmountPaid.getAmount(), "Amount paid is not correct");
        assertEquals(expResultTotalChange.getAmount(), resultTotalChange.getAmount(), "Change is not correct");
    }

}
