package com.numble.backend.post.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

// entity 아님, 부모 객체랑 항상 함께 영속됨
// 따로 query 불가
@Embeddable
public class PostLikes {
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
		name = "likes",
		joinColumns = @JoinColumn(name = "post_id")
	)
	@Column(name = "like_user_id")
	private Set<String> likeUsers = new HashSet<>();

	@Column(name = "like_count")
	private Long likeCount = 0L;
	public Integer size() {
		return likeUsers.size();
	}

	public Boolean add(String userId) {
		likeCount = likeCount+1;
		return likeUsers.add(userId);
	}

	public Boolean remove(String userId) {
		likeCount = likeCount-1;
		return likeUsers.remove(userId);
	}

	public Boolean userLiked(String userId) {
		return likeUsers.contains(userId);
	}
}
