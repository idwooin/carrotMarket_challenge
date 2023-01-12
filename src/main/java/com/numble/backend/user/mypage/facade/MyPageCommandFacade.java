package com.numble.backend.user.mypage.facade;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.user.auth.application.CustomUserDetailsService;
import com.numble.backend.user.mypage.common.dto.request.UpdateMyPageRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyPageCommandFacade {

	private final CustomUserDetailsService customUserDetailsService;
	public void updateByUserId(UpdateMyPageRequest updateMyPageRequest,
		String userId,
		MultipartFile multipartFile) {
		customUserDetailsService.editUserInfo(updateMyPageRequest, userId, multipartFile);
	}
}
