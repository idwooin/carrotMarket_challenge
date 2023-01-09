package com.numble.backend.user.mypage.facade;

import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.user.mypage.common.dto.request.UpdateMyPageRequest;

public interface MyPageCommandFacadeSpec {
	void updateByUserId(UpdateMyPageRequest updateMyPageRequest, String userId, MultipartFile multipartFile);
}
