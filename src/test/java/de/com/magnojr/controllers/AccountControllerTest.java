package de.com.magnojr.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.com.magnojr.dao.AccountDAO;
import de.com.magnojr.domain.Account;
import de.com.magnojr.dto.AccountCreateDTO;
import de.com.magnojr.services.AccountService;
import de.com.magnojr.services.IAccountService;
import de.com.magnojr.util.DataProvider;
import io.javalin.core.validation.Validator;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;


public class AccountControllerTest {

    private static AccountController accountController;

    private static Context context = mock(Context.class);
    private static AccountDAO accountDAO = mock(AccountDAO.class);
    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {

        IAccountService accountService = new AccountService(accountDAO);
        accountController = new AccountController(accountService);

    }

    @Test
    public void shouldGetAccountById() {

        final UUID employeeId = UUID.randomUUID();

        Account account = DataProvider.buildValidAccount(employeeId);

        when(accountDAO.findById(employeeId)).thenReturn(account);
        when(context.pathParam("accountId")).thenReturn(employeeId.toString());

        accountController.get(context);

        verify(context).result(toJson(account));

    }

    @Test
    public void shouldGetAccountByIdShouldReturn404WhenAccountNotFound() {

        final UUID employeeId = UUID.randomUUID();

        when(accountDAO.findById(employeeId)).thenThrow(new RuntimeException());
        when(context.pathParam("accountId")).thenReturn(employeeId.toString());

        accountController.get(context);

        verify(context).status(HttpStatus.NOT_FOUND_404);

    }

    @Test
    public void shouldListAllAccounts() {

        List<Account> accounts = Arrays.asList(
                DataProvider.buildValidAccount(UUID.randomUUID()),
                DataProvider.buildValidAccount(UUID.randomUUID())
        );

        when(accountDAO.findAll()).thenReturn(accounts);

        accountController.list(context);

        verify(context).result(toJson(accounts));

    }

    @Test
    public void shouldCreateAccount() {

        AccountCreateDTO accountCreateDTO = DataProvider.buildValidAccountCreateDTO();
        mockValidators(context, accountCreateDTO);

        accountController.create(context);

    }

    private void mockValidators(Context context, Object validObject) {
        Validator validatorMock = mock(Validator.class);
        when(context.bodyValidator(any())).thenReturn(validatorMock);
        when(validatorMock.check(any(), any())).thenReturn(validatorMock);
        when(validatorMock.get()).thenReturn(validObject);
    }

    private String toJson(Object transferResponseDTOS) {
        try {
            return mapper.writeValueAsString(transferResponseDTOS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
