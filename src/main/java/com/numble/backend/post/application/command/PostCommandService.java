package com.numble.backend.post.application.command;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.post.common.dto.request.PostCreateRequest;

public interface PostCommandService {
	public void create(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles);
	public void delete(String postId, String userId);
	public void likePost(String postId, String userId);
	public void unlikePost(String postId, String userId);
}
