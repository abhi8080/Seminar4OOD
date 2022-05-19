package se.kth.iv1350.processsale.integration;

import se.kth.iv1350.processsale.model.Sale;

/**
 *
 * Contains all calls to an external accounting system.
 */
public class AccountingSystem {

    AccountingSystem() {
    }

    /**
     * Handles the accounting.
     *
     * @param saleInformation The sale information needed for accounting.
     */
    public void accounting(Sale saleInformation) {

    }

    /**
     * Checks if an object is an instance of <code>AccountingSystem</code>.
     *
     * @param otherObject The object to compare with this
     * <code>AccountingSystem</code>.
     * @return  <code>true</code> if the specified object is an instance of
     * <code>AccountingSystem</code>, <code>false</code> if it is not.
     */
    @Override
    public boolean equals(Object otherObject) {
        return otherObject instanceof AccountingSystem;
    }
}
