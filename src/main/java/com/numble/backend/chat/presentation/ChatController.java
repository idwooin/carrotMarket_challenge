// package com.numble.backend.chat.presentation;
//
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.numble.backend.chat.application.ChatService;
// import com.numble.backend.chat.common.dto.ChatRoom;
// import com.numble.backend.chat.common.dto.response.ChatRoomResponse;
//
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// @RestController
// @RequestMapping("/api/chats")
// public class ChatController {
// 	private final ChatService chatService;
//
// 	@PostMapping("/{postId}/{userId}")
// 	public void createRoom(
// 		@PathVariable("postId") String postId,
// 		@PathVariable("userId") String userId
// 	) {
//
// 		chatService.createRoom(postId, userId);
// 	}
//
// 	// @GetMapping
// 	// public List<ChatRoom> findAllRoom() {
// 	// 	return chatService.findAllRoom();
// 	// }
// }
