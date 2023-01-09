package com.numble.backend.post.common.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.numble.backend.post.domain.StockCategory;

import lombok.Getter;

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
	private Integer likes;
	private List<OtherProduct> otherProducts;
}
