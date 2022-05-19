package se.kth.iv1350.processsale.controller;

/**
 * An exception which is thrown when an operation is not successful.
 */
public class OperationFailedException extends Exception {

    /**
     * Creates a new instance.
     *
     * @param message The exception message.
     * @param cause Cause of this exception.
     */
    public OperationFailedException(String message, Exception cause) {
        super(message, cause);
    }
}
