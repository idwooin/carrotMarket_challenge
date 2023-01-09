package com.numble.backend.user.mypage.common.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class UpdateMyPageRequest {
	@NotBlank String nickName;
}
