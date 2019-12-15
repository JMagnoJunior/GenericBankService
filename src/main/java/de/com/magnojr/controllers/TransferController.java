package de.com.magnojr.controllers;


import de.com.magnojr.dto.TransferDTO;
import de.com.magnojr.dto.validators.TransferValidator;
import de.com.magnojr.exceptions.AccountNotFoundException;
import de.com.magnojr.services.ITransferService;
import de.com.magnojr.util.DateTimeUtil;
import de.com.magnojr.util.JsonUtils;
import io.javalin.http.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;
import org.h2.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
public class TransferController {

    public static String PATH = "/transfer";

    public final ITransferService transferService;

    public void transfer(Context ctx) {
        TransferDTO transferDTO = ctx.bodyValidator(TransferDTO.class)
                .check(TransferValidator.validateTransferValue, TransferValidator.VALIDATE_TRANSFER_VALUE_MESSAGE)
                .get();
        try {
            transferService.transfer(transferDTO.getValue(), transferDTO.getFrom(), transferDTO.getTo());

        } catch (AccountNotFoundException e) {
            ctx.status(HttpStatus.NOT_FOUND_404);
            return;
        } catch (Exception e) {
            log.error("Error in transfer process: {} ", transferDTO, e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return;
        }
        ctx.status(HttpStatus.ACCEPTED_202);
    }

    public void list(Context ctx) {
        final String fromDate = ctx.queryParam("date");
        final Optional<LocalDate> date = !StringUtils.isNullOrEmpty(fromDate) ?
                Optional.of(DateTimeUtil.parseLocalDateTime(fromDate)) : Optional.empty();

        ctx.result(JsonUtils.toJson(transferService.listTransferResponsesDTO(date)));
        ctx.status(HttpStatus.OK_200);
    }

}
