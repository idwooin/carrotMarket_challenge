package com.numble.backend.post.domain.repository;

import static com.numble.backend.post.domain.QPhoto.photo;
import static com.numble.backend.post.domain.QPost.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.numble.backend.post.common.dto.response.OtherProduct;
import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.post.common.dto.response.QOtherProduct;
import com.numble.backend.post.common.dto.response.QProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.QSimpleProduct;
import com.numble.backend.post.common.dto.response.SimpleProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<ProductDetailPageResponse> findOnePost(String postId) {
		Optional<ProductDetailPageResponse> response = Optional.ofNullable(
			queryFactory.select(new QProductDetailPageResponse(
				post.photos.size(),
				post.userInfo.nickname,
				post.title,
				post.price,
				post.category,
				post.contents,
				post.createdAt,
				post.likes.likeCount
			))
			.from(post)
			.where(post.id.eq(postId))
			.fetchOne());

		if (response.isEmpty()){
			return Optional.empty();
		}

		List<String> img = queryFactory.select(
			photo.url
		)
		.from(post)
		.leftJoin(photo)
		.on(photo.post.id.eq(post.id))
		.where(post.id.eq(postId))
		.fetch();

		String ownerId = queryFactory.select(
			post.ownerId
		)
		.from(post)
		.where(post.id.eq(postId))
		.fetchOne();

		List<OtherProduct> otherProducts = queryFactory.select(new QOtherProduct(
			photo.url,
			post.title,
			post.price
		))
		.from(post)
		.leftJoin(photo)
		.on(photo.post.id.eq(post.id))
		.where(post.userInfo.userId.eq(ownerId)
			.and(photo.url.isNull().or(photo.thumbnail.eq(Boolean.TRUE)))
			.and(post.id.notIn(postId)))
		.fetch();

		response.get().setImg(img);
		response.get().setOtherProducts(otherProducts);

		return response;
	}

	@Override
	public ProductPageResponse findAllPosts(Pageable pageable) {
		List<SimpleProduct> response = queryFactory
			.select(new QSimpleProduct(
				photo.url,
				post.title,
				post.region,
				post.price,
				post.likes.likeCount,
				post.stockStatus,
				post.createdAt
			))
			.from(post)
			.leftJoin(photo)
			.on(photo.post.id.eq(post.id))
			.where(photo.url.isNull().or(photo.thumbnail.eq(Boolean.TRUE)))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(post.createdAt.desc())
			.fetch();

		Slice<SimpleProduct> simpleProducts = makeSlice(response, pageable);
		return new ProductPageResponse(simpleProducts);
	}

	@Override
	public ProductPageResponse findMyStock(String userId, Pageable pageable) {
		List<SimpleProduct> response = queryFactory
			.select(new QSimpleProduct(
				photo.url,
				post.title,
				post.region,
				post.price,
				post.likes.likeCount,
				post.stockStatus,
				post.createdAt
			))
			.from(post)
			.leftJoin(photo)
			.on(photo.post.id.eq(post.id))
			.where(post.userInfo.userId.eq(userId)
				.and(photo.url.isNull().or(photo.thumbnail.eq(Boolean.TRUE))))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(post.createdAt.desc())
			.fetch();

		Slice<SimpleProduct> simpleProducts = makeSlice(response, pageable);
		return new ProductPageResponse(simpleProducts);
	}

	@Override
	public ProductPageResponse findMyLikes(String userId, Pageable pageable) {
		List<SimpleProduct> response = queryFactory
			.select(new QSimpleProduct(
				photo.url,
				post.title,
				post.region,
				post.price,
				post.likes.likeCount,
				post.stockStatus,
				post.createdAt
			))
			.from(post)
			.leftJoin(photo)
			.on(photo.post.id.eq(post.id))
			.where(post.likes.likeUsers.contains(userId)
				.and(photo.url.isNull().or(photo.thumbnail.eq(Boolean.TRUE))))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(post.createdAt.desc())
			.fetch();

		Slice<SimpleProduct> simpleProducts = makeSlice(response, pageable);
		return new ProductPageResponse(simpleProducts);
	}

	private Slice<SimpleProduct> makeSlice(List<SimpleProduct> response, Pageable pageable) {
		boolean hasNext = false;
		if (response.size() > pageable.getPageSize()) {
			response.remove(pageable.getPageSize());
			hasNext = true;
		}

		return new SliceImpl<>(response, pageable, hasNext);
	}

}
