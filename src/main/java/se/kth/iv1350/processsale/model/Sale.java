package se.kth.iv1350.processsale.model;

import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.discount.Discount;

import java.time.LocalDateTime;
import se.kth.iv1350.processsale.dto.ItemAndSaleInfoDTO;
import java.util.ArrayList;

/**
 * Represents a sale transaction in a retail store
 */
public class Sale {

    private Amount totalCost;
    private LocalDateTime dateAndTimeOfSale;
    private ArrayList<Integer> quantityOfItems = new ArrayList<Integer>();
    private int numberOfitems;
    private double vatForEntireSale;
    private Amount amountPaid;
    private Amount change;
    private ArrayList<ItemDTO> itemsRegistredInSale = new ArrayList<ItemDTO>();
    private ArrayList<SaleObserver> saleObservers = new ArrayList<SaleObserver>();

    /**
     *
     * @return Number of items bought in the sale.
     */
    public int getNumberOfItems() {
        return this.numberOfitems;
    }

    /**
     *
     * @return The amount of change that the customer receives.
     */
    public Amount getChange() {
        return change;
    }

    ArrayList<Integer> getQuantityOfItems() {
        return quantityOfItems;
    }

    /**
     *
     * @return The amount of cash that the customer pays.
     */
    public Amount getAmountPaid() {
        return amountPaid;
    }

    /**
     * Creates a new instance.
     */
    public Sale() {
        this.dateAndTimeOfSale = LocalDateTime.now();
        this.amountPaid = new Amount(0);
        this.change = new Amount(0);
        this.totalCost = new Amount(0);
    }

    /**
     *
     * @return Date and time of when the sale started.
     */
    public LocalDateTime getDateAndTimeOfSale() {
        return dateAndTimeOfSale;
    }

    /**
     * Creates a receipt with all the necessary information about the sale.
     *
     * @return The receipt.
     */
    public String newReceipt() {
        Receipt receipt = new Receipt(this);
        return receipt.createReceipt();
    }

    private boolean isCustomerEligibleForDiscount(String customerID) {
        return customerID.chars().count() == 12;
    }

    /**
     * Retrieves a discount based on which discount the customer is eligible
     * for.
     *
     * @param customerID Identification number of customer.
     * @param discountType The type of discount the customer is eligible for.
     * @return Total cost after discount.
     */
    public Amount retrieveDiscount(String customerID, Discount discountType) {
        if (isCustomerEligibleForDiscount(customerID)) {
            totalCost = discountType.calculateDiscount(totalCost, itemsRegistredInSale);
        }

        return totalCost;
    }

    /**
     * Registers the amount of cash that the customer pays and calculates
     * change.
     *
     * @param amountPaidByCustomer The amount of cash that the customer pays.
     */
    public void paymentForSale(Amount amountPaidByCustomer) {
        this.amountPaid = new Amount(amountPaidByCustomer.getAmount());
        notifyObservers();
        this.change = new Amount(amountPaidByCustomer.getAmount() - this.totalCost.getAmount());
    }

    private void notifyObservers() {
        for (SaleObserver saleObs : this.saleObservers) {
            saleObs.newSale(totalCost);
        }

    }

    /**
     * All sale observers are added to a list and notified when a new sale is
     * made.
     *
     * @param saleObservers The observers which will be notified.
     */
    public void addSaleObservers(ArrayList<SaleObserver> saleObservers) {
        this.saleObservers.addAll(saleObservers);
    }

    /**
     *
     * @return All items registred in the sale.
     */
    public ArrayList<ItemDTO> getItemsRegistredInSale() {
        return this.itemsRegistredInSale;
    }

    private void calculateRunningTotal(ItemDTO item, double VAT) {
        int runningTotal;

        runningTotal = (int) (totalCost.getAmount() + item.getPrice().getAmount() + VAT);

        this.totalCost = new Amount(runningTotal);

    }

    private void calculateNumberOfItems() {
        for (int i = 0; i < quantityOfItems.size(); i++) {
            numberOfitems += quantityOfItems.get(i);
        }
    }

    double getVatForEntireSale() {
        return vatForEntireSale;
    }

    private double addVAT(ItemDTO item) {
        double VAT = (item.getPrice().getAmount() * item.getVATRate());
        this.vatForEntireSale += VAT;
        return VAT;
    }

    private boolean isItemIdentifierAlreadyEntered(int itemIdentifier) {
        for (ItemDTO item : itemsRegistredInSale) {
            if (itemIdentifier == item.getItemIdentifier()) {
                return true;
            }
        }

        return false;

    }

    private void updateQuantityOfItem(ItemDTO item) {
        int index = itemsRegistredInSale.indexOf(item);
        quantityOfItems.add(index, quantityOfItems.get(index) + 1);
    }

    /**
     * Registers an item in the sale and calculates running total. An item is
     * registred in the sale once, and the next time an item with the same
     * identifier is bought, the quantity of the item increases.
     *
     * @param item The item to be registered in the sale.
     * @return Item description and price of the item, and the running total.
     */
    public ItemAndSaleInfoDTO registerItemInSale(ItemDTO item) {

        if (isItemIdentifierAlreadyEntered(item.getItemIdentifier())) {
            updateQuantityOfItem(item);
        } else {
            itemsRegistredInSale.add(item);
            quantityOfItems.add(1);
        }
        double VAT = addVAT(item);
        calculateRunningTotal(item, VAT);
        calculateNumberOfItems();
        ItemAndSaleInfoDTO showItemAndSaleInfo = new ItemAndSaleInfoDTO(item.getName(), item.getPrice(), item.getItemDescription(),
                totalCost);

        return showItemAndSaleInfo;
    }

    /**
     *
     * @return Total cost of the sale.
     */
    public Amount getTotalCost() {
        return totalCost;
    }

    void setTotalCost(Amount totalCost) {
        this.totalCost = totalCost;
    }

}
