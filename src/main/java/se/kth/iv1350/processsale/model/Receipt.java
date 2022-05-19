package se.kth.iv1350.processsale.model;

/**
 *
 * Receipt of the sale.
 */
public class Receipt {

    private Sale sale;

    /**
     * Creates a new instance
     *
     * @param sale Information about the sale that needs to be on the receipt.
     */
    Receipt(Sale sale) {
        this.sale = sale;
    }

    /**
     * Creates a string represenation of the receipt, with all necessary
     * information included.
     *
     * @return A string representation of the receipt.
     */
    public String createReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("        SALE");
        receipt.append("\n\n");

        receipt.append("Date and time of sale: " + sale.getDateAndTimeOfSale().toString());
        receipt.append("\n\n");

        for (int i = 0; i < sale.getItemsRegistredInSale().size(); i++) {
            receipt.append(sale.getItemsRegistredInSale().get(i).getName() + "     " + sale.getQuantityOfItems().get(i) + " x " + sale.getItemsRegistredInSale().get(i).getPrice() + " SEK");
            receipt.append("\n");

        }
        receipt.append("\n");

        receipt.append("VAT for entire sale: " + sale.getVatForEntireSale() + " SEK");
        receipt.append("\n");
        receipt.append("Total cost: " + sale.getTotalCost() + " SEK");
        receipt.append("\n");
        receipt.append("Amount paid: " + sale.getAmountPaid() + " SEK");
        receipt.append("\n");
        receipt.append("Change: " + sale.getChange() + " SEK");

        return receipt.toString();

    }

}
