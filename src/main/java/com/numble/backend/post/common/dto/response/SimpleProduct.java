package com.numble.backend.post.common.dto.response;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SimpleProduct {
	private String url;
	private String title;
	private String region;
	private String price;
	private Long likes;
	private Boolean sold;
	private LocalDateTime createdAt;

	@QueryProjection
	public SimpleProduct(String url, String title, String region, String price, Long likes, Boolean sold,
		LocalDateTime createdAt) {
		this.url = url;
		this.title = title;
		this.region = region;
		this.price = price;
		this.likes = likes;
		this.sold = sold;
		this.createdAt = createdAt;
	}
}
