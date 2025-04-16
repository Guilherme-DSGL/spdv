package com.erp.spdv.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestUserDTO {
	@NotNull(message = "")
	private String name;
	@NotNull(message = "")
	private String password;

}
