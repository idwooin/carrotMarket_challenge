package com.numble.backend.user.mypage.common.dto.response;

import com.numble.backend.post.domain.StockStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MyChat {
	private String nickname;
	private String profileUrl;
	private String lastChat;
	private String postUrl;
	private StockStatus stockStatus;

	@QueryProjection
	public MyChat(String nickname, String profileUrl, String lastChat, String postUrl, StockStatus stockStatus) {
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.lastChat = lastChat;
		this.postUrl = postUrl;
		this.stockStatus = stockStatus;
	}
}
