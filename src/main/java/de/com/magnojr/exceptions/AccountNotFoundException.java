package de.com.magnojr.exceptions;

public class AccountNotFoundException extends RuntimeException {

    private static final String REASON = "account.not.found";

    public AccountNotFoundException() {
        super(REASON);
    }

}
