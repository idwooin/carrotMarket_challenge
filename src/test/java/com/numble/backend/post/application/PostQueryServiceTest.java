package com.numble.backend.post.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.numble.backend.post.application.query.PostQueryService;
import com.numble.backend.post.application.query.PostQueryServiceImpl;
import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.StockCategory;
import com.numble.backend.post.domain.repository.PostRepository;
import com.numble.backend.user.auth.domain.CustomUser;
import com.numble.backend.user.auth.domain.UserInfo;

@ExtendWith(MockitoExtension.class)
public class PostQueryServiceTest {

	@InjectMocks
	private PostQueryServiceImpl postQueryService;

	@Mock
	private PostRepository postRepository;

	@BeforeEach
	void before_each() {
		UserInfo user1 = new UserInfo("id1", "01000000000", "wooin1", "우인");
		UserInfo user2 = new UserInfo("id2", "01000000000", "wooin2", "우인");
		Post post1 = new Post("에어팟", StockCategory.DIGITAL, "200,000", "빨리 사주세용", user1);
		Post post2 = new Post("에어팟2", StockCategory.DIGITAL, "200,000", "빨리 사주세용", user2);
	}

	// @DisplayName("단건 Post 조회에 성공한다.")
	// @Test
	// void find_one_post_test() {
	// 	//given
	// 	ProductDetailPageResponse productDetailPageResponse = new ProductDetailPageResponse(
	//
	// 		(Integer imgSize, String nickname, String title, String price,
	// 		StockCategory stockCategory, String contents, LocalDateTime createdAt, Long likes)
	// 	);
	// 	given(postRepository.findById(anyString())).willReturn(Optional.of(barLookupResponse));
	// 	//when
	// 	ProductDetailPageResponse response = postQueryService.findById(post1.getId());
	// 	//then
	// 	assertAll(
	// 		() -> assertThat(response.getImgSize()).isEqualTo(4.3f),
	// 		() -> assertThat(response.getNickname()).isEqualTo("content!"),
	// 		() -> assertThat(response.getTitle()).contains("uuid1", "uuid2"),
	// 		() -> assertThat(response.getPrice()).isEqualTo(1),
	// 		() -> assertThat(response.getStockCategory()).isEqualTo(),
	// 		() -> assertThat(response.getContents()),
	// 		() -> assertThat(response.getCreatedAt()),
	// 		() -> assertThat(() -> response.getLikes())
	// 	);
	// 	verify(barQueryRepository, times(1)).findById(any());
	// }

	// @DisplayName("Post 전체 조회에 성공한다.")
	// @Test
	// void find_all_post_test() {
	// 	//given
	// 	//when
	// 	//then
	// }
	//
	// @DisplayName("내 Post 조회에 성공한다.")
	// @Test
	// void find_my_stock_test() {
	// 	//given
	// 	//when
	// 	//then
	// }
	//
	// @DisplayName("내가 좋아요한 post 조회에 성공한다.")
	// @Test
	// void find_my_likes_test() {
	// 	//given
	// 	//when
	// 	//then
	// }
}

