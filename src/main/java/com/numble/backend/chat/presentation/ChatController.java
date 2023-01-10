package com.numble.backend.chat.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.chat.application.ChatService;
import com.numble.backend.chat.common.dto.ChatRoom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chats")
public class ChatController {
	private final ChatService chatService;

	@PostMapping
	public ChatRoom createRoom(@RequestBody String name) {
		return chatService.createRoom(name);
	}

	@GetMapping
	public List<ChatRoom> findAllRoom() {
		return chatService.findAllRoom();
	}
}
