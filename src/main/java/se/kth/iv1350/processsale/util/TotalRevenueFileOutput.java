package se.kth.iv1350.processsale.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import se.kth.iv1350.processsale.model.Amount;
import se.kth.iv1350.processsale.model.SaleObserver;

/**
 * This class is responsible for writing the total revenue to a file.
 */
public class TotalRevenueFileOutput implements SaleObserver {

    private ArrayList<Amount> costOfSales = new ArrayList<Amount>();

    private PrintWriter logStream;

    /**
     * Creates a new instance.
     */
    public TotalRevenueFileOutput() {
        try {
            logStream = new PrintWriter(new FileWriter("log-totalIncome.txt"), true);
        } catch (IOException ioe) {
            System.out.println("CAN NOT LOG TO FILE");
            ioe.printStackTrace();
        }
    }

    /**
     * Invoked when a new sale is made.
     *
     * @param costOfSale The cost of the sale.
     */
    @Override
    public void newSale(Amount costOfSale) {
        costOfSales.add(costOfSale);
        log();
    }

    private void log() {
        Amount totalRevenue = new Amount(0);
        for (Amount amount : costOfSales) {
            totalRevenue = new Amount(totalRevenue.getAmount() + amount.getAmount());
        }

        logStream.println("Total income: " + totalRevenue + " SEK");
    }

}
