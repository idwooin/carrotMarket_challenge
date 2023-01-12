package com.numble.backend.post.facade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.post.common.dto.request.PostCreateRequest;
import com.numble.backend.post.domain.StockStatus;

public interface PostCommandFacadeSpec {
	void create(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles);
	void delete(String postId, String userId);
	void update(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles, String postId);
	void likePost(String postId, String userId);
	void unlikePost(String postId, String userId);
	void changeStatus(String postId, String userId, StockStatus stockStatus);
}
