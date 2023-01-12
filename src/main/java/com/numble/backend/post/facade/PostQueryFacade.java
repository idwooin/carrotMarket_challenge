package com.numble.backend.post.facade;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.numble.backend.post.application.query.PostQueryService;
import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostQueryFacade {
	private final PostQueryService postQueryService;
	ProductDetailPageResponse findById(String postId){
		return postQueryService.findById(postId);
	}
	ProductPageResponse findAll(Pageable pageable){
		return postQueryService.findAll(pageable);
	}
}
