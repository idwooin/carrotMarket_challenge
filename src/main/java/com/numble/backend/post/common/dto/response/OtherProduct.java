package com.numble.backend.post.common.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class OtherProduct {
	private String img;
	private String title;
	private String price;

	@QueryProjection
	public OtherProduct(String img, String title, String price) {
		this.img = img;
		this.title = title;
		this.price = price;
	}
}
