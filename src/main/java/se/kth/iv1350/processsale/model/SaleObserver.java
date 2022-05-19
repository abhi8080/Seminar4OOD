package se.kth.iv1350.processsale.model;

/**
 * A class that wants to be notified when a new sale is made has to implement
 * this interface.
 */
public interface SaleObserver {

    void newSale(Amount costOfSale);

}
