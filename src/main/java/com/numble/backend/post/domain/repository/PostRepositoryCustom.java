package com.numble.backend.post.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;

public interface PostRepositoryCustom {
	Optional<ProductDetailPageResponse> findOnePost(String postId);
	ProductPageResponse findAllPosts(Pageable pageable);
	ProductPageResponse findMyStock(String userId, Pageable pageable);
	ProductPageResponse findMyLikes(String userId, Pageable pageable);
}
