package com.numble.backend.post.facade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.post.common.dto.request.PostCreateRequest;

public interface PostCommandFacadeSpec {
	void create(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles);
	void delete(String postId, String userId);
	void likePost(String postId, String userId);
	void unlikePost(String postId, String userId);
}
