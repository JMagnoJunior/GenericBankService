package de.com.magnojr.exceptions;

public class CreateAccountException extends RuntimeException {

    private static final String REASON = "create.account";

    public CreateAccountException() {
        super(REASON);
    }

}
