package de.com.magnojr.services;

import de.com.magnojr.dao.TransferDAO;
import de.com.magnojr.domain.Account;
import de.com.magnojr.domain.Transfer;
import de.com.magnojr.dto.TransferResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class TransferService implements ITransferService {

    private final IAccountService accountService;
    private final TransferDAO transferDAO;

    @Override
    public void transfer(BigDecimal value, UUID fromAccountId, UUID toAccountId) {

        log.info("Starting transfer the value: {} from account:  {} to account: {}", value, fromAccountId, toAccountId);

        final Account originAccount = accountService.get(fromAccountId);
        final Account destinationAccount = accountService.get(toAccountId);

        Transfer transfer = CalculateTransferService.calculate(value, originAccount, destinationAccount);

        log.info("will save the transer:  {}", transfer);
        transferDAO.save(transfer);
    }

    @Override
    public List<TransferResponseDTO> listTransferResponsesDTO(Optional<LocalDate> date) {
        return transferDAO.listTransferResponsesDTO(date);
    }

}
