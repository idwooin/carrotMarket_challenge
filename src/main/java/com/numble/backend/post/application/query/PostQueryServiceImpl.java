package com.numble.backend.post.application.query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.post.domain.repository.PostRepository;
import com.numble.backend.post.exception.PostNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

	private final PostRepository postRepository;
	@Override
	public ProductDetailPageResponse findById(String postId) {
		ProductDetailPageResponse response = postRepository.findOnePost(postId)
			.orElseThrow(() -> new PostNotFoundException());

		return response;
	}

	@Override
	public ProductPageResponse findAll(String userId, Pageable pageable) {
		ProductPageResponse response = postRepository.findAllPosts(userId, pageable);

		return response;
	}

	@Override
	public ProductPageResponse findMyStock(String userId, Pageable pageable) {
		ProductPageResponse response = postRepository.findMyStock(userId, pageable);

		return response;
	}

	@Override
	public ProductPageResponse findMyLikes(String userId, Pageable pageable) {
		ProductPageResponse response = postRepository.findMyLikes(userId, pageable);

		return response;
	}
}
