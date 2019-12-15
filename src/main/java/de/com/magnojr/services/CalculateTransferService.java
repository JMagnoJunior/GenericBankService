package de.com.magnojr.services;

import de.com.magnojr.domain.Account;
import de.com.magnojr.domain.Transfer;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class CalculateTransferService {

    public static Transfer calculate(final BigDecimal transferredValue, Account fromAccount, Account toAccount) {

        final BigDecimal fromValue = new BigDecimal(fromAccount.getAmount());

        if (transferredValue.compareTo(fromValue) > 0) {
            // throw error
        }

        fromAccount.subtract(transferredValue);
        toAccount.add(transferredValue);

        return Transfer.builder()
                .id(UUID.randomUUID())
                .date(LocalDateTime.now())
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .value(transferredValue.toString())
                .build();
    }
}
