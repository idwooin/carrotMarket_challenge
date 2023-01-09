package com.numble.backend.post.common.dto.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductPageResponse {
	private Slice<SimpleProduct> simpleProductList;
}
