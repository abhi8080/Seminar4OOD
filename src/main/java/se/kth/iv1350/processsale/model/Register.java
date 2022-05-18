package se.kth.iv1350.processsale.model;

/**
 * The register used for storing cash.
 */
public class Register {

    private Amount balance;

    /**
     * Creates a new instance.
     */
    public Register() {
        this.balance = new Amount(245);
    }

    /**
     * Stores the amount of cash in the register and updates the balance.
     *
     * @param amountPaidByCustomer The amount of cash that the customer pays.
     */
    public void storeAmount(Amount amountPaidByCustomer) {
        this.balance = new Amount(balance.getAmount() + amountPaidByCustomer.getAmount());
    }

    /**
     *
     * @return The balance.
     */
    public Amount getBalance() {
        return balance;
    }

    /**
     * Checks if an object is an instance of <code>Register</code>.
     *
     * @param otherObject The object to compare with this <code>Register</code>.
     * @return  <code>true</code> if the specified object is an instance of
     * <code>Register</code>, <code>false</code> if it is not.
     */
    @Override
    public boolean equals(Object otherObject) {
        return otherObject instanceof Register;
    }

}
