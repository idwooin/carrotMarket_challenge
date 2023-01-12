package com.numble.backend.post.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.numble.backend.post.application.command.PostCommandServiceImpl;
import com.numble.backend.post.domain.StockCategory;
import com.numble.backend.post.domain.repository.PostRepository;
import com.numble.backend.post.common.dto.request.PostCreateRequest;
import com.numble.backend.user.auth.domain.UserInfo;
import com.numble.backend.user.auth.repository.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class PostCommandServiceTest {

	@InjectMocks
	private PostCommandServiceImpl postCommandService;

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserInfoRepository userInfoRepository;

	@DisplayName("post 생성 테스트")
	@Test
	void createPost() {
		// given
		PostCreateRequest postCreateRequest = new PostCreateRequest(
			"에어팟 프로 팔아요",
			StockCategory.DIGITAL,
			"200,000",
			"진짜 너무 싸게 판다 ㅋㅋㅋㅋ"
		);

		UserInfo userInfo = new UserInfo(
			"1",
		"01000000000",
		"wooin",
		"wooin"
		);

		given(userInfoRepository.findById(anyString())).willReturn(Optional.of(userInfo));

		// when
		postCommandService.create(postCreateRequest, "1", new ArrayList<>());

		// then
		verify(userInfoRepository, times(1)).findById(anyString());
		verify(postRepository, times(1)).save(any());
	}
}
