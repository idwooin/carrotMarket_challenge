package com.numble.backend.chat.common.dto.response;

import lombok.Getter;

@Getter
public class ChatMessageResponse {
	private Boolean isMe;
	private String nickname;
	private String message;
}
