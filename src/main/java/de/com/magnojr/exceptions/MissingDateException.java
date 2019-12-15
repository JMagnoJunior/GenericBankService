package de.com.magnojr.exceptions;

public class MissingDateException extends RuntimeException {

    private static final String REASON = "missing.date";

    public MissingDateException() {
        super(REASON);
    }

}
