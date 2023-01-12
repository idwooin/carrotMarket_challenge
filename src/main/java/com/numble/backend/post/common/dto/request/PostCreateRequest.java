package com.numble.backend.post.common.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.numble.backend.post.domain.StockCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostCreateRequest {

	@NotBlank(message = "제목을 입력해주세요")
	private String title;

	@NotNull(message = "상품 유형을 확인해 주세요")
	private StockCategory stockCategory;

	@NotBlank(message = "가격을 입력해주세요")
	private String price;

	@NotBlank(message = "내용을 10자 이상 입력해주세요")
	@Size(min = 10, max = 500)
	private String contents;

}
