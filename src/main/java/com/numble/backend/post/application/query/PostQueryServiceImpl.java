package com.numble.backend.post.application.query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.post.domain.repository.PostRepository;
import com.numble.backend.post.exception.PostNotFoundException;
import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.user.mypage.common.dto.response.MyStockResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
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
	public ProductPageResponse findAll(Pageable pageable) {
		ProductPageResponse response = postRepository.findAllPosts(pageable);

		return response;
	}

	@Override
	public MyStockResponse findMyStock(String userId) {
		return null;
	}
}
