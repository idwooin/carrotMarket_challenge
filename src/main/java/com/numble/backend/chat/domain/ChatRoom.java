package com.numble.backend.chat.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "update chat_room set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class ChatRoom {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	private String postId;

	private String buyer;

	private String seller;

	private Boolean deleted = Boolean.FALSE;

	public ChatRoom(String postId, String buyer, String seller) {
		this.postId = postId;
		this.buyer = buyer;
		this.seller = seller;
	}

	@OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ChatMessage> chatMessageList = new ArrayList<>();
}
