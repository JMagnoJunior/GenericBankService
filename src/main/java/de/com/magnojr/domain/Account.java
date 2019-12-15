package de.com.magnojr.domain;

import java.math.BigDecimal;
import java.util.UUID;

import de.com.magnojr.exceptions.TransferLimitException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	private UUID id;
	private User user;
	private String amount;

	public void subtract(BigDecimal valueSubtracted) {
		BigDecimal value = new BigDecimal(this.amount);
		if(value.compareTo(valueSubtracted) < 0) {
			throw new TransferLimitException();
		}

		this.amount = value.subtract(valueSubtracted).toString();
	}

	public void add(BigDecimal valueAdded) {
		BigDecimal value = new BigDecimal(this.amount);
		this.amount = value.add(valueAdded).toString();
	}
}
