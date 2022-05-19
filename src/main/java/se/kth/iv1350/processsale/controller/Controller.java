package se.kth.iv1350.processsale.controller;

import java.util.ArrayList;

import se.kth.iv1350.processsale.dto.ItemAndSaleInfoDTO;
import se.kth.iv1350.processsale.integration.AccountingSystem;
import se.kth.iv1350.processsale.integration.InvalidItemIdentifierException;
import se.kth.iv1350.processsale.integration.InventorySystem;
import se.kth.iv1350.processsale.integration.InventorySystemException;
import se.kth.iv1350.processsale.integration.Printer;
import se.kth.iv1350.processsale.integration.SystemCreator;
import se.kth.iv1350.processsale.model.Amount;
import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.Register;
import se.kth.iv1350.processsale.model.Sale;
import se.kth.iv1350.processsale.model.SaleObserver;
import se.kth.iv1350.processsale.model.discount.PercentageDiscount;
import se.kth.iv1350.processsale.model.discount.QuantityDiscount;

/**
 * This is the application's controller, which acts as a coordinator between the
 * View and the Model.
 */
public final class Controller {

    private Sale sale;
    private InventorySystem inventorySystem;
    private AccountingSystem accountingSystem;
    private Printer printer;
    private Register cashRegister;
    private ArrayList<SaleObserver> saleObservers = new ArrayList<SaleObserver>();

    /**
     * Creates a new instance
     *
     * @param systemCreator Creates all the systems in the integration layer
     * @param printer The printer used to print the receipt
     */
    public Controller(SystemCreator systemCreator, Printer printer) {
        this.inventorySystem = systemCreator.getInventorySystem();
        this.accountingSystem = systemCreator.getAccountingSystem();
        this.printer = printer;
        this.cashRegister = new Register();
    }

    /**
     * Initializes the sale
     */
    public void startSale() {
        sale = new Sale();
        sale.addSaleObservers(saleObservers);
    }

    /**
     * Ends the sale
     *
     * @return The total cost of the sale
     */
    public Amount endSale() {
        return sale.getTotalCost();
    }

    /**
     * Discount request is signaled if customer is eligible for a discount.
     *
     * @param customerID Identification number of customer.
     * @return Total cost after discount.
     */
    public Amount discountRequest(String customerID) {
        if (sale.getNumberOfItems() > 4) {
            sale.retrieveDiscount(customerID, new QuantityDiscount());
        }

        Amount totalCost = sale.retrieveDiscount(customerID, new PercentageDiscount());
        return totalCost;
    }

    /**
     * Scans the item.
     *
     * @param itemIdentifier This is the itemIdentifier for the item, which is
     * entered to retreive information about it and register it in the sale.
     * @return Information about the item and the running total.
     * @throws InvalidItemIdentifierException if an item could not be found
     * using the specified item identifier.
     * @throws OperationFailedException if unable to retrieve item information
     * for any other reason than the item identifier being invalid.
     */
    public ItemAndSaleInfoDTO scanItem(int itemIdentifier) throws InvalidItemIdentifierException, OperationFailedException {
        try {
            ItemDTO item = inventorySystem.retrieveItem(itemIdentifier);
            ItemAndSaleInfoDTO itemAndSaleInfo = sale.registerItemInSale(item);
            return itemAndSaleInfo;
        } catch (InventorySystemException invExc) {
            throw new OperationFailedException("Could not scan the item.", invExc);
        }
    }

    /**
     * Handles the operations happening during and after payment.
     *
     * @param amountPaidByCustomer The amount of cash that the customer pays.
     */
    public void pay(Amount amountPaidByCustomer) {
        cashRegister.storeAmount(amountPaidByCustomer);
        sale.paymentForSale(amountPaidByCustomer);
        Sale saleInformation = getSale();
        accountingSystem.accounting(saleInformation);
        inventorySystem.updateInventory(saleInformation);
        String receipt = sale.newReceipt();
        printer.printReceipt(receipt);

    }

    InventorySystem getInventorySystem() {
        return inventorySystem;
    }

    AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    Register getCashRegister() {
        return cashRegister;
    }

    Sale getSale() {
        return sale;
    }

    /**
     * All sale observers are added to an Arraylist and passed to the Sale
     * class.
     *
     * @param saleObserver The sale observer to be added to the ArrayList.
     */
    public void addSaleObserver(SaleObserver saleObserver) {
        saleObservers.add(saleObserver);
    }

}
