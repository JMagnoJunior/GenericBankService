package de.com.magnojr.exceptions;

public class AccountNotFoundException extends RuntimeException{

    public static final String REASON = "account.not.found";
    public AccountNotFoundException() {
        super(REASON);
    }

}
