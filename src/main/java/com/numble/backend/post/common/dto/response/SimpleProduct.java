package com.numble.backend.post.common.dto.response;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;

import com.numble.backend.post.domain.StockStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SimpleProduct {
	private String postId;
	private String url;
	private String title;
	private String region;
	private String price;
	private Long likes;
	private Boolean isLiked;
	private StockStatus stockStatus;
	private LocalDateTime createdAt;

	@QueryProjection
	public SimpleProduct(String postId, String url, String title,
		String region, String price, Long likes,
		StockStatus stockStatus, Boolean isLiked, LocalDateTime createdAt) {
		this.postId = postId;
		this.url = url;
		this.title = title;
		this.region = region;
		this.price = price;
		this.likes = likes;
		this.isLiked = isLiked;
		this.stockStatus = stockStatus;
		this.createdAt = createdAt;
	}
}
