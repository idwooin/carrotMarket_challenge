package com.numble.backend.user.mypage.facade;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.user.mypage.common.dto.request.UpdateMyPageRequest;
import com.numble.backend.user.mypage.common.dto.response.MyChatsResponse;
import com.numble.backend.user.mypage.common.dto.response.MyPageResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyPageFacadeGateway implements MyPageCommandFacadeSpec, MyPageQueryFacadeSpec{

	private final MyPageCommandFacade myPageCommandFacade;
	private final MyPageQueryFacade myPageQueryFacade;
	@Override
	public void updateByUserId(UpdateMyPageRequest updateMyPageRequest,
		String userId,
		MultipartFile multipartFile) {
		myPageCommandFacade.updateByUserId(updateMyPageRequest, userId, multipartFile);
	}

	@Override
	public MyPageResponse findByUserId(String userId) {
		return myPageQueryFacade.findByUserId(userId);
	}

	@Override
	public ProductPageResponse findMyStock(String userId, Pageable pageable) {
		return myPageQueryFacade.findMyStock(userId, pageable);
	}

	@Override
	public ProductPageResponse findMyLikes(String userId, Pageable pageable) {
		return myPageQueryFacade.findMyLikes(userId, pageable);
	}

	@Override
	public MyChatsResponse findMyChats(String userId) {
		return myPageQueryFacade.findMyChats(userId);
	}
}
