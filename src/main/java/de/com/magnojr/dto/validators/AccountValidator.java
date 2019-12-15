package de.com.magnojr.dto.validators;

import de.com.magnojr.dto.AccountCreateDTO;
import kotlin.jvm.functions.Function1;
import org.h2.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountValidator {

    public final static Function1<? super AccountCreateDTO, Boolean> validateUser = obj -> {
        boolean valid = Optional.ofNullable(obj.getUser())
                .map(userCreateDTO -> {
                    if (StringUtils.isNullOrEmpty(userCreateDTO.getName())) {
                        return false;
                    }
                    return true;
                })
                .orElse(false);
        return valid;
    };
    public final static String VALIDATE_USER_MESSAGE = "User data for the account is missing";

    public final static Function1<? super AccountCreateDTO, Boolean> validateAmount = obj ->
            (obj.getAmount().compareTo(BigDecimal.ZERO) < 0) ? false : true;
    public final static String VALIDATE_AMOUNT_MESSAGE = "Amount is not valid. It cannot be negative";

}
