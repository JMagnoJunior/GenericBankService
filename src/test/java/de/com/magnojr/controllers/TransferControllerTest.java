package de.com.magnojr.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.com.magnojr.dao.AccountDAO;
import de.com.magnojr.dao.TransferDAO;
import de.com.magnojr.dto.TransferDTO;
import de.com.magnojr.dto.TransferResponseDTO;
import de.com.magnojr.services.AccountService;
import de.com.magnojr.services.IAccountService;
import de.com.magnojr.services.ITransferService;
import de.com.magnojr.services.TransferService;
import de.com.magnojr.util.DataProvider;
import io.javalin.core.validation.Validator;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;


public class TransferControllerTest {

    private static TransferController transferController;

    private static Context context = mock(Context.class);
    private static AccountDAO accountDAO = mock(AccountDAO.class);
    private static TransferDAO transferDAO = mock(TransferDAO.class);
    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {

        IAccountService accountService = new AccountService(accountDAO);
        ITransferService transferService = new TransferService(accountService, transferDAO);
        transferController = new TransferController(transferService);

    }

    @Test
    public void shouldTransferValueBetweenAccount() {
        UUID fromAccountId = UUID.randomUUID();
        UUID toAccountId = UUID.randomUUID();
        TransferDTO transferDTO = DataProvider.buildTransferDTO(fromAccountId, toAccountId);

        mockValidatorsForTransfer(context, transferDTO);

        when(accountDAO.findById(fromAccountId)).thenReturn(DataProvider.buildValidAccount(fromAccountId));
        when(accountDAO.findById(toAccountId)).thenReturn(DataProvider.buildValidAccount(toAccountId));

        this.transferController.transfer(context);

        verify(context).status(HttpStatus.ACCEPTED_202);
    }

    @Test
    public void shouldListAllTransfers() {

        // this is not > java 9 so, I cannot us List.of, which is immutable
        List<TransferResponseDTO> transferResponseDTOS = Arrays.asList(
                DataProvider.buildTransferResponseDTO(UUID.randomUUID(), UUID.randomUUID(), "100"),
                DataProvider.buildTransferResponseDTO(UUID.randomUUID(), UUID.randomUUID(), "200")
        );

        when(transferDAO.listTransferResponsesDTO(any())).thenReturn(transferResponseDTOS);

        this.transferController.list(context);

        verify(context).result(toJson(transferResponseDTOS));

    }

    @Test
    public void shouldReturnEmptyListWhenNoTransfersAreFound() {

        when(transferDAO.listTransferResponsesDTO(any())).thenReturn(Collections.EMPTY_LIST);

        this.transferController.list(context);

        verify(context).result(toJson(Collections.EMPTY_LIST));

    }

    private void mockValidatorsForTransfer(Context context, TransferDTO transferDTO) {
        Validator validatorMock = mock(Validator.class);
        when(context.bodyValidator(TransferDTO.class)).thenReturn(validatorMock);
        when(validatorMock.check(any(), any())).thenReturn(validatorMock);
        when(validatorMock.get()).thenReturn(transferDTO);
    }

    private String toJson(List<TransferResponseDTO> transferResponseDTOS) {
        try {
            return mapper.writeValueAsString(transferResponseDTOS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
