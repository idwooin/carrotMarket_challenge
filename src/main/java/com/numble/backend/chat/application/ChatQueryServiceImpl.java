// package com.numble.backend.chat.application;
//
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.numble.backend.chat.common.dto.response.ChatRoomResponse;
// import com.numble.backend.chat.domain.repository.ChatRoomRepository;
// import com.numble.backend.user.mypage.common.dto.response.MyChatsResponse;
//
// import lombok.RequiredArgsConstructor;
//
// @Service
// @Transactional(readOnly = true)
// @RequiredArgsConstructor
// public class ChatQueryServiceImpl implements ChatQueryService{
// 	private final ChatRoomRepository chatRoomRepository;
// 	@Override
// 	public MyChatsResponse findMyChats(String userId) {
// 		return chatRoomRepository.findByBuyer(userId);
// 	}
//
// 	@Override
// 	public MyChatsResponse findPostChats(String postId) {
// 		return chatRoomRepository.findBySeller(postId);
// 	}
//
// 	@Override
// 	public ChatRoomResponse findChatRoom(String userId, String otherId) {
// 		return null;
// 	}
// }
