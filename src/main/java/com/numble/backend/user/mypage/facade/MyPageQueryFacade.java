package com.numble.backend.user.mypage.facade;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.numble.backend.post.application.query.PostQueryService;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.user.auth.application.CustomUserDetailsService;
import com.numble.backend.user.mypage.common.dto.response.MyChatsResponse;
import com.numble.backend.user.mypage.common.dto.response.MyPageResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyPageQueryFacade {
	private final CustomUserDetailsService customUserDetailsService;
	private final PostQueryService postQueryService;
	// private final ChatQueryService chatQueryService;

	public MyPageResponse findByUserId(String userId) {
		return customUserDetailsService.findMyPage(userId);
	}

	public ProductPageResponse findMyStock(String userId, Pageable pageable) {
		return postQueryService.findMyStock(userId, pageable);
	}

	public ProductPageResponse findMyLikes(String userId, Pageable pageable) {
		return postQueryService.findMyLikes(userId, pageable);
	}

	public MyChatsResponse findMyChats(String userId) {
		// return chatQueryService.findMyChats(userId);
		return new MyChatsResponse();
	}
}
