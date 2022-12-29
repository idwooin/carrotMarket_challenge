package com.numble.backend.user.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.numble.backend.user.auth.domain.CustomUser;
import com.numble.backend.user.auth.domain.UserInfo;
import com.numble.backend.user.auth.exception.UserAlreadyExistsException;
import com.numble.backend.user.auth.exception.UserNicknameDuplicatedException;
import com.numble.backend.user.auth.exception.UserNotFoundException;
import com.numble.backend.user.auth.presentation.dto.UserRegisterRequest;
import com.numble.backend.user.auth.repository.UserInfoRepository;
import com.numble.backend.user.auth.repository.UserRepository;
import com.numble.backend.user.auth.repository.dto.UserLookUpResponse;
import com.numble.backend.user.auth.vo.UserContext;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserInfoRepository userInfoRepository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CustomUser user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

		return new UserContext(user, buildAuthorities(user));
	}

	public void register(UserRegisterRequest userRegisterRequest) {
		checkAlreadyExistsUser(userRegisterRequest.getEmail(), userRegisterRequest.getNickname());

		CustomUser newUser = createUser(userRegisterRequest.getPassword(), userRegisterRequest.getEmail());

		CustomUser savedUser = userRepository.save(newUser);
		userInfoRepository.save(UserInfo.of(savedUser.getId(), userRegisterRequest));
	}

	public void delete(String userId) {
		userInfoRepository.deleteByUserId(userId);
		userRepository.deleteById(userId);
	}

	@Transactional(readOnly = true)
	public UserLookUpResponse findUserWithDetailInfo(String userId) {
		return userRepository.findUserDetailInfoById(userId)
			.orElseThrow(UserNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public CustomUser findUserForAuthentication(String userId) {
		return userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
	}

	// public void editUserInformation(UserInfoEditRequest editRequest, String userId) {
	// 	edit(editRequest, userId);
	// }

	// private void edit(UserInfoEditRequest editRequest, String userId) {
	// 	CustomUser findUser = userRepository.findById(userId)
	// 		.orElseThrow(UserNotFoundException::new);
	//
	// 	UserInfo findUserInfo = userInfoRepository.findByUserId(userId)
	// 		.orElseThrow(UserNotFoundException::new);
	//
	// 	checkAlreadyExistsUser(editRequest.email(), editRequest.nickName());
	//
	// 	findUser.editEmail(editRequest.email());
	// 	findUserInfo.editNickNameAndImage(editRequest.nickName(), editRequest.imageUrl());
	// }

	private void checkAlreadyExistsUser(String email, String nickname) {
		checkAlreadyExistsEmail(email);
		checkAlreadyExistsNickName(nickname);
	}

	private void checkAlreadyExistsNickName(String nickname) {
		userInfoRepository.findByNickname(nickname)
			.ifPresent(user -> {
				throw new UserNicknameDuplicatedException();
			});
	}

	private void checkAlreadyExistsEmail(String email) {
		userRepository.findByEmail(email)
			.ifPresent(user -> {
				throw new UserAlreadyExistsException();
			});
	}

	private CustomUser createUser(final String password, final String email) {
		String encodedPassword = passwordEncoder.encode(password);
		return CustomUser.createWithRoleUser(email, encodedPassword);
	}

	private List<GrantedAuthority> buildAuthorities(CustomUser user) {
		return user.getAuthorities()
			.stream()
			.map(userRole -> new SimpleGrantedAuthority(userRole.getAuthority()))
			.collect(Collectors.toList());
	}
}
