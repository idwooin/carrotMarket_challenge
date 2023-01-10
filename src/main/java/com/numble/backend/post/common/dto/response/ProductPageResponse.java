package com.numble.backend.post.common.dto.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductPageResponse {
	private Slice<SimpleProduct> simpleProductList;
}
