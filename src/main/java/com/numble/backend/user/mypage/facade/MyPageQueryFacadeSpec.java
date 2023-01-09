package com.numble.backend.user.mypage.facade;

import com.numble.backend.user.mypage.common.dto.response.MyChatsResponse;
import com.numble.backend.user.mypage.common.dto.response.MyLikesResponse;
import com.numble.backend.user.mypage.common.dto.response.MyPageResponse;
import com.numble.backend.user.mypage.common.dto.response.MyStockResponse;

public interface MyPageQueryFacadeSpec {
	public MyPageResponse findByUserId(String userId);
	public MyStockResponse findMyStock(String userId);
	public MyLikesResponse findMyLikes(String userId);
	public MyChatsResponse findMyChats(String userId);
}
