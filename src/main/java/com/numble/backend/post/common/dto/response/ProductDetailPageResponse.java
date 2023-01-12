package com.numble.backend.post.common.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.numble.backend.post.domain.StockCategory;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductDetailPageResponse {
	private List<String> img;
	private Integer imgSize;
	private String nickname;
	private String title;
	private String price;
	private StockCategory stockCategory;
	private String contents;
	private LocalDateTime createdAt;
	private Long likes;
	private List<OtherProduct> otherProducts;

	@QueryProjection
	public ProductDetailPageResponse(Integer imgSize, String nickname, String title, String price,
		StockCategory stockCategory, String contents, LocalDateTime createdAt, Long likes) {
		this.imgSize = imgSize;
		this.nickname = nickname;
		this.title = title;
		this.price = price;
		this.stockCategory = stockCategory;
		this.contents = contents;
		this.createdAt = createdAt;
		this.likes = likes;
	}

	public void setImg(List<String> img) {
		this.img = img;
	}

	public void setOtherProducts(List<OtherProduct> otherProducts) {
		this.otherProducts = otherProducts;
	}

}
