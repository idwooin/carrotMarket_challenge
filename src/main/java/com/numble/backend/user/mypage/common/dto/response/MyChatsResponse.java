package com.numble.backend.user.mypage.common.dto.response;

import org.springframework.data.domain.Slice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyChatsResponse {
	Slice<MyChat> myChats;
}
