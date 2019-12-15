package de.com.magnojr.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transfer {

	private UUID id;

	private LocalDateTime date;
	private String value;
	private Account fromAccount;
	private Account toAccount;

}
