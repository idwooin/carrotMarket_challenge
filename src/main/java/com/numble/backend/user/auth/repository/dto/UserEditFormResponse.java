package com.numble.backend.user.auth.repository.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserEditFormResponse(
	@NotBlank String imageUrl,
	@NotBlank String nickName,
	@Email @NotBlank String email
) {
}
