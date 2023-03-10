package com.numble.backend.post.application.command;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.post.common.dto.request.PostCreateRequest;
import com.numble.backend.post.domain.StockStatus;

public interface PostCommandService {
	public void create(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles);
	public void delete(String postId, String userId);
	public void update(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles, String postId);
	public void likePost(String postId, String userId);
	public void unlikePost(String postId, String userId);
	public void changeStatus(String postId, String userId, StockStatus stockStatus);
}
