package com.numble.backend.post.domain.repository;

import static com.numble.backend.post.domain.QPost.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.post.common.dto.response.QSimpleProduct;
import com.numble.backend.post.common.dto.response.SimpleProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<ProductDetailPageResponse> findOnePost(String postId) {
		// Optional<ProductDetailPageResponse> response = Optional.ofNullable(
		// 	queryFactory.select(new ProductDetailPageResponse(
		// 		post.img.size(),
		// 		post.userInfo.nickname,
		// 		post.title,
		// 		post.price,
		// 		post.category,
		// 		post.createdAt,
		// 		post.contents,
		// 		post.likes.count(),
		// 		post.userInfo.post.size()
		// 	))
		// 	.from(post)
		// 	.where(post.id.eq(postId))
		// 	.fetchOne());
		//
		// List<OtherProduct> responses = queryFactory.select(
		// 	new OtherProduct(
		//
		// 	)
		// )
		// 	.from(post)
		//
		// if (response.isEmpty()){
		// 	return Optional.empty();
		// }
		// return Optional.ofNullable(response);
		return null;
	}

	@Override
	public ProductPageResponse findAllPosts(Pageable pageable) {
		List<SimpleProduct> response = queryFactory
			.select(new QSimpleProduct(
				post.photos.get(0).url,
				post.title,
				post.region,
				post.price,
				post.likes.count(),
				post.sold,
				post.createdAt
			))
			.from(post)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(post.createdAt.desc())
			.fetch();

		boolean hasNext = false;
		if (response.size() > pageable.getPageSize()) {
			response.remove(pageable.getPageSize());
			hasNext = true;
		}
		Slice<SimpleProduct> simpleProducts = new SliceImpl<>(response, pageable, hasNext);
		return new ProductPageResponse(simpleProducts);
	}
}
