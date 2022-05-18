package se.kth.iv1350.processsale.view;

import java.util.ArrayList;

import se.kth.iv1350.processsale.model.Amount;
import se.kth.iv1350.processsale.model.SaleObserver;
class TotalRevenueView implements SaleObserver{
    private ArrayList<Amount> purchases = new ArrayList<Amount>();
    
    TotalRevenueView()
    {
        
    }
    
    /**
     * Invoked when a new sale is made. 
     * @param costOfSale The cost of the sale. 
     */
    @Override
    public void newSale(Amount costOfSale)
    {
        purchases.add(costOfSale);
        printTotalRevenue();
    }
    private void printTotalRevenue()
    {
        Amount totalRevenue = new Amount(0);
        for(Amount amount : purchases)
        {
            totalRevenue = new Amount(totalRevenue.getAmount() + amount.getAmount());
        }
        System.out.println("------------------");
        System.out.println("Total income: " + totalRevenue + "SEK" );
        System.out.println("------------------");
    }
}
