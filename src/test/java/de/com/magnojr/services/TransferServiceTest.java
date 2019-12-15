package de.com.magnojr.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import de.com.magnojr.domain.Account;
import de.com.magnojr.domain.Transfer;
import de.com.magnojr.util.DataProvider;

public class TransferServiceTest {
	
	@Test
	public void shouldCalculateTransfer() {
		final BigDecimal value = new BigDecimal("100");

		final Account from = DataProvider.buildValidAccount(UUID.randomUUID());
		String previousAmountOriginAccount = from.getAmount();
		final Account to = DataProvider.buildValidAccount(UUID.randomUUID());
		String previousAmountDestinationAccount = to.getAmount();

		Transfer transfer = CalculateTransferService.calculate(value, from, to);

		BigDecimal expectedAmountOriginAccount = new BigDecimal(previousAmountOriginAccount).subtract(value);
		BigDecimal expectedAmountDestinationAccount = new BigDecimal(previousAmountDestinationAccount).add(value);

		assertThat(from.getAmount(), is(expectedAmountOriginAccount.toString()));
		assertThat(to.getAmount(), is(expectedAmountDestinationAccount.toString()));
		assertThat(transfer.getValue(), is(value.toString()));
	}



}
