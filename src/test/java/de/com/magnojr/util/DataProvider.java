package de.com.magnojr.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import de.com.magnojr.domain.Account;
import de.com.magnojr.domain.User;
import de.com.magnojr.dto.AccountCreateDTO;
import de.com.magnojr.dto.TransferDTO;
import de.com.magnojr.dto.TransferResponseDTO;
import de.com.magnojr.dto.UserCreateDTO;

public class DataProvider {
	
	public static Account buildValidAccount(UUID id) {
		return Account.builder()
				.id(id)
				.user(buildValidUser(UUID.randomUUID()))
				.amount(String.valueOf(generateRandomIntIntRange(1,10000)))
				.build();
	}

	public static TransferDTO buildTransferDTO(UUID fromAccountId, UUID toAccountId) {
		return TransferDTO
				.builder()
				.from(fromAccountId)
				.to(toAccountId)
				.value(new BigDecimal("100"))
				.build();
	}

	public static User buildValidUser(UUID id) {
		return User.builder()
				.id(id)
				.name("test_" + id)
				.build();
	}

	public static TransferResponseDTO buildTransferResponseDTO(UUID fromAccountId, UUID toAccountId, String value) {
		return TransferResponseDTO
				.builder()
				.dateTime(LocalDateTime.now())
				.fromAccount(fromAccountId)
				.toAccount(toAccountId)
				.value(new BigDecimal(value))
				.build();
	}

	public static AccountCreateDTO buildValidAccountCreateDTO() {
		UserCreateDTO userCreateDTO = UserCreateDTO.builder()
				.name("Test User")
				.build();

		return AccountCreateDTO.builder()
				.amount(new BigDecimal("100"))
				.user(userCreateDTO)
				.build();
	}
	
	private static int generateRandomIntIntRange(int min, int max) {
	    Random r = new Random();
	    return r.nextInt((max - min) + 1) + min;
	}

}
