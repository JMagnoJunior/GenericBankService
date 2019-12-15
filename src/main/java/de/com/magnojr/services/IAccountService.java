package de.com.magnojr.services;

import de.com.magnojr.domain.Account;
import de.com.magnojr.dto.AccountCreateDTO;

import java.util.List;
import java.util.UUID;

public interface IAccountService {

    List<Account> list();
    Account get(UUID id);
    void create(AccountCreateDTO accountCreateDTO);

}
