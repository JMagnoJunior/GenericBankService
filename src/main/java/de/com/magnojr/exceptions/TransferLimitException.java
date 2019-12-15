package de.com.magnojr.exceptions;

public class TransferLimitException extends RuntimeException {

    private static final String REASON = "transfer.limit.exception";

    public TransferLimitException() {
        super(REASON);
    }

}
