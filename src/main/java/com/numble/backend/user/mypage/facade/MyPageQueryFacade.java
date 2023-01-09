package com.numble.backend.user.mypage.facade;

import org.springframework.stereotype.Component;

import com.numble.backend.post.application.query.PostLikeQueryService;
import com.numble.backend.post.application.query.PostQueryService;
import com.numble.backend.user.auth.application.CustomUserDetailsService;
import com.numble.backend.user.mypage.common.dto.response.MyChatsResponse;
import com.numble.backend.user.mypage.common.dto.response.MyLikesResponse;
import com.numble.backend.user.mypage.common.dto.response.MyPageResponse;
import com.numble.backend.user.mypage.common.dto.response.MyStockResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyPageQueryFacade {
	private final CustomUserDetailsService customUserDetailsService;
	private final PostQueryService postQueryService;
	private final PostLikeQueryService postLikeQueryService;
	public MyPageResponse findByUserId(String userId) {
		return customUserDetailsService.findMyPage(userId);
	}

	public MyStockResponse findMyStock(String userId) {
		return postQueryService.findMyStock(userId);
	}

	public MyLikesResponse findMyLikes(String userId) {
		return postLikeQueryService.findMyLikes(userId);
	}

	public MyChatsResponse findMyChats(String userId) {
		return null;
	}
}
