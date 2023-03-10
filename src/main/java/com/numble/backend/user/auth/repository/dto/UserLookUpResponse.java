package com.numble.backend.user.auth.repository.dto;

public record UserLookUpResponse(
	String userId,
	String email,
	String password,
	String phone,
	String name,
	String nickName,
	String profileImage
) {
}
