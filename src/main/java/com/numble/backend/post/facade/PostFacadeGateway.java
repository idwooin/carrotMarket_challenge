package com.numble.backend.post.facade;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.post.common.dto.request.PostCreateRequest;
import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.post.domain.StockStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostFacadeGateway implements PostCommandFacadeSpec, PostQueryFacadeSpec{
	private final PostCommandFacade postCommandFacade;
	private final PostQueryFacade postQueryFacade;

	public void create(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles) {
		postCommandFacade.create(postCreateRequest, userId, multipartFiles);
	}

	public void delete(String postId, String userId) {
		postCommandFacade.delete(postId, userId);
	}

	@Override
	public void likePost(String postId, String userId) {
		postCommandFacade.likePost(postId, userId);
	}

	@Override
	public void unlikePost(String postId, String userId) {
		postCommandFacade.unlikePost(postId, userId);
	}

	@Override
	public ProductDetailPageResponse findById(String postId) {
		return postQueryFacade.findById(postId);
	}

	@Override
	public ProductPageResponse findAll(Pageable pageable) {
		return postQueryFacade.findAll(pageable);
	}

	@Override
	public void changeStatus(String postId, String userId, StockStatus stockStatus) {
		postCommandFacade.changeStatus(postId, userId, stockStatus);
	}
}
