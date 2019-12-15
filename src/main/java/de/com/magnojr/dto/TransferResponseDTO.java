package de.com.magnojr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDTO {

	private UUID fromAccount;
	private UUID toAccount;
	private BigDecimal value;
	private LocalDateTime dateTime;

}
