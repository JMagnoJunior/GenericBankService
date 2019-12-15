package de.com.magnojr.controllers;


import de.com.magnojr.dto.AccountCreateDTO;
import de.com.magnojr.dto.validators.AccountValidator;
import de.com.magnojr.exceptions.AccountNotFoundException;
import de.com.magnojr.services.IAccountService;
import de.com.magnojr.util.JsonUtils;
import io.javalin.http.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
public class AccountController {

    public static String PATH = "/account";

    public final IAccountService accountService;

    public void list(Context ctx) {
        try {
            ctx.result(JsonUtils.toJson(accountService.list()));
        } catch (Exception e) {
            log.error("Error listing accounts", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return;
        }
    }

    public void get(Context ctx) {

        String accountId = ctx.pathParam("accountId");
        try {
            UUID id = UUID.fromString(accountId);
            ctx.result(JsonUtils.toJson(accountService.get(id)));
            ctx.status(HttpStatus.OK_200);
        } catch (AccountNotFoundException e) {
            log.warn("account id not found: {}", accountId, e);
            ctx.status(HttpStatus.NOT_FOUND_404);
            return;
        } catch (Exception e) {
            log.error("The account id is not valid: {}", accountId, e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return;
        }
    }

    public void create(Context ctx) {

        AccountCreateDTO accountCreateDTO = ctx.bodyValidator(AccountCreateDTO.class)
                .check(AccountValidator.validateUser, AccountValidator.VALIDATE_USER_MESSAGE)
                .check(AccountValidator.validateAmount, AccountValidator.VALIDATE_AMOUNT_MESSAGE)
                .get();

        try {
            accountService.create(accountCreateDTO);
            ctx.status(HttpStatus.CREATED_201);
            return;
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return;
        }

    }

}
