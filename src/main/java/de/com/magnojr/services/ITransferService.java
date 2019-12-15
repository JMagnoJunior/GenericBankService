package de.com.magnojr.services;

import de.com.magnojr.dto.TransferResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITransferService {

    void transfer(BigDecimal value, UUID fromAccountId, UUID toAccountId);

    List<TransferResponseDTO> listTransferResponsesDTO(Optional<LocalDate> date);

}
