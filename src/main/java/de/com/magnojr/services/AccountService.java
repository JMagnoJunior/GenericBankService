package de.com.magnojr.services;

import de.com.magnojr.converters.ConverterService;
import de.com.magnojr.dao.AccountDAO;
import de.com.magnojr.domain.Account;
import de.com.magnojr.dto.AccountCreateDTO;
import de.com.magnojr.exceptions.AccountNotFoundException;
import de.com.magnojr.exceptions.CreateAccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class AccountService implements IAccountService {

    private final AccountDAO accountDAO;

    @Override
    public List<Account> list() {
        return accountDAO.findAll();
    }

    @Override
    public Account get(final UUID accountId) {
        try {
            return accountDAO.findById(accountId);
        } catch (Exception e) {
            log.warn("Account not found {}", accountId, e);
            throw new AccountNotFoundException();
        }
    }

    @Override
    public void create(AccountCreateDTO accountCreateDTO) {
        try {
            Account newAccount = ConverterService.convert(accountCreateDTO);
            accountDAO.save(newAccount);
        } catch (Exception e) {
            log.warn("Could not create the account", accountCreateDTO, e);
            throw new CreateAccountException();
        }
    }
}
