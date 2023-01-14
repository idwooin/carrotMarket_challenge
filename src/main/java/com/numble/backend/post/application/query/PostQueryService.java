package com.numble.backend.post.application.query;

import org.springframework.data.domain.Pageable;

import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;

public interface PostQueryService {
	ProductDetailPageResponse findById(String id);
	ProductPageResponse findAll(String userId, Pageable pageable);
	ProductPageResponse findMyStock(String userId, Pageable pageable);
	ProductPageResponse findMyLikes(String userId, Pageable pageable);
}
