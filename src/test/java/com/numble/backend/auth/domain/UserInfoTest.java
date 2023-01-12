package com.numble.backend.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.numble.backend.user.auth.application.CustomUserDetailsService;
import com.numble.backend.user.auth.domain.UserInfo;
import com.numble.backend.user.auth.repository.UserInfoRepository;
import com.numble.backend.user.auth.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserInfoTest {
	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private AmazonS3Client amazonS3Client;

	@DisplayName("생성 테스트")
	@Test
	public void user_info_create_test() {
		assertThatCode(() -> new UserInfo("user_id", "010-1234-5678", "shine", "tester"))
			.doesNotThrowAnyException();
	}

	@DisplayName("ID가 같은 경우 equals를 통해 동일한 객체로 판단하는지 확인한다.")
	@Test
	public void compare_equal_test() {
		UserInfo userInfoOne = createUserInfo("uuid", "user_1");
		UserInfo userInfoTwo = createUserInfo("uuid", "user_2");
		assertThat(userInfoOne).isEqualTo(userInfoTwo);
	}

	@DisplayName("ID가 다른 경우 equals를 통해 다른 객체로 판단한다.")
	@Test
	public void compare_not_equal_test() {
		UserInfo userInfoOne = createUserInfo("uuid1", "user_1");
		UserInfo userInfoTwo = createUserInfo("uuid2", "user_1");
		assertThat(userInfoOne).isNotEqualTo(userInfoTwo);
	}

	// @DisplayName("이메일 혹은 닉네임이 같을 시 저장되지 않는다")
	// @Test
	// public void user_register_exception_test() {
	// 	//given
	// 	UserRegisterRequest user1 = new UserRegisterRequest("1@example.com", "1234", "010-1234-5678", "shine", "tester");
	// 	UserRegisterRequest user2 = new UserRegisterRequest("1@example.com", "1234", "010-1234-5678", "shine", "tester2");
	// 	UserRegisterRequest user3 = new UserRegisterRequest("2@example.com", "1234", "010-1234-5678", "shine", "tester1");
	//
	// 	CustomUser customUser = new CustomUser(
	// 		"1@example.com",
	// 		"1234"
	// 	);
	//
	// 	given(userRepository.save(any())).willReturn(customUser);
	// 	//when then
	// 	customUserDetailsService.register(user1);
	//
	// 	verify(userRepository, times(1)).save(any());
	// 	verify(userInfoRepository, times(1)).save(any());
	//
	// 	assertAll(
	// 		() -> assertThrows(UserAlreadyExistsException.class, () -> {customUserDetailsService.register(user2);}),
	// 		() -> assertThrows(UserEmailDuplicatedException.class, () -> {customUserDetailsService.register(user3);})
	// 	);
	// }

	private UserInfo createUserInfo(String id, String userId) {
		UserInfo userInfo = new UserInfo(userId, "010-1234-5678", "shine", "tester");
		ReflectionTestUtils.setField(userInfo, "id", id);
		return userInfo;
	}
}
