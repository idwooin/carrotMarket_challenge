package com.numble.backend.chat.common.dto.response;

import java.util.List;

import com.numble.backend.post.common.dto.response.OtherProduct;
import com.numble.backend.post.domain.StockStatus;

import lombok.Getter;

@Getter
public class ChatRoomResponse {
	private OtherProduct otherProduct;
	private List<ChatMessageResponse> chatMessageResponses;
}
