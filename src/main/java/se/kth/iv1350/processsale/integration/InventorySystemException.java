package se.kth.iv1350.processsale.integration;

/**
 * Thrown when a failure occurs when performing an operation in the
 * <code>InventorySystem</code>.
 */
public class InventorySystemException extends RuntimeException {

    InventorySystemException(String message) {
        super(message);
    }

}
