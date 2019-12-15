package de.com.magnojr.converters;

import de.com.magnojr.domain.Account;
import de.com.magnojr.domain.User;
import de.com.magnojr.dto.AccountCreateDTO;

import java.util.UUID;

public class ConverterService {

    public static Account convert(AccountCreateDTO accountCreateDTO) {

        User user = User.builder()
                .id(UUID.randomUUID())
                .name(accountCreateDTO.getUser().getName())
                .build();

        return Account.builder()
                .id(UUID.randomUUID())
                .amount(accountCreateDTO.getAmount().toString())
                .user(user)
                .build();
    }
}
