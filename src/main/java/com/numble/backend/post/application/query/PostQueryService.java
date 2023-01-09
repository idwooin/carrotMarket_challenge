package com.numble.backend.post.application.query;

import org.springframework.data.domain.Pageable;

import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.user.mypage.common.dto.response.MyStockResponse;

public interface PostQueryService {
	public ProductDetailPageResponse findById(String id);
	public ProductPageResponse findAll(Pageable pageable);
	public MyStockResponse findMyStock(String userId);
}
