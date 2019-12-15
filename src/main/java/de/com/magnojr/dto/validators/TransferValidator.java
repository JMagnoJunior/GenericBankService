package de.com.magnojr.dto.validators;

import de.com.magnojr.dto.TransferDTO;
import kotlin.jvm.functions.Function1;

import java.math.BigDecimal;

public class TransferValidator {

    public final static Function1<? super TransferDTO, Boolean> validateTransferValue = obj -> obj.getValue().compareTo(BigDecimal.ZERO) > 0;
    public final static String VALIDATE_TRANSFER_VALUE_MESSAGE = "Transfer values should be positives";

}
