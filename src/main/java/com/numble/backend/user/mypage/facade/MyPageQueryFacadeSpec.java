package com.numble.backend.user.mypage.facade;

import org.springframework.data.domain.Pageable;

import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.user.mypage.common.dto.response.MyChatsResponse;
import com.numble.backend.user.mypage.common.dto.response.MyPageResponse;

public interface MyPageQueryFacadeSpec {
	MyPageResponse findByUserId(String userId);
	ProductPageResponse findMyStock(String userId, Pageable pageable);
	ProductPageResponse findMyLikes(String userId, Pageable pageable);
	MyChatsResponse findMyChats(String userId);
}
