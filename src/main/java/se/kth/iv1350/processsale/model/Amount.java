package se.kth.iv1350.processsale.model;

/**
 * Represents an amount of money.
 */
public final class Amount {

    private final int amount;

    /**
     * Creates a new instance.
     *
     * @param amount Amount of money entered by the user.
     */
    public Amount(int amount) {
        this.amount = amount;
    }

    /**
     *
     * @return The cash amount of this object.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     *
     * @return A string representation of an amount.
     */
    @Override
    public String toString() {
        return "" + this.amount;
    }

}
