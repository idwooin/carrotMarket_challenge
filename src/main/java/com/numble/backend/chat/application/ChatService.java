// package com.numble.backend.chat.application;
//
// import java.io.IOException;
//
// import org.springframework.stereotype.Service;
// import org.springframework.web.socket.TextMessage;
// import org.springframework.web.socket.WebSocketSession;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.numble.backend.chat.domain.ChatRoom;
// import com.numble.backend.chat.domain.repository.ChatRoomRepository;
// import com.numble.backend.chat.exception.ChatRoomNotFoundException;
// import com.numble.backend.post.domain.repository.PostRepository;
// import com.numble.backend.post.exception.PostNotFoundException;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @RequiredArgsConstructor
// @Service
// public class ChatService {
// 	private final ObjectMapper objectMapper;
// 	private final ChatRoomRepository chatRoomRepository;
// 	private final PostRepository postRepository;
//
// 	// @PostConstruct
// 	// private void init() {
// 	// 	chatRooms = new LinkedHashMap<>();
// 	// }
//
// 	// public List<ChatRoom> findAllRoom() {
// 	// 	return new ArrayList<>(chatRooms.values());
// 	// }
//
// 	public ChatRoom findRoomById(String roomId) {
// 		return chatRoomRepository.findById(roomId)
// 			.orElseThrow(() -> new ChatRoomNotFoundException());
// 	}
//
// 	public void createRoom(String postId, String userId) {
// 		String otherId = postRepository.findById(postId)
// 			.orElseThrow(() -> new PostNotFoundException())
// 			.getOwnerId();
//
// 		ChatRoom chatRoom = new ChatRoom(
// 			postId,
// 			userId,
// 			otherId
// 		);
//
// 		ChatRoom chatRoom1 = chatRoomRepository.save(chatRoom);
// 	}
//
// 	public <T> void sendMessage(WebSocketSession session, T message) {
// 		try{
// 			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
// 		} catch (IOException e) {
// 			log.error(e.getMessage(), e);
// 		}
// 	}
// }
