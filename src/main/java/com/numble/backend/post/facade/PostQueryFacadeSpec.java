package com.numble.backend.post.facade;

import org.springframework.data.domain.Pageable;

import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;

public interface PostQueryFacadeSpec {
	ProductDetailPageResponse findById(String postId);
	ProductPageResponse findAll(String userId, Pageable pageable);
}
