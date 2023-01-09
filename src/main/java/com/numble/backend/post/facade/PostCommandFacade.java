package com.numble.backend.post.facade;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.post.application.command.PostCommandService;
import com.numble.backend.post.common.dto.request.PostCreateRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostCommandFacade {
	private final PostCommandService postCommandService;
	public void create(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles) {
		postCommandService.create(postCreateRequest, userId, multipartFiles);
	}

	public void delete(String postId, String userId) {
		postCommandService.delete(postId, userId);
	}

	public void likePost(String postId, String userId) {
		postCommandService.likePost(postId, userId);
	}

	public void unlikePost(String postId, String userId) {
		postCommandService.unlikePost(postId, userId);
	}
}
