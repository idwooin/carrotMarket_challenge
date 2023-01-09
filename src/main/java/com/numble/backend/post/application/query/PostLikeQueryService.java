package com.numble.backend.post.application.query;

import com.numble.backend.user.mypage.common.dto.response.MyLikesResponse;

public interface PostLikeQueryService {
	MyLikesResponse findMyLikes(String userId);
}
