package de.com.magnojr.exceptions;

public class TransferLimitException extends RuntimeException{

    public static final String REASON = "transfer.limit.exception";
    public TransferLimitException() {
        super(REASON);
    }

}
