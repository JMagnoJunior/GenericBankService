package de.com.magnojr.dto;

import java.math.BigDecimal;
import java.util.UUID;

import io.javalin.core.validation.Validator;
import kotlin.jvm.functions.Function1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {

	private UUID from;
	private UUID to;
	private BigDecimal value;

}
