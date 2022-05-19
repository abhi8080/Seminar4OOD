package se.kth.iv1350.processsale.integration;

/**
 *
 * Responsible for calling a printer to print the receipt for the sale.
 */
public class Printer {

    /**
     * Prints the content of the receipt to <code>System.out</code>.
     *
     * @param receipt The receipt.
     */
    public void printReceipt(String receipt) {
        System.out.println(receipt);
    }

}
